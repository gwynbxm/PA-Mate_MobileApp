/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nyp.edu.sg.pamateapp.models.ExerciseDetails;
import nyp.edu.sg.pamateapp.models.ExerciseInstructions;
import nyp.edu.sg.pamateapp.models.ExercisePerformance;
import nyp.edu.sg.pamateapp.models.ExercisePlan;
import nyp.edu.sg.pamateapp.models.Notifications;
import nyp.edu.sg.pamateapp.models.Patient;

/**
 * Created by GBXM on 9/1/2018.
 */

public class DbHelper {

    private MobileServiceTable<Patient> patientTable;
    private MobileServiceTable<ExercisePlan> exercisePlanTable;
    private MobileServiceTable<ExerciseDetails> exerciseDetailsTable;
    private MobileServiceTable<ExercisePerformance> exercisePerformanceTable;
    private MobileServiceTable<Notifications> notificationsTable;
    private MobileServiceTable<ExerciseInstructions> exerciseInstructionsTable;

    public DbHelper(Context context) {
        try {
            MobileServiceClient mobileServiceClient = new MobileServiceClient(
                    "https://pamatemobileapp.azurewebsites.net",
                    context
            );
            patientTable = mobileServiceClient.getTable(Patient.class);
            exercisePlanTable = mobileServiceClient.getTable(ExercisePlan.class);
            exerciseDetailsTable = mobileServiceClient.getTable(ExerciseDetails.class);
            exercisePerformanceTable = mobileServiceClient.getTable(ExercisePerformance.class);
            notificationsTable = mobileServiceClient.getTable(Notifications.class);
            exerciseInstructionsTable = mobileServiceClient.getTable(ExerciseInstructions.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create Patient details into Patient Table
     *
     * @param patient
     */
    public void createPatient(final Patient patient) {
        try {
            patientTable.insert(patient).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve patient details from Patient Table
     *
     * @param username
     */
    public Patient getPatientDetails(final String username) {

        try {
            List<Patient> details = patientTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();

            if (details.size() > 0) {
                return details.get(0);
            } else {
                return null;
            }
        } catch (InterruptedException
                | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Update patient details to Patient Table
     *
     * @param username
     * @param name
     * @param gender
     * @param dob
     * @param height
     * @param weight
     * @param email
     * @param password
     */
    public void updatePatient(String username, String name,
                              String gender, String dob, String height, String weight,
                              String email, String password) {

        List<Patient> details;

        Patient p;

        try {
            details = patientTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();

            if (details.size() > 0) {

                p = details.get(0);
                p.setPatientName(name);
                p.setPatientGender(gender);
                p.setPatientDOB(dob);
                p.setPatientHeight(height);
                p.setPatientWeight(weight);
                p.setPatientEmail(email);
                p.setPatientPassword(password);
                p.setPatientUsername(username);

                patientTable.update(p).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check for duplications of email address and username
     *
     * @param email
     * @param username
     */
    public boolean checkPatientEmailAndUsername(final String email, final String username) {
        try {
            List<Patient> details = patientTable
                    .where().field("patientEmail")
                    .eq(email)
                    .or()
                    .field("patientUsername")
                    .eq(username)
                    .execute().get();

            if (details.size() > 0) {
                return true;
            }
        } catch (InterruptedException
                | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if Patient exists in Patient table
     *
     * @param username = Patient Login Username
     * @param password = Patient Login Password
     */
    public boolean checkPatient(final String username, final String password) {
        try {
            List<Patient> details = patientTable
                    .where().field("patientUsername")
                    .eq(username)
                    .and()
                    .field("patientPassword")
                    .eq(password)
                    .execute().get();

            if (details.size() > 0) {
                return true;
            }

        } catch (InterruptedException
                | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update Patient Login Datetime when Sign in
     *
     * @param username = Patient Login Username
     * @param dtLogin  = Record Current Date Time
     */
    public void updateLoginDT(String username, String dtLogin) {
        List<Patient> details;

        Patient p = null;

        try {
            details = patientTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();

            if (details.size() > 0) {

                p = details.get(0);
                p.setDtLogin(dtLogin);

                patientTable.update(p).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Patient Logout Datetime when Log out
     *
     * @param username  = Patient Login Username
     * @param dtLogout= Record Current Date Time
     */
    public void updateLogoutDT(String username, String dtLogout) {
        List<Patient> details;

        Patient p = null;

        try {
            details = patientTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();

            if (details.size() > 0) {

                p = details.get(0);
                p.setDtLogout(dtLogout);

                patientTable.update(p).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a particular patient
     * their list of Exercise Plans from Exercise Plan table
     *
     * @param username
     */
    public ArrayList<ExercisePlan> getPatientPlans(String username) {
        ArrayList<ExercisePlan> details;
        try {
            details = exercisePlanTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();

            if (details.size() > 0) {
                return details;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve the Exercise Plan Details according to the planId
     *
     * @param planId
     */
    public ExerciseDetails getPlanDetail(String planId) {
        try {
            List<ExerciseDetails> details = exerciseDetailsTable
                    .where()
                    .field("exercisePlanId")
                    .eq(planId)
                    .execute()
                    .get();
            if (details.size() > 0) {
                return details.get(0);
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check the ListView position with the ExerciseDetailsId
     *
     * @param planId
     */
    public boolean checkExercisePlan(String planId) {
        try {
            List<ExerciseDetails> details = exerciseDetailsTable
                    .where()
                    .field("exercisePlanId")
                    .eq(planId)
                    .execute()
                    .get();
            if (details.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Check if plan has been completed
     *
     * @param planId
     */
    public boolean checkPlanDone(String planId) {
        try {
            List<ExercisePerformance> details = exercisePerformanceTable
                    .where()
                    .field("exercisePlanId")
                    .eq(planId)
                    .execute()
                    .get();
            if (details.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Check today's Exercise Performance
     *
     * @param date
     **/
    public boolean checkLatestExercisePerformance(String date) {
        try {
            List<ExercisePerformance> details = exercisePerformanceTable
                    .where()
                    .field("startDate")
                    .eq(date)
                    .execute()
                    .get();
            if (details.size() > 0) {
                return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Retrieve a particular patient's
     * exercise performance(s) from Exercise Performance Table
     *
     * @param username
     */
    public ArrayList<ExercisePerformance> getAllExercisePerformance(String username) {
        ArrayList<ExercisePerformance> details;
        try {
            details = exercisePerformanceTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();
            if (details.size() > 0) {
                return details;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve a particular patient latest Exercise Performance
     *
     * @param username
     * @param date
     */
    public ExercisePerformance getExerciseLatestPerformance(String username, String date) {

        List<ExercisePerformance> details;
        try {
            details = exercisePerformanceTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .and()
                    .field("startDate")
                    .eq(date).execute().get();
            if (details.size() > 0) {
                return details.get(0);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve Exercise Plan that needs to be fulfilled today
     *
     * @param username
     * @param date
     */
    public ExercisePlan getExercisePlan(String username, String date) {
        List<ExercisePlan> details;
        try {
            details = exercisePlanTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .and()
                    .field("planStartDate")
                    .eq(date).execute().get();
            if (details.size() > 0) {
                return details.get(0);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create Notifications for a particular patient
     *
     * @param title
     * @param description
     * @param date
     * @param username
     */
    public void createNotifications(String title, String description,
                                    String date, String username) {

        Notifications n = new Notifications(title, description, date, username);

        try {
            notificationsTable.insert(n).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all Notifications for a particular patient
     *
     * @param username
     */
    public ArrayList<Notifications> getAllNotifications(String username) {
        ArrayList<Notifications> details;
        try {
            details = notificationsTable
                    .where()
                    .field("patientUsername")
                    .eq(username)
                    .execute()
                    .get();
            if (details.size() > 0) {
                return details;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ExerciseInstructions> getAllExerciseVideos() {
        ArrayList<ExerciseInstructions> details;
        try {
            details = exerciseInstructionsTable
                    .select()
                    .execute()
                    .get();
            if (details.size() > 0) {
                return details;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
