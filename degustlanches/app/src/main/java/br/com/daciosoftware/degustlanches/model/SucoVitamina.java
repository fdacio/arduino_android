package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SucoVitamina extends ProdutoMenu implements ProdutoEntity<SucoVitamina, SucoVitaminaPreco> {

    private List<SucoVitaminaPreco> precos = new ArrayList<>();

    public SucoVitamina(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome   = jsonObject.optString("nome");

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                SucoVitaminaPreco sucoVitaminaPreco = new SucoVitaminaPreco(jsonPreco);
                precos.add(sucoVitaminaPreco);
            }
        }
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.SUCOS_VITAMINAS;
    }

    @Override
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getIngredientes() {
        return null;
    }

    @Override
    public String getFoto() {
        return null;
    }

    @Override
    public List<SucoVitaminaPreco> getPrecos() {
        return precos;
    }
}
