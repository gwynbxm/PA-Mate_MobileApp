/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nyp.edu.sg.pamateapp.bottom_navi.HomeActivity;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnCreateAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCreateAcc = (Button) findViewById(R.id.btnCreateAccount);

        if(((MyApplication) this.getApplication()).getSession().getLoggedIn() != null ){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccActivity.class);
                startActivity(intent);
            }
        });
    }
}
