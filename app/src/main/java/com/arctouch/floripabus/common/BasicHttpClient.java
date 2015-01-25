package com.arctouch.floripabus.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;


public class BasicHttpClient {

    private static BasicHttpClient instance;
    private static org.apache.http.client.HttpClient client;

    public static BasicHttpClient getInstance() {
        if (instance == null) {
           instance = new BasicHttpClient();
        }
        return instance;
    }

    public String doGetRequest(String url) {
        HttpGet get = new HttpGet(url);
        client = new DefaultHttpClient();
        return executeAndGetResponse(get);
    }

    public String doPostRequest(String url, Map<String, String> headers, String requestContent) {
        HttpPost post = new HttpPost(url);

        configRequestHeaders(post, headers);
        client = new DefaultHttpClient();
        configRequestBody(post, requestContent);

        return executeAndGetResponse(post);
    }

    private void configRequestHeaders(HttpEntityEnclosingRequest request, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            request.addHeader(key,headers.get(key));
        }
    }


    private void configRequestBody(HttpEntityEnclosingRequest request, String requestContent) {
        try {
            StringEntity requestBody = new StringEntity(requestContent);
            request.setEntity(requestBody);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String executeAndGetResponse(HttpUriRequest request) {
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
