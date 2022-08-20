package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class PizzaPreco implements ProdutoPrecoEntity {

    private int id;
    private int pizzas_id;
    private int pizzas_tamanhos_id;
    private int produtos_id;
    private double preco;
    private PizzaTamanho tamanho;

    public PizzaPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.pizzas_id = jsonObject.optInt("pizzas_id");
        this.pizzas_tamanhos_id = jsonObject.optInt("pizzas_tamanhos_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.tamanho = new PizzaTamanho(jsonObject.optJSONObject("pizzas_tamanho"));
    }

    @Override
    public int getProdutoId() {
        return produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return tamanho.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return preco;
    }
}
