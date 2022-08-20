package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class SucoVitaminaTipo {
    private int id;
    private String nome;

    public SucoVitaminaTipo(JSONObject jsonObject) {
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
