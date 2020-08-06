/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by GBXM on 15/1/2018.
 */

public class Threadings {
    public static void delay(final long ms, final Runnable runnable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }).start();
    }

    public static void postRunnable(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runInBackground(final Runnable runnable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }).start();
    }
}
