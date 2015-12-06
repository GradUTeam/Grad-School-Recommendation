package com.gradu.admin.graduadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gradu.admin.graduadmin.AdminMainActivity;
import com.gradu.admin.graduadmin.R;
import com.gradu.admin.graduadmin.helper.SQLiteHandler;
import com.gradu.admin.graduadmin.helper.SessionManager;

public class EditUnivInfo extends ActionBarActivity {
    private static final String TAG = AddUnivInfo.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;

    private Button btnSubmit;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutAdmin();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_univ_info);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnHome = (Button) findViewById(R.id.btnHome);

        fetchUnivInfo();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*editUnivInfo(univName, univDesc, univLocn, tuitionFees, researchOppt, acceptanceRate,
                        compSciRank, electRank, civilRank, misRank, mechRank, nationalRank, gpa_actual,
                        greQuant, greVerbal, greAwa, toeflScore, ieltsScore);*/
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
        getMenuInflater().inflate(R.menu.menu_edit_univ_info, menu);
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
        Intent intent = new Intent(EditUnivInfo.this, AdminMainActivity.class);
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
        Intent intent = new Intent(EditUnivInfo.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void fetchUnivInfo(){

    }
}
