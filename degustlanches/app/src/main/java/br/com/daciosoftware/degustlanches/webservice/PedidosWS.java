package br.com.daciosoftware.degustlanches.webservice;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;

public class PedidosWS extends WebService {

    private JSONObject createPedido(Pedido pedido, String url) {

        try {

            JSONObject jsonPedido = new JSONObject();

            jsonPedido.accumulate("clientes_id", pedido.getCliente().getCodigo());
            jsonPedido.accumulate("forma_pagamento", pedido.getFormaPagamento().ordinal());
            jsonPedido.accumulate("troco_para", pedido.getTrocaPara());
            jsonPedido.accumulate("taxa_entrega", pedido.getEnderecoEntrega().getBairro().getTaxaEntrega());
            jsonPedido.accumulate("desconto", pedido.getDesconto());
            jsonPedido.accumulate("local", 1);
            JSONArray jsonArrayProdutos = new JSONArray();

            for (PedidoProduto pedidoProduto : pedido.getPedidoProdutos()) {
                JSONObject jsonProduto = new JSONObject();
                jsonProduto.put("produtos_id", pedidoProduto.getProduto().getCodigo());
                jsonProduto.put("quantidade", pedidoProduto.getQuantidade());
                jsonProduto.put("preco", pedidoProduto.getPreco());
                jsonProduto.put("observacoes", (pedidoProduto.getObservacoes() != null)?pedidoProduto.getObservacoes():"");
                jsonArrayProdutos.put(jsonProduto);
            }
            jsonPedido.accumulate("produtos", jsonArrayProdutos);

            JSONObject jsonEnderecoEntrega = new JSONObject();
            jsonEnderecoEntrega.accumulate("endereco", pedido.getEnderecoEntrega().getEndereco());
            jsonEnderecoEntrega.accumulate("numero", pedido.getEnderecoEntrega().getNumero());
            jsonEnderecoEntrega.accumulate("complemento", pedido.getEnderecoEntrega().getComplemento());
            jsonEnderecoEntrega.accumulate("ponto_referencia", pedido.getEnderecoEntrega().getPontoReferencia());
            jsonEnderecoEntrega.accumulate("bairros_id", pedido.getEnderecoEntrega().getBairro().getId());
            jsonPedido.accumulate("endereco_entrega", jsonEnderecoEntrega);

            String json = jsonPedido.toString();
            StringEntity se = new StringEntity(json);

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse;


            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
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

    private JSONObject cancelPedido(Pedido pedido, String url) {

        try {

            JSONObject jsonPedido = new JSONObject();

            jsonPedido.accumulate("id", pedido.getId());

            String json = jsonPedido.toString();
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

    public JSONObject create(Pedido pedido) {
        String endPoint = "/api/pedidos/create";
        String url = WebService.getUrlRootHttps() + endPoint;
        return this.createPedido(pedido, url);
    }

    public JSONObject cancel(Pedido pedido) {
        String endPoint = "/api/pedidos/cancel";
        String url = WebService.getUrlRootHttps() + endPoint;
        return this.cancelPedido(pedido, url);
    }

    public JSONObject readByCliente(Integer id) {
        String endPoint = "/api/pedidos/pedidos-cliente/"+id;
        String url = getUrlRootHttps()+ endPoint;
        return this.getJSONObject(url);
    }

    @Override
    public String getEndPoint() {
        return "/api/pedidos/";
    }

}
