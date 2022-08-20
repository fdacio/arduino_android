package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class AcaiAcompanhamentoAcai implements Serializable {

    private int acais_id;
    private int acais_acompanhamentos_id;
    private AcaiAcompanhamento acaiAcompanhamento;
    private boolean isChecked;

    public AcaiAcompanhamentoAcai(JSONObject jsonObject) {
        this.acais_id = jsonObject.optInt("acais_id");
        this.acais_acompanhamentos_id = jsonObject.optInt("acais_acompanhamentos_id");
        this.acaiAcompanhamento = new AcaiAcompanhamento(jsonObject.optJSONObject("acais_acompanhamento"));
    }

    public AcaiAcompanhamento getAcaiAcompanhamento() {
        return acaiAcompanhamento;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
