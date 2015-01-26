package com.arctouch.floripabus.services;


import com.arctouch.floripabus.common.BasicHttpClient;
import com.google.android.gms.maps.model.LatLng;

public class GeocodeService {

    private static final String ENDPOINT = "http://maps.google.com/maps/api/geocode/json?sensor=false&latlng=";
    private static GeocodeService instance;

    private GeocodeService() {

    }

    public static GeocodeService getInstance() {
        if (instance == null) {
            instance = new GeocodeService();
        }
        return instance;
    }

    public String getStreetName(LatLng latLng) {
        String json = getLocationInfo(latLng.latitude + "," + latLng.longitude);

        String streetName = null;

        if (!json.isEmpty() && json.contains(",")) {
            streetName = json.substring(0, json.indexOf(",")).trim();
        }

        return streetName;
    }

    private String getLocationInfo(String latLong) {
        return parseJson(BasicHttpClient.getInstance().doGetRequest(ENDPOINT + latLong));
    }

    private String parseJson(String json) {
        try {
            String formattedJSON = json.substring(json.indexOf("\"formatted_address\""), json.indexOf("\"geometry\""));
            formattedJSON = formattedJSON.substring(formattedJSON.indexOf(":") + 1, formattedJSON.lastIndexOf(","));
            return formattedJSON.replace("\"", "");
        } catch (Exception e) {
            return "";
        }
    }
}
