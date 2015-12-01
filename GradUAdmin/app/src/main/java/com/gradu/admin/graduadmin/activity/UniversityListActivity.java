package com.gradu.admin.graduadmin.activity;

/**
 * Created by akash mandole
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gradu.admin.graduadmin.R;
import com.gradu.admin.graduadmin.app.AppConfig;
import com.gradu.admin.graduadmin.app.AppController;
import com.gradu.admin.graduadmin.helper.SQLiteHandler;
import com.gradu.admin.graduadmin.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UniversityListActivity extends Activity {
    private static final String TAG = UniversityListActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private Button btnSunyBuffalo;
    private Button btncsula;
    private Button btnasu;
    private Button btnstanford;
    private Button btnrit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_list);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        btnSunyBuffalo = (Button) findViewById(R.id.btnsunybuffalo);
        btnasu = (Button) findViewById(R.id.btnasu);
        btnrit = (Button) findViewById(R.id.btnrit);
        btnstanford = (Button) findViewById(R.id.btnstanford);
        btncsula = (Button) findViewById(R.id.btncsula);

        btnSunyBuffalo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                fetchUniversityId(((Button) view).getText().toString());
            }
        });

        btnasu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                fetchUniversityId(((Button) view).getText().toString());
            }
        });

        btnrit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                fetchUniversityId(((Button) view).getText().toString());
            }
        });

        btnstanford.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                fetchUniversityId(((Button) view).getText().toString());
            }
        });

        btncsula.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                fetchUniversityId(((Button) view).getText().toString());
            }
        });
    }

    private void fetchUniversityId(final String name) {
        {
            {
                // Tag used to cancel the request
                String tag_string_req = "req_fetch_university_data";

                pDialog.setMessage("Fetching university data...");
                showDialog();

                StringRequest strReq = new StringRequest(Method.POST,
                        AppConfig.URL_FETCH_UNIVERSITY_ID, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Fetch University id Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            // Check for error node in json
                            if (!error) {
                                JSONObject juniversity = jObj.getJSONObject("univId");
                                int univid = juniversity.getInt("univId");
                                System.out.println(univid);

                                Intent intent = new Intent(getApplicationContext(), UniversityInfoActivity.class);
                                intent.putExtra("univid", univid);
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
                        Log.e(TAG, "Fetching university list Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to login url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("univName", name);
                        return params;
                    }

                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            }
        }
    }

   /* private void fetchUniversityList() {
        {
            // Tag used to cancel the request
            String tag_string_req = "req_fetch_university_data";

            pDialog.setMessage("Fetching university data...");
            showDialog();

            StringRequest strReq = new StringRequest(Method.POST,
                    AppConfig.URL_FETCH_UNIVERSITY_LIST, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Fetch University List Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {

                            System.out.println(jObj.toString());
                            int count = jObj.getInt("count");
                            System.out.println(count);
                            JSONObject universityList = jObj.getJSONObject("universityList");
                            // System.out.println(universityList);
                            // System.out.println(universityList.length());

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
                    Log.e(TAG, "Fetching university list Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }
    }*/

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}