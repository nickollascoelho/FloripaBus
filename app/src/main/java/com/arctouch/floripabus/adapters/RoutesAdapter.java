package com.arctouch.floripabus.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.model.Route;

import java.util.List;

public class RoutesAdapter extends ArrayAdapter<Route> {

    public RoutesAdapter(Context context, List<Route> objects) {
        super(context, R.layout.route_row_layout, objects);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView longName;
        TextView shortName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Route item = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.route_row_layout, parent, false);
            viewHolder.longName = (TextView) convertView.findViewById(R.id.longNameTextView);
            viewHolder.shortName = (TextView) convertView.findViewById(R.id.shortNameTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.longName.setText(item.getLongName());
        viewHolder.shortName.setText(item.getShortName());

        return convertView;

    }
}
