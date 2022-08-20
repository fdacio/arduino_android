package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SushiRecheioSushi implements Serializable {

    private int sushis_id;
    private int sushis_recheios_id;
    private SushiRecheio sushiRecheio;
    private boolean isChecked;

    public SushiRecheioSushi(JSONObject jsonObject) {
        this.sushis_id = jsonObject.optInt("sushis_id");
        this.sushis_recheios_id = jsonObject.optInt("sushis_recheios_id");
        this.sushiRecheio = new SushiRecheio(jsonObject.optJSONObject("sushis_recheio"));
    }

    public SushiRecheio getSushiRecheio() {
        return sushiRecheio;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
 }
