package com.arctouch.floripabus.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.model.Departure;
import com.arctouch.floripabus.model.Street;

import java.util.List;

public class StreetsAdapter extends ArrayAdapter<Street> {


    public StreetsAdapter(Context context, List<Street> objects) {
        super(context, R.layout.street_row_layout, objects);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView sequence;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Street item = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.street_row_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameStreetTextView);
            viewHolder.sequence = (TextView) convertView.findViewById(R.id.sequenceStreetTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(item.getName());
        viewHolder.sequence.setText(item.getSequence().toString());

        return convertView;
    }
}
