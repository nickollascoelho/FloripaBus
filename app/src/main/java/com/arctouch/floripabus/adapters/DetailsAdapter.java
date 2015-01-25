package com.arctouch.floripabus.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arctouch.floripabus.activities.DetailsActivity;
import com.arctouch.floripabus.activities.fragments.DeparturesFragment;
import com.arctouch.floripabus.activities.fragments.StreetsFragment;


public class DetailsAdapter extends FragmentPagerAdapter {

    private DetailsActivity activity;

    public DetailsAdapter(FragmentManager fm, DetailsActivity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();
        args.putSerializable("stops", this.activity.getStreetsResult());

        switch (i) {
            case 0:
                StreetsFragment streetsFragment = new StreetsFragment();
                streetsFragment.setArguments(args);
                return streetsFragment;

            default:
                DeparturesFragment departuresFragment = new DeparturesFragment();
                departuresFragment.setArguments(args);
                return departuresFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Stops";

            default:
                return "Departures";
        }
    }
}
