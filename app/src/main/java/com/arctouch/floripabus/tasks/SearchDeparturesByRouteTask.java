package com.arctouch.floripabus.tasks;

import android.os.AsyncTask;

import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.model.Departure;
import com.arctouch.floripabus.services.RoutesService;

import java.util.List;


public class SearchDeparturesByRouteTask extends AsyncTask<Integer, Void, List<Departure>> {

    private final Receiver<List<Departure>> receiver;

    public SearchDeparturesByRouteTask(Receiver<List<Departure>> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected List<Departure> doInBackground(Integer... selectedRouteIds) {
        return RoutesService.getInstance().findDeparturesByRouteId(selectedRouteIds[0]);
    }

    @Override
    protected void onPostExecute(List<Departure> result) {
        this.receiver.onReceive(result);
    }
}
