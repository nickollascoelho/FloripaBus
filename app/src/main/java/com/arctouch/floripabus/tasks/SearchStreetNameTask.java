package com.arctouch.floripabus.tasks;

import android.os.AsyncTask;

import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.common.GeocodeClient;


public class SearchStreetNameTask extends AsyncTask<String, Void, String> {

    private final Receiver<String> receiver;

    public SearchStreetNameTask(Receiver<String> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected String doInBackground(String... latLong) {
        return GeocodeClient.getInstance().getStreetName(latLong[0]);
    }

    @Override
    protected void onPostExecute(String streetName) {
        this.receiver.onReceive(streetName);
    }


}
