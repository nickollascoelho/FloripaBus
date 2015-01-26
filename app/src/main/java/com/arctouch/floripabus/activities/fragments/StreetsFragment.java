package com.arctouch.floripabus.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.arctouch.floripabus.adapters.StreetsAdapter;
import com.arctouch.floripabus.model.Street;
import com.arctouch.floripabus.model.StreetsResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StreetsFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        StreetsResult stops = (StreetsResult) args.get("stops");
        showStreets(stops.getStreets());
    }

    private void showStreets(List<Street> streets) {
        if (streets == null || streets.isEmpty()) {
            setListAdapter(null);
            return;
        }

        setListAdapter(new StreetsAdapter(getActivity(), streets));
    }
}
