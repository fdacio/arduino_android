package br.com.daciosoftware.degustlanches.webservice;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.daciosoftware.degustlanches.model.Cliente;

public class ClientesWS extends WebService {

    private JSONObject save(Cliente cliente, String url, ActionType actionType) {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("nome", cliente.getNome());
            jsonObject.accumulate("celular", cliente.getCelular());
            jsonObject.accumulate("email", cliente.getEmail());
            jsonObject.accumulate("endereco", cliente.getEndereco());
            jsonObject.accumulate("numero", cliente.getNumero());
            jsonObject.accumulate("bairros_id", cliente.getBairros_id());
            jsonObject.accumulate("complemento", cliente.getComplemento());
            jsonObject.accumulate("ponto_referencia", cliente.getPontoReferencia());
            String json = jsonObject.toString();
            StringEntity se = new StringEntity(json);

            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = null;

            if (actionType == ActionType.CREATE) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpResponse = httpClient.execute(httpPost);
            } else if (actionType == ActionType.UPDATE) {

                HttpPut httpPut = new HttpPut(url);
                httpPut.setEntity(se);
                httpPut.setHeader("Accept", "application/json");
                httpPut.setHeader("Content-type", "application/json");
                httpResponse = httpClient.execute(httpPut);
            }

            StringBuilder sb = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject create(Cliente cliente) {
        String endPoint = "/api/clientes/create";
        String url = WebService.getUrlRootHttps() + endPoint;
        return this.save(cliente, url, ActionType.CREATE);
    }

    public JSONObject update(Cliente cliente) {
        String endPoint = "/api/clientes/update/" + cliente.getCodigo();
        String url = WebService.getUrlRootHttps() + endPoint;
        return this.save(cliente, url, ActionType.UPDATE);
    }

    public JSONObject readByCelular(String celular) {
        String endPoint = "/api/clientes/celular/" + celular.replaceAll("[^0-9]", "");
        String url = WebService.getUrlRootHttps() + endPoint;
        return getJSONObject(url);
    }

    public JSONObject readByEmail(String email) {
        String endPoint = "/api/clientes/email/" + email;
        String url = WebService.getUrlRootHttps() + endPoint;
        return getJSONObject(url);
    }

    @Override
    public String getEndPoint() {
        return "/api/clientes/";
    }
}
