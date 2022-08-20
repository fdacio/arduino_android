package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class Bairro implements Serializable {

    private int id;
    private String nome;
    private double taxaEntrega;
    private int lojasId;

    public Bairro() {

    }

    public Bairro(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.nome = jsonObject.optString("nome");
        this.taxaEntrega = jsonObject.optDouble("taxa_entrega");
        this.lojasId = jsonObject.optInt("lojas_id");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public int getLojasId() {
        return lojasId;
    }

    public void setLojasId(int lojasId) {
        this.lojasId = lojasId;
    }
}
