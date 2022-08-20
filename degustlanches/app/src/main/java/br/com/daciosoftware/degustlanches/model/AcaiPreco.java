package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class AcaiPreco implements ProdutoPrecoEntity, Serializable {

    private int id;
    private int acais_id;
    private int acais_unidades_id;
    private int produtos_id;
    private double preco;
    private AcaiUnidade unidade;

    public AcaiPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.acais_id = jsonObject.optInt("acais_id");
        this.acais_unidades_id = jsonObject.optInt("acais_unidades_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.unidade = new AcaiUnidade(jsonObject.optJSONObject("acais_unidade"));
    }

    @Override
    public int getProdutoId() {
        return this.produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return unidade.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return preco;
    }
}
