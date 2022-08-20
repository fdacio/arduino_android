package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class PastelIngrediente {

    private int pasteis_id;
    private int ingredientes_id;
    private double quantidade;
    private Ingrediente ingrediente;

    public PastelIngrediente(){}

    public PastelIngrediente(JSONObject jsonObject) {
        this.pasteis_id = jsonObject.optInt("pastes_id");
        this.ingredientes_id = jsonObject.optInt("ingredientes_id");
        this.quantidade = jsonObject.optDouble("quantidade");
        this.ingrediente = new Ingrediente(jsonObject.optJSONObject("ingrediente"));
    }

    public int getPastes_id() {
        return pasteis_id;
    }

    public void setPastes_id(int pasteis_id) {
        this.pasteis_id = pasteis_id;
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
