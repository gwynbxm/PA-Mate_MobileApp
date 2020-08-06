/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nyp.edu.sg.pamateapp.bottom_navi.DashboardActivity;

/**
 * Created by GBXM on 6/2/2018.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Date> dateArrayList;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        dateArrayList = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -i);

            dateArrayList.add(calendar.getTime());
        }
    }

    @Override
    public Fragment getItem(int position) {
        Date date = dateArrayList.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",
                Locale.US);

        return DashboardActivity.newInstance(simpleDateFormat.format(date));
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Date date = dateArrayList.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM",
                Locale.US);
        return simpleDateFormat.format(date);
    }
}
