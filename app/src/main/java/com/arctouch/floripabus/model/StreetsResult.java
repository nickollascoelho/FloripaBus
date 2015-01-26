package com.arctouch.floripabus.model;

import java.io.Serializable;
import java.util.List;

public class StreetsResult implements Serializable {

    private List<Street> streets;
    private Route selectedRoute;

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }
}
