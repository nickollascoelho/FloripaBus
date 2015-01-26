package com.arctouch.floripabus.tasks;

import android.os.AsyncTask;

import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.model.Street;
import com.arctouch.floripabus.services.RoutesService;

import java.util.List;


public class SearchStreetsByRouteTask extends AsyncTask<Integer, Void, List<Street>> {

    private Receiver<List<Street>> receiver;

    public SearchStreetsByRouteTask(Receiver<List<Street>> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected List<Street> doInBackground(Integer... selectedRouteId) {
        return RoutesService.getInstance().findStopsByRouteId(selectedRouteId[0]);
    }

    @Override
    protected void onPostExecute(List<Street> result) {
        receiver.onReceive(result);
    }
}
