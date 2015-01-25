package com.arctouch.floripabus.common;


public class GeocodeClient {

    private static final String ENDPOINT = "http://maps.google.com/maps/api/geocode/json?sensor=false&latlng=";
    private static GeocodeClient instance;

    private GeocodeClient() {

    }

    public static GeocodeClient getInstance() {
        if (instance == null) {
            instance = new GeocodeClient();
        }
        return instance;
    }

    public String getStreetName(String latLong) {
        String json = getLocationInfo(latLong);

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
