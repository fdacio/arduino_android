package br.com.daciosoftware.degustlanches.webservice;

import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public abstract class WebService {

    public static final String URL_ROOT_HTTPS = "https://degustlanches.com.br/degustlanchesdelivery";
    public static final String URL_ROOT_HTTP = "http://degustlanches.com.br/degustlanchesdelivery";

    public JSONObject getJSONObject(String url) {

        try {
            String json = new HttpConnection().getContentJSON(url);
            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject putJSONObject(String url) {

        try {
            String json = new HttpConnection().getContentJSON(url);
            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public abstract String getEndPoint();

    public JSONObject read() {
        String endPoint = getEndPoint();
        String url = getUrlRootHttps() + endPoint;
        return this.getJSONObject(url);
    }

    public JSONObject readById(Integer id) {
        String endPoint = getEndPoint() + id;
        String url = getUrlRootHttps() + endPoint;
        return this.getJSONObject(url);
    }

    public static String getUrlRootHttps() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return URL_ROOT_HTTPS;
        } else {
            return URL_ROOT_HTTP;
        }
    }
}

