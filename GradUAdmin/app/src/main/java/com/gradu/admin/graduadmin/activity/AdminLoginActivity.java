package com.gradu.admin.graduadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.gradu.admin.graduadmin.AdminMainActivity;
import com.gradu.admin.graduadmin.MainActivity;
import  com.gradu.admin.graduadmin.R;
import  com.gradu.admin.graduadmin.app.AppConfig;
import  com.gradu.admin.graduadmin.app.AppController;
import  com.gradu.admin.graduadmin.helper.SQLiteHandler;
import  com.gradu.admin.graduadmin.helper.SessionManager;

public class AdminLoginActivity extends Activity {
    private static final String TAG = AdminLoginActivity.class.getSimpleName();
    private Button btnLogin;
    private EditText inputAdminUsername;
    private EditText inputPassword;
    private Button btnBack;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        inputAdminUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnSignin);
        btnBack = (Button) findViewById(R.id.btnBack);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if admin is already logged in or not
        if (session.isLoggedIn()) {
            // admin is already logged in. Take him to main activity
            Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String adminUsername = inputAdminUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!adminUsername.isEmpty() && !password.isEmpty()) {
                    // login admin
                    checkLogin(adminUsername, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginActivity.this, MainNavigationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String adminUsername, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADMIN_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // admin successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        JSONObject user = jObj.getJSONObject("user");
                        String adminName = user.getString("admin_name");
                        String adminUsername = user.getString("admin_username");

                        // Inserting row in users table
                        db.addAdmin(adminName, adminUsername);

                        // Launch main activity
                        Intent intent = new Intent(AdminLoginActivity.this,
                                AdminMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("admin_username", adminUsername);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
