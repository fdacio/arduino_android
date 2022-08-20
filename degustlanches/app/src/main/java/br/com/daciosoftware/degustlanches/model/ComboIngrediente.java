package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class ComboIngrediente implements Serializable {

    private int combos_id;
    private int ingredientes_id;
    private double quantidade;
    private Ingrediente ingrediente;

    public ComboIngrediente(){}

    public ComboIngrediente(JSONObject jsonObject) {
        this.combos_id = jsonObject.optInt("combos_id");
        this.ingredientes_id = jsonObject.optInt("ingredientes_id");
        this.quantidade = jsonObject.optDouble("quantidade");
        this.ingrediente = new Ingrediente(jsonObject.optJSONObject("ingrediente"));
    }

    public int getCombos_id() {
        return combos_id;
    }

    public void setCombos_id(int combos_id) {
        this.combos_id = combos_id;
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
