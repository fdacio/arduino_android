package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class SanduichePreco implements ProdutoPrecoEntity {

    private int id;
    private int sanduiches_id;
    private int paes_id;
    private int produtos_id;
    private double preco;
    private Pao pao;

    public SanduichePreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.sanduiches_id = jsonObject.optInt("sanduiches_id");
        this.paes_id = jsonObject.optInt("paes_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.pao = new Pao(jsonObject.optJSONObject("pae"));
    }

    @Override
    public int getProdutoId() {
        return produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return pao.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return preco;
    }
}
