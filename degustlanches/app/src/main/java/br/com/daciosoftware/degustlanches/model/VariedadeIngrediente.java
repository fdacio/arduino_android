package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class VariedadeIngrediente implements Serializable {

    private int variedades_id;
    private int ingredientes_id;
    private double quantidade;
    private Ingrediente ingrediente;

    public VariedadeIngrediente(){}

    public VariedadeIngrediente(JSONObject jsonObject) {
        this.variedades_id = jsonObject.optInt("variedades_id");
        this.ingredientes_id = jsonObject.optInt("ingredientes_id");
        this.quantidade = jsonObject.optDouble("quantidade");
        this.ingrediente = new Ingrediente(jsonObject.optJSONObject("ingrediente"));
    }

    public int getVariedades_id() {
        return variedades_id;
    }

    public void setVariedades_id(int variedades_id) {
        this.variedades_id = variedades_id;
    }

    public int getIngredientes_id() {
        return ingredientes_id;
    }

    public void setIngredientes_id(int ingredientes_id) {
        this.ingredientes_id = ingredientes_id;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
