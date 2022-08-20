package br.com.daciosoftware.degustlanches.webservice;

import org.json.JSONObject;

public class LojasWS extends WebService {

    @Override
    public String getEndPoint() {
        return "/api/lojas/";
    }

    public JSONObject horarioFuncionamento(Integer id) {
        String endPoint = "/api/lojas/hf/" + id;
        String urlWebService = getUrlRootHttps() + endPoint;
        return getJSONObject(urlWebService);
    }

}
