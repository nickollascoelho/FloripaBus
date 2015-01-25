package com.arctouch.floripabus.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.activities.DetailsActivity;
import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.common.DialogUtil;
import com.arctouch.floripabus.model.Departure;
import com.arctouch.floripabus.tasks.SearchDeparturesByRouteTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        List<Map<String, String>> dataList = parseToMap(departures);
        String[] from = new String[]{"calendar", "time"};
        int[] to = new int[]{R.id.calendarTextView, R.id.timeTextView};

        setListAdapter(new SimpleAdapter(getActivity(), dataList, R.layout.departure_row_layout, from, to));
    }

    private List<Map<String, String>> parseToMap(List<Departure> departures) {
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

        for (Departure departure : departures) {
            Map<String, String> departureMap = new HashMap<String, String>();
            departureMap.put("id", String.valueOf(departure.getId()));
            departureMap.put("calendar", departure.getCalendar());
            departureMap.put("time", departure.getTime());
            dataList.add(departureMap);
        }

        return dataList;
    }

    @Override
    public void onReceive(List<Departure> result) {
        showDepartures(result);
        DialogUtil.closeProgressDialog();
    }
}
