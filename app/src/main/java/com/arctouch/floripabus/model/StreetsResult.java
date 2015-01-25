package com.arctouch.floripabus.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StreetsResult implements Serializable {

    private List<Street> streets;
    private Map<String, String> selectedRoute;

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }

    public void setSelectedRoute(Map<String, String> selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public Map<String, String> getSelectedRoute() {
        return selectedRoute;
    }
}
