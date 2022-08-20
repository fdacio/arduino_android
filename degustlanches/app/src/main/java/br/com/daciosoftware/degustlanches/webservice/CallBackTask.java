package br.com.daciosoftware.degustlanches.webservice;

import org.json.JSONObject;

public interface CallBackTask {

    void post(JSONObject jsonObject);

    void get(JSONObject jsonObject);

}
