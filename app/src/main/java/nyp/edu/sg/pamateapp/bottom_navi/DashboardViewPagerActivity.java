/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.bottom_navi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.helper.MyPagerAdapter;

public class DashboardViewPagerActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.activity_item_dashboard, container, false);
        ViewPager pager = (ViewPager) result.findViewById(R.id.db_Pager);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) result.findViewById(R.id.db_pager_header);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);

        pager.setAdapter(buildAdapter());
        pager.setCurrentItem(6);

        return (result);

    }

    private PagerAdapter buildAdapter() {
        return (new MyPagerAdapter(getChildFragmentManager()));
    }
}
