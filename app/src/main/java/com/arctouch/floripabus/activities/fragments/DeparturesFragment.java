package com.arctouch.floripabus.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.arctouch.floripabus.activities.DetailsActivity;
import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.adapters.DeparturesAdapter;
import com.arctouch.floripabus.common.DialogUtil;
import com.arctouch.floripabus.model.Departure;
import com.arctouch.floripabus.tasks.SearchDeparturesByRouteTask;

import java.util.List;


public class DeparturesFragment extends ListFragment implements Receiver<List<Departure>> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogUtil.showProgressDialog("Wait a moment...", "Searching", getActivity());
        Integer selectedRouteId = ((DetailsActivity) getActivity()).getSelectedRouteId();
        new SearchDeparturesByRouteTask(this).execute(selectedRouteId);
    }


    private void showDepartures(List<Departure> departures) {
        if (departures == null || departures.isEmpty()) {
            setListAdapter(null);
            return;
        }

        setListAdapter(new DeparturesAdapter(getActivity(), departures));
    }

    @Override
    public void onReceive(List<Departure> result) {
        showDepartures(result);
        DialogUtil.closeProgressDialog();
    }
}
