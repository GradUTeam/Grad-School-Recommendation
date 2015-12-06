package com.gradu.admin.graduadmin;

import com.gradu.admin.graduadmin.activity.AddUnivInfo;
import com.gradu.admin.graduadmin.activity.AdminLoginActivity;
import com.gradu.admin.graduadmin.activity.EditUnivInfo;
import com.gradu.admin.graduadmin.helper.SQLiteHandler;
import com.gradu.admin.graduadmin.helper.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMainActivity extends Activity {

    private TextView txtAdminName;
    private TextView txtAdminUsername;
    private Button btnAddUnivInfo;
    //private Button btnEditUnivInfo;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutAdmin();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        txtAdminName = (TextView) findViewById(R.id.adminName);
        txtAdminUsername = (TextView) findViewById(R.id.adminUsername);
        btnAddUnivInfo = (Button) findViewById(R.id.btnAddUnivInfo);
        //btnEditUnivInfo = (Button) findViewById(R.id.btnEditUnivInfo);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getAdminDetails();

        String adminName = user.get("admin_name");
        String adminUsername = user.get("admin_username");

        // Displaying the user details on the screen
        txtAdminName.setText(adminName);
        txtAdminUsername.setText(adminUsername);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutAdmin();
            }
        });

        // Add University Info button click event
        btnAddUnivInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addUnivInfo();
            }
        });

        // Edit University Info button click event
        /*btnEditUnivInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editUnivInfo();
            }
        });*/
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutAdmin() {
        session.setLogin(false);

        db.deleteAdmins();

        // Launching the login activity
        Intent intent = new Intent(AdminMainActivity.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void addUnivInfo() {
        // Launching the AddUnivInfo activity
        Intent intent = new Intent(AdminMainActivity.this, AddUnivInfo.class);
        startActivity(intent);
        finish();
    }

    private void editUnivInfo() {
        // Launching the EditUnivInfo activity
        Intent intent = new Intent(AdminMainActivity.this, EditUnivInfo.class);
        startActivity(intent);
        finish();
    }
}