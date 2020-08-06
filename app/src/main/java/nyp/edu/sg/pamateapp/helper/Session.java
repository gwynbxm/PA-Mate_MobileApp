/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GBXM on 21/12/2017.
 */

public class Session {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoggedIn(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    public String getLoggedIn() {
        return preferences.getString("username", null);
    }

    public void logout(){
        editor.remove("username");
        editor.commit();
    }
}
