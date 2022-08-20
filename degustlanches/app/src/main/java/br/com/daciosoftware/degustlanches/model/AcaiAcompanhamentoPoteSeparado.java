package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class AcaiAcompanhamentoPoteSeparado implements Serializable {

    private int id;
    private String descricao;
    private double preco;

    public AcaiAcompanhamentoPoteSeparado(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.descricao = jsonObject.optString("descricao");
        this.preco = jsonObject.optDouble("preco");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
