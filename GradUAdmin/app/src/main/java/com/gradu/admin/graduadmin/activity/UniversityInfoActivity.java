package com.example.akash.graduapplication.activity;

/**
 * Created by akash mandole
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.akash.graduapplication.R;
import com.example.akash.graduapplication.app.AppConfig;
import com.example.akash.graduapplication.app.AppController;
import com.example.akash.graduapplication.helper.SQLiteHandler;
import com.example.akash.graduapplication.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UniversityInfoActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private int uniid;
    private EditText nametext;
    private EditText descriptiontext;
    private EditText locationtext;
    private EditText nationaltext;
    private EditText computersciencetext;
    private EditText civiltext;
    private EditText electricaltext;
    private EditText mechanicaltext;
    private EditText mistext;
    private EditText tuitionfeetext;
    private EditText researchopportunitiestext;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_info);

        Bundle bundle = getIntent().getExtras();
        uniid = bundle.getInt("univid");

        nametext = (EditText) findViewById(R.id.nametext);
        descriptiontext = (EditText) findViewById(R.id.descriptiontext);;
        locationtext = (EditText) findViewById(R.id.locationtext);

        nationaltext = (EditText) findViewById(R.id.nationaltext);
        computersciencetext = (EditText) findViewById(R.id.computersciencetext);
        civiltext = (EditText) findViewById(R.id.civiltext);
        electricaltext = (EditText) findViewById(R.id.electricaltext);
        mechanicaltext = (EditText) findViewById(R.id.mechanicaltext);
        mistext = (EditText) findViewById(R.id.mistext);

        tuitionfeetext = (EditText) findViewById(R.id.tuitionfeetext);
        researchopportunitiestext = (EditText) findViewById(R.id.researchopportunitiestext);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // fetch data

        fetchData(uniid);
    }

    private void fetchData(final int uniid) {
        {
            // Tag used to cancel the request
            String tag_string_req = "req_fetch_university_data";

            pDialog.setMessage("Fetching university data...");
            showDialog();

            StringRequest strReq = new StringRequest(Method.POST,
                    AppConfig.URL_FETCH_UNIVERSITY_INFO, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Fetch University Info Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {
                            // user successfully logged in
                            // Create login session
                            session.setLogin(true);

                            // Now store the user in SQLite
                           // String uid = jObj.getString("uid");
                            JSONObject university = jObj.getJSONObject("university");

                            System.out.println(university);

                            String univName = university.getString("univName");
                            String univDescription = university.getString("univDescription");
                            String univLocation = university.getString("univLocation");
                            String univNationalRank = university.getString("univNationalRank");
                            String univCompScienceRank = university.getString("univCompScienceRank");
                            String univCivilRank = university.getString("univCivilEngRank");
                            String univElectricalRank = university.getString("univElecEngRank");
                            String univMechanicalRank = university.getString("univMechEngRank");
                            String univMisRank = university.getString("univMisRank");
                            String tuitionfee = university.getString("univCost");
                            String researchopportunities = university.getString("univResearchOpp");

                            nametext.setText(univName);
                            descriptiontext.setText(univDescription);
                            locationtext.setText(univLocation);
                            nationaltext.setText(univNationalRank);
                            computersciencetext.setText(univCompScienceRank);
                            civiltext.setText(univCivilRank);
                            electricaltext.setText(univElectricalRank);
                            mechanicaltext.setText(univMechanicalRank);
                            mistext.setText(univMisRank);
                            tuitionfeetext.setText(tuitionfee);
                            researchopportunitiestext.setText(researchopportunities);

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
                    Log.e(TAG, "Fetching university data Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uniid", String.valueOf(uniid));
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }
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