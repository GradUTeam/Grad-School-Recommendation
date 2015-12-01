package com.gradu.admin.graduadmin;

import com.gradu.admin.graduadmin.activity.LoginActivity;
import com.gradu.admin.graduadmin.activity.UniversityListActivity;
import com.gradu.admin.graduadmin.helper.SQLiteHandler;
import com.gradu.admin.graduadmin.helper.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtUsername;
    private Button btnLogout;
    private Button btnMyProfile;
    private Button btnGradSchoolRec;
    private Button btnUnivInfo;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtUsername = (TextView) findViewById(R.id.username);
        btnMyProfile = (Button) findViewById(R.id.btnMyProfile);
        btnGradSchoolRec = (Button) findViewById(R.id.btnGradSchoolRec);
        btnUnivInfo = (Button) findViewById(R.id.btnUnivInfo);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        //HashMap<String, String> user = db.getUserDetails();

        //String name = user.get("name");
        //String username = user.get("username");

        // Displaying the user details on the screen
        //txtName.setText(name);
        //txtUsername.setText(username);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

       /*btnMyProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnGradSchoolRec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GradSchoolRecActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        btnUnivInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UniversityListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        //db.deleteUsers();

        // Launching the login activity
        //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(intent);
        //finish();
    }
}