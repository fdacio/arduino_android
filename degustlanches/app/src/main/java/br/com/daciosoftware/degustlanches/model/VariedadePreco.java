package br.com.daciosoftware.degustlanches.model;


import org.json.JSONObject;

public class VariedadePreco implements ProdutoPrecoEntity {

    private int id;
    private int variedades_id;
    private int variedades_unidades_id;
    private int produtos_id;
    private double preco;
    private VariedadeUnidade unidade;


    public VariedadePreco(){}

    public VariedadePreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.variedades_id = jsonObject.optInt("variedades_id");
        this.variedades_unidades_id = jsonObject.optInt("variedades_unidades_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.unidade = new VariedadeUnidade(jsonObject.optJSONObject("variedades_unidade"));
    }
    @Override
    public int getProdutoId() {
        return this.produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return this.unidade.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return this.preco;
    }
}
