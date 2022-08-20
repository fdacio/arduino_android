package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class PizzaIngrediente {

    private int pizzas_id;
    private int ingredientes_id;
    private double quantidade;
    private Ingrediente ingrediente;

    public PizzaIngrediente(JSONObject jsonObject) {
        this.pizzas_id = jsonObject.optInt("pizzas_id");
        this.ingredientes_id = jsonObject.optInt("ingredientes_id");
        this.quantidade = jsonObject.optDouble("quantidade");
        this.ingrediente = new Ingrediente(jsonObject.optJSONObject("ingrediente"));
    }

    public int getPizzas_id() {
        return pizzas_id;
    }

    public void setPizzas_id(int pizzas_id) {
        this.pizzas_id = pizzas_id;
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
