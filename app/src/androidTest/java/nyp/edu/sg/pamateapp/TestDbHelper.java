/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.models.Patient;

import static org.junit.Assert.assertEquals;

/**
 * Created by GBXM on 11/1/2018.
 */
@RunWith(AndroidJUnit4.class)
public class TestDbHelper {

    private static DbHelper dbHelper;

    @BeforeClass
    public static void init() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        dbHelper = new DbHelper(appContext);
    }

    @Test
    public void testGetPatientDetails() throws Exception {
        Patient patient = dbHelper.getPatientDetails("gwyngwyn");
        Assert.assertEquals("59", patient.getPatientWeight());
    }

    @Test
    public void testCreatePatient() throws Exception {
//        dbHelper.createPatient("wendy", "Female", "20-11-1999", 160, 49,
//                "wendy@gmail.com", "wendywendy", "123", "Jan 2018");
    }

}

