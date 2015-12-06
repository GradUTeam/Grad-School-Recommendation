package com.gradu.admin.graduadmin.activity;

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
import com.gradu.admin.graduadmin.AdminMainActivity;
import com.gradu.admin.graduadmin.R;
import com.gradu.admin.graduadmin.app.AppConfig;
import com.gradu.admin.graduadmin.app.AppController;
import com.gradu.admin.graduadmin.helper.SQLiteHandler;
import com.gradu.admin.graduadmin.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUnivInfo extends ActionBarActivity {
    private static final String TAG = AddUnivInfo.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;

    private Button btnSubmit;
    private Button btnHome;
    private EditText inputUnivName;
    private EditText inputUnivDesc;
    private Spinner inputUnivLocn;
    private EditText inputTuitionFees;
    private Spinner inputResearchOppt;
    private EditText inputAcceptanceRate;
    private EditText inputCompSciRank;
    private EditText inputElectRank;
    private EditText inputCivilRank;
    private EditText inputMisRank;
    private EditText inputMechRank;
    private EditText inputNationalRank;
    private EditText inputGpa_actual;
    private EditText inputGreQuant;
    private EditText inputGreVerbal;
    private EditText inputGreAwa;
    private EditText inputToeflScore;
    private EditText inputIeltsScore;
    private EditText inputWorkExp;
    private EditText inputPaperPub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutAdmin();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_univ_info);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnHome = (Button) findViewById(R.id.btnHome);

        inputUnivName = (EditText) findViewById(R.id.univName);
        inputUnivDesc = (EditText) findViewById(R.id.univDesc);
        inputUnivLocn = (Spinner) findViewById(R.id.univLocn);
        inputTuitionFees = (EditText) findViewById(R.id.tuition_fees);
        inputResearchOppt = (Spinner) findViewById(R.id.resOpp);
        inputAcceptanceRate = (EditText) findViewById(R.id.acceptanceRate);
        inputCompSciRank = (EditText) findViewById(R.id.compsci);
        inputElectRank = (EditText) findViewById(R.id.elect);
        inputCivilRank = (EditText) findViewById(R.id.civil);
        inputMisRank = (EditText) findViewById(R.id.mis);
        inputMechRank = (EditText) findViewById(R.id.mech);
        inputNationalRank = (EditText) findViewById(R.id.national);
        inputGpa_actual = (EditText) findViewById(R.id.gpa_actual);
        inputGreQuant = (EditText) findViewById(R.id.greQuant);
        inputGreVerbal = (EditText) findViewById(R.id.greVerbal);
        inputGreAwa = (EditText) findViewById(R.id.greAwa);
        inputToeflScore = (EditText) findViewById(R.id.toeflScore);
        inputIeltsScore = (EditText) findViewById(R.id.ieltsScore);
        inputWorkExp = (EditText) findViewById(R.id.workEx);
        inputPaperPub = (EditText) findViewById(R.id.paperPublished);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String univName = inputUnivName.getText().toString().trim();
                String univDesc = inputUnivDesc.getText().toString().trim();
                String univLocn = inputUnivLocn.getSelectedItem().toString().trim();
                String tuitionFees = inputTuitionFees.getText().toString().trim();
                String researchOppt = inputResearchOppt.getSelectedItem().toString();
                String acceptanceRate = inputAcceptanceRate.getText().toString().trim();
                String compSciRank = inputCompSciRank.getText().toString().trim();
                String electRank = inputElectRank.getText().toString().trim();
                String civilRank = inputCivilRank.getText().toString().trim();
                String misRank = inputMisRank.getText().toString().trim();
                String mechRank = inputMechRank.getText().toString().trim();
                String nationalRank = inputNationalRank.getText().toString().trim();
                String gpa_actual = inputGpa_actual.getText().toString().trim();
                String greQuant = inputGreQuant.getText().toString().trim();
                String greVerbal = inputGreVerbal.getText().toString().trim();
                String greAwa = inputGreAwa.getText().toString().trim();
                String toeflScore = inputToeflScore.getText().toString().trim();
                String ieltsScore = inputIeltsScore.getText().toString().trim();
                String workExp = inputWorkExp.getText().toString().trim();
                String paperPub = inputPaperPub.getText().toString().trim();

                if( !univName.isEmpty() && !univDesc.isEmpty() && !univLocn.isEmpty() && !tuitionFees.isEmpty()
                        && !researchOppt.isEmpty() && !acceptanceRate.isEmpty() && !compSciRank.isEmpty()
                        && !electRank.isEmpty() && !civilRank.isEmpty() && !misRank.isEmpty() && !mechRank.isEmpty()
                        && !nationalRank.isEmpty() && !gpa_actual.isEmpty() && !greQuant.isEmpty() && !greVerbal.isEmpty()
                        && !greAwa.isEmpty() && !toeflScore.isEmpty() && !ieltsScore.isEmpty() && !workExp.isEmpty() && !paperPub.isEmpty()){
                    if(researchOppt.equals("Select")){
                        researchOppt = "Not Available";
                    }
                    if((Integer.parseInt(greQuant) <= 160) && (Integer.parseInt(greVerbal) <= 160) && (Integer.parseInt(greAwa) <= 6)
                            && (Integer.parseInt(gpa_actual) <= 10) && (Integer.parseInt(toeflScore) <= 120) && (Integer.parseInt(ieltsScore) <= 10)
                            && (Integer.parseInt(workExp) < 10) && Integer.parseInt(tuitionFees) < 999999) {
                        if(univLocn.equals("Select")) {
                            Toast.makeText(getApplicationContext(), "Please Select a State!", Toast.LENGTH_LONG).show();
                        } else {
                            addUnivInfo(univName, univDesc, univLocn, tuitionFees, researchOppt, acceptanceRate,
                                    compSciRank, electRank, civilRank, misRank, mechRank, nationalRank, gpa_actual,
                                    greQuant, greVerbal, greAwa, toeflScore, ieltsScore, workExp, paperPub);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Correct Details!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter All Details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_univ_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToHome() {
        // Launching the EditUnivInfo activity
        Intent intent = new Intent(AddUnivInfo.this, AdminMainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutAdmin() {
        session.setLogin(false);

        db.deleteAdmins();

        // Launching the login activity
        Intent intent = new Intent(AddUnivInfo.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void addUnivInfo(final String univName, final String univDesc, final String univLocn,
                             final String tuitionFees, final String researchOppt, final String acceptanceRate,
                             final String compSciRank, final String electRank, final String civilRank,
                             final String misRank, final String mechRank, final String nationalRank,
                             final String gpa_actual, final String greQuant, final String greVerbal,
                             final String greAwa, final String toeflScore, final String ieltsScore,
                             final String workExp, final String paperPub) {
        String tag_string_req = "req_univ_info";
        pDialog.setMessage("Saving University Details...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_UNIV_INFO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "University Information Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getApplicationContext(), "Univ info successfully submitted!", Toast.LENGTH_LONG).show();

                        // Launch Main activity
                        Intent intent = new Intent(
                                AddUnivInfo.this,
                                AdminMainActivity.class);
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
                Log.e(TAG, "Submit University Details Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("univName", univName);
                params.put("univDesc", univDesc);
                params.put("univLocn", univLocn);
                params.put("tuitionFees", tuitionFees);
                params.put("researchOppt", researchOppt);
                params.put("acceptanceRate", acceptanceRate);
                params.put("compSciRank", compSciRank);
                params.put("electRank", electRank);
                params.put("civilRank", civilRank);
                params.put("misRank", misRank);
                params.put("mechRank", mechRank);
                params.put("nationalRank", nationalRank);
                params.put("gpa_actual", gpa_actual);
                params.put("greQuant", greQuant);
                params.put("greVerbal", greVerbal);
                params.put("greAwa", greAwa);
                params.put("toeflScore", toeflScore);
                params.put("ieltsScore", ieltsScore);
                params.put("workExp", workExp);
                params.put("paperPub", paperPub);

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