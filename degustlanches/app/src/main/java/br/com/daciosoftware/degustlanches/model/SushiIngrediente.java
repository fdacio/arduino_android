package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SushiIngrediente implements Serializable {

    private int sushis_id;
    private int ingredientes_id;
    private double quantidade;
    private Ingrediente ingrediente;

    public SushiIngrediente(JSONObject jsonObject) {
        this.sushis_id = jsonObject.optInt("sushis_id");
        this.ingredientes_id = jsonObject.optInt("ingredientes_id");
        this.quantidade = jsonObject.optDouble("quantidade");
        this.ingrediente = new Ingrediente(jsonObject.optJSONObject("ingrediente"));
    }

    public int getSushis_id() {
        return sushis_id;
    }

    public void setSushis_id(int sushis_id) {
        this.sushis_id = sushis_id;
    }

    public int getIngredientes_id() {
        return ingredientes_id;
    }

    public void setIngredientes_id(int ingredientes_id) {
        this.ingredientes_id = ingredientes_id;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }


}
