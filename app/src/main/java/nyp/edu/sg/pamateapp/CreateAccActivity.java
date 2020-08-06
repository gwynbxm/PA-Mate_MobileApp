/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.InputValidation;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.Patient;

public class CreateAccActivity extends AppCompatActivity {

    EditText tvName,
            tvDOB,
            tvHeight,
            tvWeight,
            tvUsername,
            tvEmailAddress,
            tvPassword,
            tvConfirmPassword;

    RadioButton rbtnMale,
            rbtnFemale;

    TextView tvLoginLink;

    Button btnNext;

    DatePickerDialog datePickerDOB;
    SimpleDateFormat dateFormat;

    ScrollView layout_create_acc;

    InputValidation inputValidation;

    //    DatabaseHandler db;
    DbHelper helper;
    Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        //initialize objects

        helper = new DbHelper(this);
//        db = new DatabaseHandler(this);
        inputValidation = new InputValidation(this);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        //get back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize views
        layout_create_acc = (ScrollView) findViewById(R.id.layout_create_acc);
        tvName = (EditText) findViewById(R.id.txtCreateName);
        tvDOB = (EditText) findViewById(R.id.txtCreateDOB);
        tvHeight = (EditText) findViewById(R.id.txtCreateHeight);
        tvWeight = (EditText) findViewById(R.id.txtCreateWeight);
        rbtnMale = (RadioButton) findViewById(R.id.rbtnMale);
        rbtnFemale = (RadioButton) findViewById(R.id.rbtnFemale);
        tvEmailAddress = (EditText) findViewById(R.id.txtCreateEmailAddress);
        tvUsername = (EditText) findViewById(R.id.txtCreateUsername);
        tvPassword = (EditText) findViewById(R.id.txtCreatePassword);
        tvConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        btnNext = (Button) findViewById(R.id.btnNext);
        tvLoginLink = (TextView) findViewById(R.id.tvLoginLink);

        //display date picker dialog by calling show() method on dialog instance of edit text
        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDOB.setInputType(InputType.TYPE_NULL);
                if (v == tvDOB) {
                    setDOB();
                    datePickerDOB.show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPatientDetails();
            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginIntent = new Intent(CreateAccActivity.this, LoginActivity.class);
                startActivity(toLoginIntent);
            }
        });
    }

    /*
    * validate edit texts
    * create Patient into database
    */
    private void insertPatientDetails() {
        if (!inputValidation.editTextFilled(tvName, getString(R.string.error_message_name))) {
            return;
        } else if (!inputValidation.editTextFilled(tvDOB, getString(R.string.error_message_dob))) {
            return;
        } else if (!inputValidation.editTextFilled(tvHeight,
                getString(R.string.error_message_height))) {
            return;
        } else if (!inputValidation.editTextFilled(tvWeight,
                getString(R.string.error_message_weight))) {
            return;
        } else if (!inputValidation.editTextFilled(tvEmailAddress,
                getString(R.string.error_message_email)) ||
                !Patterns.EMAIL_ADDRESS.matcher
                        (tvEmailAddress.getText().toString().trim()).matches()) {
            return;
        } else if (!inputValidation.editTextFilled(tvUsername,
                getString(R.string.error_message_username))) {
            return;
        } else if (!inputValidation.editTextFilled(tvPassword,
                getString(R.string.error_message_password))) {
            return;
        } else if (!inputValidation.editTextFilled(tvConfirmPassword,
                getString(R.string.error_message_confirm_password))) {
            return;
        } else if (!inputValidation.editTextMatches(tvPassword, tvConfirmPassword)) {
            Snackbar.make(layout_create_acc,
                    getString(R.string.error_message_compare_password),
                    Snackbar.LENGTH_LONG).show();
            return;
        } else {
            Threadings.runInBackground(new Runnable() {
                @Override
                public void run() {
                    if (!helper.checkPatientEmailAndUsername(
                            tvEmailAddress.getText().toString().trim(),
                            tvUsername.getText().toString().trim())) {

                        Threadings.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                String name = tvName.getText().toString().trim();
                                String dob = tvDOB.getText().toString().trim();

                                String gender;
                                if (rbtnFemale.isChecked()) {
                                    gender = "Female";
                                } else {
                                    gender = "Male";
                                }

                                float height = Float.parseFloat(tvHeight.getText().toString().trim());
                                float weight = Float.parseFloat(tvWeight.getText().toString().trim());
                                String email = tvEmailAddress.getText().toString().trim();
                                String username = tvUsername.getText().toString().trim();
                                String password = tvPassword.getText().toString().trim();

                                Date date = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy",
                                        Locale.US);
                                String monthName = dateFormat.format(date);

                                p = new Patient(name, gender, dob, String.valueOf(height),
                                        String.valueOf(weight), email, username, password,
                                        "", "", monthName);

                                Threadings.runInBackground(new Runnable() {
                                    @Override
                                    public void run() {
                                        helper.createPatient(p);

                                        Threadings.postRunnable(new Runnable() {
                                            @Override
                                            public void run() {

                                                emptyEditText();

                                                showSuccessDialog();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    } else {
                        Snackbar.make(layout_create_acc,
                                getString(R.string.error_message_username_email_exist)
                                , Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /*
    * set date selected on the date picker and display on edit text field
    */
    private void setDOB() {
        tvDOB.setText(null);
        Calendar calendar = Calendar.getInstance();

        //to receive callback when the user sets the date
        datePickerDOB = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                tvDOB.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

    }

    private void emptyEditText() {
        tvName.setText(null);
        tvDOB.setText(null);
        tvHeight.setText(null);
        tvWeight.setText(null);
        tvEmailAddress.setText(null);
        tvUsername.setText(null);
        tvPassword.setText(null);
        tvConfirmPassword.setText(null);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //goes back to previous activity
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Created Successfully!");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.alertdialog_create_success, null);

        builder.setView(view);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //go back to login
                Intent intent = new Intent(CreateAccActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
