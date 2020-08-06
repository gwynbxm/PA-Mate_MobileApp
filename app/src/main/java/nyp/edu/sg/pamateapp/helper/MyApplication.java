/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.app.Application;

import com.evernote.android.job.JobManager;

import nyp.edu.sg.pamateapp.MainActivity;

/**
 * Created by GBXM on 21/12/2017.
 */

public class MyApplication extends Application {
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        session = new Session(this);
        JobManager.create(this).addJobCreator(new DemoJobCreater());
    }
}
