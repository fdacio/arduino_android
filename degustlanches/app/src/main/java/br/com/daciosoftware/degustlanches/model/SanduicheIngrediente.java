package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SanduicheIngrediente implements Serializable {

    private int sanduiches_id;
    private int ingredientes_id;
    private double quantidade;
    private Ingrediente ingrediente;

    public SanduicheIngrediente(){}

    public SanduicheIngrediente(JSONObject jsonObject) {
        this.sanduiches_id = jsonObject.optInt("sanduiches_id");
        this.ingredientes_id = jsonObject.optInt("ingredientes_id");
        this.quantidade = jsonObject.optDouble("quantidade");
        this.ingrediente = new Ingrediente(jsonObject.optJSONObject("ingrediente"));
    }

    public int getSanduiches_id() {
        return sanduiches_id;
    }

    public void setSanduiches_id(int sanduiches_id) {
        this.sanduiches_id = sanduiches_id;
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
