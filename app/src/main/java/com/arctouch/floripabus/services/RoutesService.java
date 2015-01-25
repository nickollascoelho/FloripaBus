package com.arctouch.floripabus.services;

import com.arctouch.floripabus.common.BasicHttpClient;
import com.arctouch.floripabus.model.Departure;
import com.arctouch.floripabus.model.Route;
import com.arctouch.floripabus.model.Street;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoutesService {

    private static RoutesService instance;
    private static final String ENDPOINT_PREFIX = "https://api.appglu.com/v1/queries/";
    private static final String FIND_ROUTES_BY_STOP_NAME_ENDPOINT = "findRoutesByStopName/run";
    private static final String FIND_DEPARTURES_BY_ROUTE_ID_ENDPOINT =  "findDeparturesByRouteId/run";
    private static final String FIND_STOPS_BY_ROUTE_ID_ENDPOINT =  "findStopsByRouteId/run";

    private BasicHttpClient client;
    private Map<String, String> headers;
    private ObjectMapper mapper;

    private RoutesService() {
        this.client = BasicHttpClient.getInstance();
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.headers.put("Authorization", "Basic V0tENE43WU1BMXVpTThWOkR0ZFR0ek1MUWxBMGhrMkMxWWk1cEx5VklsQVE2OA==");
        this.headers.put("X-AppGlu-Environment", "staging");
        this.mapper =  new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static RoutesService getInstance() {
        if (instance == null) {
           instance = new RoutesService();
        }
        return instance;
    }

    public List<Route> findRoutesByStopName(String stopName) {
      return find(FIND_ROUTES_BY_STOP_NAME_ENDPOINT, "{\"params\": {\"stopName\":\"%" + stopName.trim() + "%\"}}" , Route.class);
    }

    public List<Departure> findDeparturesByRouteId(Integer routeId) {
        return find(FIND_DEPARTURES_BY_ROUTE_ID_ENDPOINT, "{\"params\": {\"routeId\" : " + routeId + " }}", Departure.class);
    }

    public List<Street> findStopsByRouteId(Integer routeId) {
        return find(FIND_STOPS_BY_ROUTE_ID_ENDPOINT, "{\"params\": {\"routeId\" : " + routeId + " }}", Street.class);
    }

    private <T> List<T> find(String endpoint, String param, Class<T> clazz) {
        String json = getResultAsJson(ENDPOINT_PREFIX + endpoint, param);

        if (json == null || json.isEmpty()) {
            return Collections.<T> emptyList();
        }

        try {
            return this.mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.<T> emptyList();
    }

    private String getResultAsJson(String url, String requestBody) {
        return extractRows(doPost(url, requestBody));
    }

    private String doPost(String url, String requestBody) {
        return client.doPostRequest(url, this.headers, requestBody);
    }

    private String extractRows(String response) {
        if (response.contains("error")) {
            return "";
        }

        if (response.contains("[") && response.contains("]")) {
            return response.substring(response.indexOf("["), (response.lastIndexOf("]") + 1));
        }

        return "";
    }

}
