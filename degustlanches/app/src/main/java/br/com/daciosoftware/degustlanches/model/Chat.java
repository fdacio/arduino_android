package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;

import br.com.daciosoftware.degustlanches.util.MyDateUtil;

public class Chat {

    private int id;
    private Calendar dataHora;
    private String mensagem;
    private boolean lida;
    private int tipo;
    private Calendar lidaEm;
    private int clienteId;

    public Chat() {}

    public Chat(JSONObject jsonObject) {

        try {

            this.id = jsonObject.optInt("id");
            this.dataHora = MyDateUtil.dateTimeUSToCalendar(jsonObject.optString("data_hora"));
            this.mensagem = jsonObject.optString("mensagem");
            this.lida = jsonObject.optBoolean("lido");
            this.tipo = jsonObject.optInt("tipo");
            String lidaEm = jsonObject.optString("lida_em");
            if ( lidaEm != "null") {
                this.lidaEm = MyDateUtil.dateTimeUSToCalendar(lidaEm);
            }
            this.clienteId = jsonObject.optInt("clientes_id");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Calendar getLidaEm() {
        return lidaEm;
    }

    public void setLidaEm(Calendar lidaEm) {
        this.lidaEm = lidaEm;
    }

    public int getCliente() {
        return clienteId;
    }

    public void setCliente(int clienteId) {
        this.clienteId = clienteId;
    }
}
