package com.gradu.admin.graduadmin.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gradu.admin.graduadmin.MainActivity;
import com.gradu.admin.graduadmin.R;

public class RecoResults extends Activity {

    private TextView myText = null;
    private Button backTohome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reco_results);

        LinearLayout l = (LinearLayout) findViewById(R.id.linear);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
//        myText = new TextView(this);
//        myText.setText("My Text");

//        lView.addView(myText);
        TextView txtView = (TextView) findViewById(R.id.textView);
        txtView.setText(message);
        backTohome = (Button) findViewById(R.id.bktohome);
        backTohome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(RecoResults.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
