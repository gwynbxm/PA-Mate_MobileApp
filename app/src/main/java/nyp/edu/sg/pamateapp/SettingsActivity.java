/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Calendar;

import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.ShowNotificationJob;
import nyp.edu.sg.pamateapp.models.ExercisePlan;
import nyp.edu.sg.pamateapp.helper.AppCompatPreferenceActivity;

public class SettingsActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    private static DbHelper helper;
    private static ExercisePlan ep;
    private static Vibrator v;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        helper = new DbHelper(this);
        ep = new ExercisePlan();

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            final boolean setReminderSwitch = preferences.getBoolean("on_off_reminder_switch", false);
            final boolean setVibrationSwitch = preferences.getBoolean("on_off_vibration_switch", false);
            String setTime = preferences.getString("set_reminder_time", null);
            boolean setSync = preferences.getBoolean("perform_sync", false);
            String setSyncInterval = preferences.getString("sync_interval_list", null);

            // Reminder (ON/OFF)
            final SwitchPreference reminderSwitch =
                    (SwitchPreference) findPreference("on_off_reminder_switch");
            reminderSwitch.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object value) {
                            boolean isReminderOn = (Boolean) value;

                            if (isReminderOn) {
                                reminderSwitch.setSummary("ON");
                                reminderSwitch.setChecked(true);
                            } else {
                                reminderSwitch.setSummary("OFF");
                                reminderSwitch.setChecked(false);
                            }
                            setAlarm();
                            return false;
                        }
                    });

            // Vibration (ON/OFF)
            final SwitchPreference vibrationSwitch = (SwitchPreference)
                    findPreference("on_off_vibration_switch");
            vibrationSwitch.setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object value) {
                            boolean isVibrateOn = (Boolean) value;
                            if (isVibrateOn) {
                                vibrationSwitch.setSummary("ON");
                                vibrationSwitch.setChecked(true);
                            } else {
                                vibrationSwitch.setSummary("OFF");
                                vibrationSwitch.setChecked(false);
                            }
                            return false;
                        }
                    });

            // Set Reminder Time
            findPreference("set_reminder_time").setOnPreferenceChangeListener(
                    new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object value) {
                            findPreference("set_reminder_time").setSummary(value.toString());
                            editor.putString("set_reminder_time", value.toString());
                            editor.apply();

                            setAlarm();
                            return false;
                        }
                    });

            // Perform Sync
            final CheckBoxPreference performSync = (CheckBoxPreference)
                    findPreference("perform_sync");

            performSync.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    boolean isSyncEnabled = (Boolean) value;

                    if (isSyncEnabled) {
                        performSync.setSummary("Enabled");
                        performSync.setChecked(true);
                    } else {
                        performSync.setSummary("Disabled");
                        performSync.setChecked(false);
                    }
                    return false;
                }
            });

            // Set Sync Interval
            final ListPreference syncInterval = (ListPreference)
                    findPreference("sync_interval_list");
            syncInterval.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    syncInterval.setSummary(value.toString() + " minutes");
                    editor.putString("sync_interval_list", value.toString() + " minutes");
                    return false;
                }
            });

            // Navigate to Help Activity
            findPreference("information_help").setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            Intent helpIntent =
                                    new Intent(getActivity(),
                                            HelpActivity.class);
                            startActivity(helpIntent);
                            return false;
                        }
                    });

            // Navigate to About Activity
            findPreference("information_about").setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            Intent helpIntent =
                                    new Intent(getActivity(),
                                            AboutActivity.class);
                            startActivity(helpIntent);
                            return false;
                        }
                    });

            if (setReminderSwitch) {
                reminderSwitch.setSummary("ON");
            } else {
                reminderSwitch.setSummary("OFF");
            }

            if (setVibrationSwitch) {
                vibrationSwitch.setSummary("ON");
            } else {
                vibrationSwitch.setSummary("OFF");
            }

            if (setSync) {
                performSync.setSummary("Enabled");
            } else {
                performSync.setSummary("Disabled");
            }

        }

        private void setAlarm() {
            boolean setReminderSwitch = preferences.getBoolean("on_off_reminder_switch", false);
            boolean setVibrationSwitch = preferences.getBoolean("on_off_vibration_switch", false);
            String setTime = preferences.getString("set_reminder_time", null);

            cancelAlarm();

            long offset = 0;

            if (setReminderSwitch) {
                Calendar setCAL = Calendar.getInstance();
                int hour = getHour(String.valueOf(setTime));
                int minute = getMinute(String.valueOf(setTime));
                setCAL.set(Calendar.HOUR_OF_DAY, hour);
                setCAL.set(Calendar.MINUTE, minute);

                Calendar now = Calendar.getInstance();

                if (now.getTimeInMillis() < setCAL.getTimeInMillis()) {
                    offset = setCAL.getTimeInMillis() - now.getTimeInMillis();
                } else {
                    setCAL.add(Calendar.DAY_OF_YEAR, 1);
                    offset = setCAL.getTimeInMillis() - now.getTimeInMillis();
                }

                int id = new JobRequest.Builder(ShowNotificationJob.TAG)
                        .setExact(offset)
                        .build()
                        .schedule();
                editor.putInt("job_id", id);
                editor.apply();
            }
        }

        private int getHour(String time) {
            String[] pieces = time.split(":");

            if (pieces.length <= 1) {
                return 0;
            }

            return (Integer.parseInt(pieces[0]));
        }

        private int getMinute(String time) {
            String[] pieces = time.split(":");

            if (pieces.length <= 1) {
                return 0;
            }

            return (Integer.parseInt(pieces[1]));
        }

        private void cancelAlarm() {
            Integer jobId = preferences.getInt("job_id", 0);
            if (jobId != 0) {
                JobManager.instance().cancel(jobId);
            }
        }
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
