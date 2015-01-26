package com.arctouch.floripabus.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.model.Departure;

import java.util.List;

public class DeparturesAdapter extends ArrayAdapter<Departure> {


    public DeparturesAdapter(Context context, List<Departure> objects) {
        super(context, R.layout.departure_row_layout, objects);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView calendar;
        TextView time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Departure item = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.departure_row_layout, parent, false);
            viewHolder.calendar = (TextView) convertView.findViewById(R.id.calendarTextView);
            viewHolder.time = (TextView) convertView.findViewById(R.id.timeTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.calendar.setText(item.getCalendar());
        viewHolder.time.setText(item.getTime());

        return convertView;

    }
}
