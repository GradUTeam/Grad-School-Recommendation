package com.gradu.admin.graduadmin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gradu.admin.graduadmin.MainActivity;
import com.gradu.admin.graduadmin.R;
import com.gradu.admin.graduadmin.app.AppConfig;
import com.gradu.admin.graduadmin.app.AppController;
import com.gradu.admin.graduadmin.helper.SQLiteHandler;
import com.gradu.admin.graduadmin.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputUsername;
    private EditText inputPassword;
    private Spinner inputCourseInterest = null;
    private EditText inputEngScore = null;
    private EditText inputGreQuant = null;
    private EditText inputGreVerbal = null;
    private Spinner inputGreAWA= null;
    private EditText inputUndergradGpa = null;
    private Spinner inputWorkExp = null;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.fullname);
        inputEmail = (EditText) findViewById(R.id.emailId);
        inputPassword = (EditText) findViewById(R.id.passwordset);
        inputUsername = (EditText) findViewById(R.id.usernameset);
        inputCourseInterest = (Spinner)findViewById(R.id.courseInterest);
        inputEngScore = (EditText)findViewById(R.id.engScore);
        inputGreQuant = (EditText)findViewById(R.id.greQuant);
        inputGreVerbal = (EditText)findViewById(R.id.greVerbal);
        inputGreAWA = (Spinner)findViewById(R.id.greAWA);
        inputUndergradGpa = (EditText)findViewById(R.id.undergradGpa);
        inputWorkExp = (Spinner)findViewById(R.id.workExp);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fullname = inputFullName.getText().toString().trim();
                String emailId = inputEmail.getText().toString().trim();
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String courseInterest = String.valueOf(inputCourseInterest.getSelectedItem());
                String engScore = inputEngScore.getText().toString();
                String greQuant = inputGreQuant.getText().toString();
                String greAWA =  String.valueOf(inputGreAWA.getSelectedItem());
                String greVerbal =  inputGreVerbal.getText().toString().trim();
                String undergradGpa =  inputUndergradGpa.getText().toString().trim();
                String workExp = String.valueOf(inputWorkExp.getSelectedItem());

                if (!fullname.isEmpty() && !username.isEmpty() && !emailId.isEmpty() && !password.isEmpty() &&
                !courseInterest.isEmpty() && !undergradGpa.isEmpty() && !greQuant.isEmpty() && !greVerbal.isEmpty() &&
                        !greAWA.isEmpty() && !engScore.isEmpty() && !workExp.isEmpty()) {
                    registerUser(fullname, emailId, username, password, courseInterest, undergradGpa, greQuant, greVerbal, greAWA, engScore,workExp );
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String fullname, final String emailId,
                              final String username, final String password, final String courseInterest, final String undergradGpa,
                              final String greQuant, final String greVerbal, final String greAWA, final String engScore, final String workExp)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("user");
                        //String fullname = user.getString("fullname");
                        //String emailId = user.getString("emailId");
                        String username = user.getString("username");
                        //String courseInterest = user.getString("courseInterest");
                     /*   String undergradGpa = user.getString("undergradGpa");
                        String greQuant = user.getString("greQuant");
                        String greVerbal = user.getString("greVerbal");
                        String greAWA = user.getString("greAWA");
                        String engScore = user.getString("engScore");
                        String workExp = user.getString("workExp");*/

                        // Inserting row in users table
                        db.addUser(username);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname", fullname);
                params.put("username", username);
                params.put("emailId", emailId);
                params.put("password", password);
                params.put("courseInterest", courseInterest);
                params.put("undergradGpa", undergradGpa);
                params.put("greQuant", greQuant);
                params.put("greVerbal", greVerbal);
                params.put("greAWA", greAWA);
                params.put("engScore", engScore);
                params.put("workExp", workExp);

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
