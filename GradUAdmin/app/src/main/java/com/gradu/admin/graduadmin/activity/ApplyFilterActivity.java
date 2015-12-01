package com.example.pranshu.gradureco.activity;

/**
 * Created by dell on 29-11-2015.
 */
        import com.example.pranshu.gradureco.R;
        import com.example.pranshu.gradureco.app.AppController;
        import com.example.pranshu.gradureco.app.AppConfig;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.android.volley.Request.Method;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;



public class ApplyFilterActivity extends Activity {

    private Button search;
    private Button backTohome;
    private EditText Ranking_low;
    private EditText Ranking_high;
    private EditText tuition_low;
    private EditText tuition_high;
    private Spinner state_value;
    private ProgressDialog pDialog;
    JSONArray amb;
    JSONArray mod;
    JSONArray safe;
    String text="";
//
    private static final String TAG = ApplyFilterActivity.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applyfilter);

        Ranking_low = (EditText) findViewById(R.id.Ranking_text1);
        Ranking_high = (EditText) findViewById(R.id.Ranking_text2);
        tuition_low = (EditText) findViewById(R.id.tuition1);
        tuition_high = (EditText) findViewById(R.id.tuition2);
        search = (Button) findViewById(R.id.search);
        backTohome = (Button) findViewById(R.id.bktohome);
        state_value=(Spinner)findViewById(R.id.state_spinner);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

//        backTohome.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Intent intent = new Intent(ApplyFilterActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String Ranking_text1 = Ranking_low.getText().toString();
                String Ranking_text2 = Ranking_high.getText().toString();
                String tuition1 = tuition_low.getText().toString();
                String tuition2 = tuition_high.getText().toString();
                String state = state_value.getSelectedItem().toString();

                // Check for empty data in the form
                if (!Ranking_text1.isEmpty() && !Ranking_text2.isEmpty()&& !tuition1.isEmpty()&& !tuition2.isEmpty()) {
                    // login user
                    Apply_filter(Ranking_text1, Ranking_text2,tuition1,tuition2,state);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter appropriate values!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });



    }

    /**
     * function to verify login details in mysql db
     * */
    private void Apply_filter(final String Ranking_text1, final String Ranking_text2, final String tuition1, final String tuition2, final String state) {
        // Tag used to cancel the request
        String tag_string_req = "req_recommend";

        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_Recommend, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                          amb = jObj.getJSONArray("Amb");
                          mod = new JSONArray(jObj.get("Mod").toString());
                          safe = new JSONArray(jObj.get("Safe").toString());
//
                        text=text.concat("Ambitious:"+"\n\n");
                        for(int i =0;i<amb.length();i++){
                            text=text.concat(amb.get(i).toString()+"\n");
                        }
                        text=text.concat("\n");
                        text=text.concat("Moderate:"+"\n\n");
                        for(int i =0;i<mod.length();i++){
                            text=text.concat(mod.get(i).toString()+"\n");
                        }
                        text=text.concat("\n");
                        text=text.concat("Safe:"+"\n\n");
                        for(int i =0;i<safe.length();i++){
                            text=text.concat(safe.get(i).toString()+"\n");
                        }

                        Intent intent = new Intent(ApplyFilterActivity.this,
                                RecoResults.class);
                        intent.putExtra("message", text);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                //SQLiteHandler logedIn_user = new SQLiteHandler();
                Map<String, String> params = new HashMap<String, String>();
                //params.put("uid", logedIn_user.getUserDetails().get("username"));
                params.put("uid", "1");
                params.put("rank_low", Ranking_text1);
                params.put("rank_high", Ranking_text2);
                params.put("tuition_low", tuition1);
                params.put("tuition_high", tuition2);
                params.put("state", state);

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