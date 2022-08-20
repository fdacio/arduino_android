package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Acai extends ProdutoMenu implements ProdutoEntity<Acai, AcaiPreco>, Serializable {

    private List<AcaiAcompanhamentoAcai> acompanhamentos = new ArrayList<>();
    private List<AcaiPreco> precos = new ArrayList<>();
    private AcaiAcompanhamentoPoteSeparado poteSeparado;
    private String foto;

    public Acai(JSONObject jsonObject) {

        this.id = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome = jsonObject.optString("nome");
        this.foto = jsonObject.optString("foto");

        JSONArray jsonAcompanhamentos = jsonObject.optJSONArray("acompanhamentos");
        for (int i = 0; i < jsonAcompanhamentos.length(); i++) {
            JSONObject jsonAcompanhamento = jsonAcompanhamentos.optJSONObject(i);
            AcaiAcompanhamentoAcai acaiAcompanhamento = new AcaiAcompanhamentoAcai(jsonAcompanhamento);
            acompanhamentos.add(acaiAcompanhamento);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                AcaiPreco acaiPreco = new AcaiPreco(jsonPreco);
                precos.add(acaiPreco);
            }
        }

        poteSeparado = new AcaiAcompanhamentoPoteSeparado(jsonObject.optJSONObject("potes_separado"));
    }

    @Override
    public ProdutoTipo getTipo() {
       return ProdutoTipo.ACAI;
    }

    @Override
    public String getIngredientes() {
        return null;
    }

    @Override
    public String getFoto() {
        return this.foto;
    }

    @Override
    public List<AcaiPreco> getPrecos() {
        return precos;
    }

    public List<AcaiAcompanhamentoAcai> getAcompanhamentos() {
        return acompanhamentos;
    }

    public AcaiAcompanhamentoPoteSeparado getPoteSeparado() {
        return poteSeparado;
    }
}
