package com.arctouch.floripabus.tasks;

import android.os.AsyncTask;

import com.arctouch.floripabus.activities.Receiver;
import com.arctouch.floripabus.model.Route;
import com.arctouch.floripabus.services.RoutesService;

import java.util.List;


public class SearchRouteTask extends AsyncTask<String, Void, List<Route>> {

    private final Receiver<List<Route>> receiver;

    public SearchRouteTask(Receiver<List<Route>> receiver) {
        this.receiver = receiver;
    }

    @Override
    protected List<Route> doInBackground(String... search) {
        return RoutesService.getInstance().findRoutesByStopName(search[0]);
    }

    @Override
    protected void onPostExecute(List<Route> routes) {
        this.receiver.onReceive(routes);
    }


}
