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

import br.com.daciosoftware.degustlanches.model.Chat;
import br.com.daciosoftware.degustlanches.model.Cliente;

public class ChatsWS extends WebService {

    public JSONObject readClienteById(Chat chat) {
        String endPoint = "/api/chats/clientes/"+chat.getCliente();
        String url = getUrlRootHttps() + endPoint;
        return this.getJSONObject(url);
    }

    private JSONObject createChat(Chat chat, String url) {

        try {

            JSONObject jsonChat = new JSONObject();

            jsonChat.accumulate("clientes_id", chat.getCliente());
            jsonChat.accumulate("mensagem", chat.getMensagem());
            jsonChat.accumulate("tipo", 1);

            String json = jsonChat.toString();
            StringEntity se = new StringEntity(json);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse;

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            httpResponse = httpClient.execute(httpPost);

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


    private JSONObject update(Chat chat, String url) {

        try {

            JSONObject jsonChat = new JSONObject();

            jsonChat.accumulate("id", chat.getId());

            String json = jsonChat.toString();
            StringEntity se = new StringEntity(json);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse;
            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(se);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            httpResponse = httpClient.execute(httpPut);


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

    public JSONObject create(Chat chat) {
        String endPoint = "/api/chats/create";
        String url = WebService.getUrlRootHttps() + endPoint;
        return this.createChat(chat, url);
    }

    public JSONObject update(Chat chat) {
        String endPoint = "/api/chats/update";
        String url = WebService.getUrlRootHttps() + endPoint;
        return this.update(chat, url);
    }

    public JSONObject delete(Chat chat) {
        String endPoint = "/api/chats/delete/"+chat.getId();
        String url = WebService.getUrlRootHttps() + endPoint;
        return super.getJSONObject(url);
    }

    public JSONObject getNaoLidos(Cliente cliente) {
        String endPoint = "/api/chats/clientes/nao-lidas/"+cliente.getCodigo();
        String url = WebService.getUrlRootHttps() + endPoint;
        return super.getJSONObject(url);
    }

    @Override
    public String getEndPoint() {
        return "/api/chats/";
    }
}
