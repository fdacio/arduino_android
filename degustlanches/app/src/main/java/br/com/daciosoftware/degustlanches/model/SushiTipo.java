package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SushiTipo implements Serializable {

    private int id;
    private String nome;

    public SushiTipo() {
    }

    public SushiTipo(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.nome = jsonObject.optString("nome");
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

}
