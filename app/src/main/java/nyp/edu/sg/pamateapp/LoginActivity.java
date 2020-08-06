/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nyp.edu.sg.pamateapp.bottom_navi.HomeActivity;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.InputValidation;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.Patient;
import nyp.edu.sg.pamateapp.sqlite.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    TextView tvCreateAccLink;
    Button btnLogin;

    FrameLayout layout_login;

    Patient p;

    InputValidation inputValidation;
    //    DatabaseHandler db;
    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize objects
        helper = new DbHelper(this);
//        db = new DatabaseHandler(this);
        inputValidation = new InputValidation(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout_login = (FrameLayout) findViewById(R.id.layout_login);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        tvCreateAccLink = (TextView) findViewById(R.id.tvCreateAccLink);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyLoginDetails();

            }
        });
        tvCreateAccLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreateAccIntent = new Intent(LoginActivity.this, CreateAccActivity.class);
                startActivity(toCreateAccIntent);
            }
        });
    }

    private void verifyLoginDetails() {
        if (!inputValidation.editTextFilled(txtUsername, getString(R.string.error_message_username))) {
            return;
        } else if (!inputValidation.editTextFilled(txtPassword, getString(R.string.error_message_password))) {
            return;
        } else {
            Threadings.runInBackground(new Runnable() {
                @Override
                public void run() {
                    final boolean exist = helper.checkPatient(txtUsername.getText().toString().trim(),
                            txtPassword.getText().toString().trim());

                    Threadings.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            if (exist) {
                                Session session = ((MyApplication) getApplication()).getSession();
                                session.setLoggedIn(txtUsername.getText().toString().trim());

                                ((MyApplication) getApplication()).setSession(session);

                                SharedPreferences preferences =
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putString("username", txtUsername.getText().toString());
                                editor.apply();

                                Intent toDbIntent = new Intent(LoginActivity.this,
                                        HomeActivity.class);

                                emptyEditText();

                                startActivity(toDbIntent);

                                finish();
                            } else {
                                Snackbar.make(layout_login,
                                        getString(R.string.error_message_username_password),
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }

    private void emptyEditText() {
        txtUsername.setText(null);
        txtPassword.setText(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
