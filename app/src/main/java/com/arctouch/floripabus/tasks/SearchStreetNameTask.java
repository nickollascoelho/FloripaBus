package com.arctouch.floripabus.tasks;

import android.os.AsyncTask;

import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.services.GeocodeService;
import com.google.android.gms.maps.model.LatLng;


public class SearchStreetNameTask extends AsyncTask<LatLng, Void, String> {

    private final Receiver<String> receiver;

    public SearchStreetNameTask(Receiver<String> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected String doInBackground(LatLng... latLong) {
        return GeocodeService.getInstance().getStreetName(latLong[0]);
    }

    @Override
    protected void onPostExecute(String streetName) {
        this.receiver.onReceive(streetName);
    }


}
