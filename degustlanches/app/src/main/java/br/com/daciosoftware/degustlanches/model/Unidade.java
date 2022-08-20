package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class Unidade implements Serializable {

    private int id;
    private String nome;

    public Unidade(JSONObject jsonObject) {
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
