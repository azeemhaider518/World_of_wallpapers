package com.wasidnp.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wasidnp.Config;
import com.wasidnp.R;
import com.wasidnp.activities.MainActivity;
import com.wasidnp.fragments.FragmentCategory;
import com.wasidnp.fragments.FragmentPopular;
import com.wasidnp.fragments.FragmentRecent;
import com.wasidnp.fragments.Fragmentfiltering;
import com.wasidnp.utilities.AppBarLayoutBehavior;

public class FragmentTabRecent extends Fragment {

    private MainActivity mainActivity;
    private Toolbar toolbar;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    public static int single_tab = 1;
    public static int double_tab = 2;
    public static int tripple_tab = 3;
    public static int four_tab = 4;


    public FragmentTabRecent() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);

        AppBarLayout appBarLayout = view.findViewById(R.id.tab_appbar_layout);
        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new AppBarLayoutBehavior());

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setupToolbar();

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        if (Config.ENABLE_TAB_LAYOUT) {
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(viewPager);

                    tabLayout.getTabAt ( 0 ).setIcon(R.drawable.latest).setText ( R.string.tab_category);
                    tabLayout.getTabAt ( 1 ).setIcon(R.drawable.wallpaper).setText ( R.string.tab_recent ).select();
                    tabLayout.getTabAt ( 2 ).setIcon(R.drawable.rate).setText ( R.string.tab_popular);
                    tabLayout.getTabAt ( 3 ).setIcon(R.drawable.ic_star_outline).setText ( R.string.tab_trending);
                }
            });
        } else {
            tabLayout.setVisibility(View.GONE);
        }

        return view;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (Config.ENABLE_TAB_LAYOUT) {
                switch (position) {
                    case 0:
                        return new FragmentCategory();
                    case 1:
                        return new FragmentRecent();
                    case 2:
                        return new FragmentPopular ();
                    case 3:
                        return new Fragmentfiltering();
                }
            } else {
                switch (position) {
                    case 0:
                        return new FragmentRecent();
                }
            }
            return null;
        }

        @Override
        public int getCount() {

            if (Config.ENABLE_TAB_LAYOUT) {
                return four_tab;
            } else {
                return single_tab;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (Config.ENABLE_TAB_LAYOUT) {
                switch (position) {
                    case 0:
                        return getResources().getString(R.string.tab_recent);
                    case 1:
                        return getResources().getString(R.string.tab_category);
                    case 2:
                        return getResources().getString(R.string.tab_popular);
                    case 3:
                        return getResources().getString(R.string.tab_trending);
                }
            } else {
                switch (position) {
                    case 0:
                        return getResources().getString(R.string.tab_recent);
                }
            }
            return null;
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }
    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.app_name));
        if (Config.ENABLE_TAB_LAYOUT) {
            Log.d("Log", "Tab Layout is Enabled");
        } else {
            toolbar.setSubtitle(getString(R.string.drawer_recent));
        }
            mainActivity.setSupportActionBar(toolbar);
    }

}

