package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class PastelPreco implements ProdutoPrecoEntity {

    private int id;
    private int pasteis_id;
    private int pasteis_tamanhos_id;
    private int produtos_id;
    private double preco;
    private PastelTamanho tamanho;

    public PastelPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.pasteis_id = jsonObject.optInt("pasteis_id");
        this.pasteis_tamanhos_id = jsonObject.optInt("pasteis_tamanhos_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.tamanho = new PastelTamanho(jsonObject.optJSONObject("pasteis_tamanho"));
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
