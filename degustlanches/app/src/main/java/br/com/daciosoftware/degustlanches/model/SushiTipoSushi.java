package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SushiTipoSushi implements Serializable {

    private int sushis_id;
    private int sushis_tipos_id;
    private SushiTipo sushiTipo;
    private int quantidade;

    public SushiTipoSushi(JSONObject jsonObject) {
        this.sushis_id = jsonObject.optInt("sushis_id");
        this.sushis_tipos_id = jsonObject.optInt("sushis_tipos_id");
        this.sushiTipo = new SushiTipo(jsonObject.optJSONObject("sushis_tipo"));
    }

    public int getSushis_id() {
        return sushis_id;
    }

    public void setSushis_id(int sushis_id) {
        this.sushis_id = sushis_id;
    }

    public int getSushis_tipos_id() {
        return sushis_tipos_id;
    }

    public void setSushis_tipos_id(int sushis_tipos_id) {
        this.sushis_tipos_id = sushis_tipos_id;
    }

    public SushiTipo getSushiTipo() {
        return sushiTipo;
    }

    public void setSushiTipo(SushiTipo sushiTipo) {
        this.sushiTipo = sushiTipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
