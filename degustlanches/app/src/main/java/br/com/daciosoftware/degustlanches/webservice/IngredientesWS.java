package br.com.daciosoftware.degustlanches.webservice;

import org.json.JSONObject;

public class IngredientesWS extends WebService {

    @Override
    public String getEndPoint() {
        return "/api/ingredientes/";
    }

    public JSONObject readIngredienteTipo(Integer tipo) {
        String endPoint = getEndPoint() + tipo;
        String url = getUrlRootHttps() + endPoint;
        return this.getJSONObject(url);
    }
}
