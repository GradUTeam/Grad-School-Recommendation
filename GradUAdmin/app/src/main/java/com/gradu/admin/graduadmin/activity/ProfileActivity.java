package com.example.kishore.grad.activity;

/**
 * Created by Kishore on 11/30/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.*;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.kishore.grad.R;
import com.example.kishore.grad.app.AppConfig;
import com.example.kishore.grad.app.AppController;
import com.example.kishore.grad.helper.SQLiteHandler;
import com.example.kishore.grad.helper.SessionManager;

public class ProfileActivity extends Activity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private Button btnUpdate;
    private EditText FullName;
    private EditText Username;
    private Spinner CourseInterest;
    private EditText EngScore;
    private EditText GreQuant;
    private EditText GreVerbal;
    private Spinner GreAWA;
    private EditText UndergradGpa;
    private Spinner WorkExp;
    private Button btnEditProfile;
    //private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FullName = (EditText) findViewById(R.id.fullname);
        Username = (EditText) findViewById(R.id.usernameset);
        CourseInterest = (Spinner) findViewById(R.id.courseInterest);
        EngScore = (EditText) findViewById(R.id.engScore);
        GreQuant = (EditText) findViewById(R.id.greQuant);
        GreVerbal = (EditText) findViewById(R.id.greVerbal);
        GreAWA = (Spinner) findViewById(R.id.greAWA);
        UndergradGpa = (EditText) findViewById(R.id.undergradGpa);
        WorkExp = (Spinner) findViewById(R.id.workExp);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        String usrname = "joerodgers";
        fetchUser(usrname);
        // Progress dialog
        //pDialog = new ProgressDialog(this);
        //pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            SQLiteHandler myHandler = new SQLiteHandler(ProfileActivity.this);
            //String usrname = myHandler.getUsername().toString();

            // User is already logged in. Take him to main activity
        }


        // Register Button Click event
        // Link to Login Screen


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fullname = FullName.getText().toString().trim();
                String username = Username.getText().toString().trim();
                String courseInterest = String.valueOf(CourseInterest.getSelectedItem());
                String engScore = EngScore.getText().toString();
                String greQuant = GreQuant.getText().toString();
                String greAWA = String.valueOf(GreAWA.getSelectedItem());
                String greVerbal = GreVerbal.getText().toString().trim();
                String undergradGpa = UndergradGpa.getText().toString().trim();
                String workExp = String.valueOf(WorkExp.getSelectedItem());
                btnUpdate = (Button) findViewById(R.id.btnUpdate);
                if (!fullname.isEmpty() && !username.isEmpty()) {
                    updateUser(fullname, username, courseInterest, undergradGpa, greQuant, greVerbal, greAWA, engScore, workExp);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void fetchUser(final String usrname )
    {
        SQLiteHandler myHandler = new SQLiteHandler(ProfileActivity.this);

        // Tag used to cancel the request
        String tag_string_req = "req_fetch";
        //pDialog.setMessage("Fetching ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FETCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Fetch Response: " + response);
               // hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        JSONObject user = jObj.getJSONObject("usr");
                        String fullname = user.getString("fullname");
                        String username = user.getString("username");
                        //String courseInterest = user.getString("courseInterest");
                        String undergradGpa = user.getString("undergradGpa");
                        String greQuant = user.getString("greQuant");
                        String greVerbal = user.getString("greVerbal");
                        //String greAWA = user.getString("greAWA");
                        String engScore = user.getString("engScore");
                        //String workExp = user.getString("workExp");

                        FullName.setText(fullname);
                        Username.setText(username);
                        CourseInterest.setSelection(1);
                        UndergradGpa.setText(undergradGpa);
                        GreQuant.setText(greQuant);
                        GreVerbal.setText(greVerbal);
                        GreAWA.setSelection(5);
                        EngScore.setText(engScore);
                        WorkExp.setSelection(1);
                        ;



                        // Inserting row in users table
                       // db.addUser(fullname, emailId,username, courseInterest);

                       /* Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();*/
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
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                params.put("usrname", usrname);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void updateUser(final String fullname, final String username, final String courseInterest, final String undergradGpa,
                              final String greQuant, final String greVerbal, final String greAWA, final String engScore, final String workExp)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_update";

        //pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response);
              //  hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        /*JSONObject user = jObj.getJSONObject("user");
                        String fullname = user.getString("fullname");
                        String emailId = user.getString("emailId");
                        String username = user.getString("username");
                        String courseInterest = user.getString("courseInterest");
                        String undergradGpa = user.getString("undergradGpa");
                        String greQuant = user.getString("greQuant");
                        String greVerbal = user.getString("greVerbal");
                        String greAWA = user.getString("greAWA");
                        String engScore = user.getString("engScore");
                        String workExp = user.getString("workExp");*/

                        // Inserting row in users table


                        Toast.makeText(getApplicationContext(), "User successfully updated.", Toast.LENGTH_LONG).show();

                        // Launch login activity
                       /* Intent intent = new Intent(
                                ProfileActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();*/
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
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname", fullname);
                params.put("username", username);
                params.put("undergradGpa", undergradGpa);
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
 /*   private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/
}