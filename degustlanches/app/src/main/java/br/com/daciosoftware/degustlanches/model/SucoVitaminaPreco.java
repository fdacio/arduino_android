package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class SucoVitaminaPreco implements ProdutoPrecoEntity {

    private int id;
    private int sucos_vitaminas_id;
    private int sucos_vitaminas_tipos_id;
    private SucoVitaminaTipo tipo;
    private int produtos_id;
    private double preco;

    public SucoVitaminaPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.sucos_vitaminas_id = jsonObject.optInt("sucos_vitaminas_id");
        this.sucos_vitaminas_tipos_id = jsonObject.optInt("sucos_vitaminas_tipos_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.tipo = new SucoVitaminaTipo(jsonObject.optJSONObject("sucos_vitaminas_tipo"));
    }

    @Override
    public int getProdutoId() {
        return produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return tipo.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return preco;
    }
}