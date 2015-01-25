package com.arctouch.floripabus.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleAdapter;

import com.arctouch.floripabus.R;
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
        showStops(stops.getStreets());
    }

    private void showStops(List<Street> streets) {
        if (streets == null || streets.isEmpty()) {
            setListAdapter(null);
            return;
        }

        List<Map<String, String>> dataList = parseToMap(streets);
        String[] from = new String[]{"name", "sequence"};
        int[] to = new int[]{R.id.nameStopTextView, R.id.sequenceStopTextView};
        setListAdapter(new SimpleAdapter(getActivity(), dataList, R.layout.stop_row_layout, from, to));

    }

    private List<Map<String, String>> parseToMap(List<Street> streets) {
        List<Map<String, String>> dataList = new ArrayList<>();

        for (Street street : streets) {
            Map<String, String> stopMap = new HashMap<>();
            stopMap.put("id", String.valueOf(street.getId()));
            stopMap.put("name", street.getName());
            stopMap.put("sequence", String.valueOf(street.getSequence()));
            dataList.add(stopMap);
        }

        return dataList;
    }
}
