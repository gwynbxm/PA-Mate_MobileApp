/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.InputValidation;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.Patient;
import nyp.edu.sg.pamateapp.sqlite.DatabaseHandler;

public class EditProfileActivity extends AppCompatActivity {

    ImageView imgProfile;

    EditText txtEditName,
            txtEditBirthday,
            txtEditHeight,
            txtEditWeight,
            txtEditUsername,
            txtEditEmailAdd,
            txtEditPassword,
            txtEditConfirmPassword;

    RadioButton rbtnMale,
            rbtnFemale;

    Button btnChangeImg,
            btnSaveProfile;

    ScrollView layout_edit_profile;

    //DatabaseHandler db;
    Patient p;
    DbHelper helper;
    InputValidation inputValidation;

    DatePickerDialog datePickerDOB;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //db = new DatabaseHandler(this);
        helper = new DbHelper(this);
        p = new Patient();
        inputValidation = new InputValidation(this);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        //get back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //instantiate views
        imgProfile = (ImageView) findViewById(R.id.imgEditProfile);
        btnChangeImg = (Button) findViewById(R.id.btnEditImg);

        txtEditName = (EditText) findViewById(R.id.txtEditName);
        txtEditBirthday = (EditText) findViewById(R.id.txtEditBirthday);
        rbtnMale = (RadioButton) findViewById(R.id.rbtnEditMale);
        rbtnFemale = (RadioButton) findViewById(R.id.rbtnEditFemale);
        txtEditHeight = (EditText) findViewById(R.id.txtEditHeight);
        txtEditWeight = (EditText) findViewById(R.id.txtEditWeight);
        txtEditUsername = (EditText) findViewById(R.id.txtEditUsername);
        txtEditEmailAdd = (EditText) findViewById(R.id.txtEditEmailAdd);
        txtEditPassword = (EditText) findViewById(R.id.txtEditPassword);
        txtEditConfirmPassword = (EditText) findViewById(R.id.txtEditConfirmPassword);

        layout_edit_profile = (ScrollView) findViewById(R.id.layout_edit_profile);

        btnSaveProfile = (Button) findViewById(R.id.btnSaveProfile);

        //retrieve from database and display text fields
        //convert application to MyApplication and get the session variable out
        Session session = ((MyApplication) this.getApplication()).getSession();
        //get session and store into a variable
        final String username = session.getLoggedIn();
        //call get Patient method from database and store to Patient variable

        retrievePatientDetails(username);
    }

    public void retrievePatientDetails(final String username) {
        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                p = helper.getPatientDetails(username);
                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        //display all Patient details according to the session username
                        txtEditName.setText(String.valueOf(p.getPatientName()));
                        txtEditBirthday.setText(String.valueOf(p.getPatientDOB()));

                        if (p.getPatientGender().equals("Male")) {
                            rbtnMale.setChecked(true);
                        } else {
                            rbtnFemale.setChecked(true);
                        }

                        txtEditHeight.setText(String.valueOf(p.getPatientHeight()));
                        txtEditWeight.setText(String.valueOf(p.getPatientWeight()));
                        txtEditUsername.setText(p.getPatientUsername());
                        txtEditEmailAdd.setText(p.getPatientEmail());
                        txtEditPassword.setText(p.getPatientPassword());
                        txtEditConfirmPassword.setText(p.getPatientPassword());

                        txtEditBirthday.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                txtEditBirthday.setInputType(InputType.TYPE_NULL);

                                if (v == txtEditBirthday) {
                                    datePickerDOB.show();
                                    setDOB();
                                }
                            }
                        });

                        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                insertPatientDetails();
                            }
                        });
                    }
                });
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

//    boolean canEdit = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //goes back to previous activity
                onBackPressed();
                return true;
            case R.id.edit_profile:
                txtEditName.setEnabled(true);
                rbtnMale.setEnabled(true);
                rbtnFemale.setEnabled(true);
                txtEditBirthday.setEnabled(true);
                txtEditHeight.setEnabled(true);
                txtEditWeight.setEnabled(true);
                txtEditUsername.setEnabled(true);
                txtEditPassword.setEnabled(true);
                txtEditConfirmPassword.setEnabled(true);
                btnSaveProfile.setEnabled(true);
                btnChangeImg.setEnabled(true);
//                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (canEdit) {

//            canEdit = false;
//        } else {

//            canEdit = true;
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

    /*
            * set date selected on the date picker and display on edit text field
            */
    private void setDOB() {
        txtEditBirthday.setText(null);

        Calendar calendar = Calendar.getInstance();

        //to receive callback when the user sets the date
        datePickerDOB = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                txtEditBirthday.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void insertPatientDetails() {
        if (!inputValidation.editTextFilled(txtEditName, getString(R.string.error_message_name))) {
            return;
        } else if (!inputValidation.editTextFilled(txtEditBirthday, getString(R.string.error_message_dob))) {
            return;
        } else if (!inputValidation.editTextFilled(txtEditHeight,
                getString(R.string.error_message_height))) {
            return;
        } else if (!inputValidation.editTextFilled(txtEditWeight,
                getString(R.string.error_message_weight))) {
            return;
        } else if (!inputValidation.editTextFilled(txtEditPassword, getString(R.string.error_message_password))) {
            return;
        } else if (!inputValidation.editTextFilled(txtEditConfirmPassword,
                getString(R.string.error_message_confirm_password))) {
            return;
        } else if (!inputValidation.editTextMatches(txtEditPassword, txtEditConfirmPassword)) {
            Snackbar.make(layout_edit_profile,
                    getString(R.string.error_message_compare_password),
                    Snackbar.LENGTH_LONG).show();
            return;
        } else {
            Threadings.runInBackground(new Runnable() {
                @Override
                public void run() {

                    String name = txtEditName.getText().toString().trim();
                    String dob = txtEditBirthday.getText().toString().trim();

                    String gender;
                    if (rbtnFemale.isChecked()) {
                        gender = "Female";
                    } else {
                        gender = "Male";
                    }

                    float height = Float.parseFloat(txtEditHeight.getText().toString().trim());
                    float weight = Float.parseFloat(txtEditWeight.getText().toString().trim());
                    String email = txtEditEmailAdd.getText().toString().trim();
                    String username = txtEditUsername.getText().toString().trim();
                    String password = txtEditPassword.getText().toString().trim();

                    p = new Patient();
                    //insert into database
//                    oolean isUpdated =
//                    db.updatePatient(p.getPatientName(), p.getPatientGender(),
//                            p.getPatientDOB(), Float.valueOf(p.getPatientHeight()),
//                            Float.valueOf(p.getPatientWeight()),
//                            p.getPatientEmail(), p.getPatientUsername(),
//                            p.getPatientPassword());

                    helper.updatePatient(username,
                            name,
                            gender,
                            dob,
                            String.valueOf(height),
                            String.valueOf(weight),
                            email,
                            password);

                    Snackbar.make(layout_edit_profile,
                            "Details updated successfully.",
                            Snackbar.LENGTH_LONG).show();

                    Threadings.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            txtEditName.setEnabled(false);
                            rbtnMale.setEnabled(false);
                            rbtnFemale.setEnabled(false);
                            txtEditBirthday.setEnabled(false);
                            txtEditHeight.setEnabled(false);
                            txtEditWeight.setEnabled(false);
                            txtEditUsername.setEnabled(false);
                            txtEditPassword.setEnabled(false);
                            txtEditConfirmPassword.setEnabled(false);
                            btnSaveProfile.setEnabled(false);
                            btnChangeImg.setEnabled(false);

                        }
                    });
                }
            });
        }
    }
}
