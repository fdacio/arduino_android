package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class EnderecoEntrega implements Serializable {

    private String endereco;
    private String numero;
    private Bairro bairro;
    private String complemento;
    private String pontoReferencia;

    public EnderecoEntrega() {

    }

    public EnderecoEntrega(JSONObject jsonObject) {
        this.endereco = jsonObject.optString("endereco");
        this.numero = jsonObject.optString("numero");
        this.bairro = new Bairro(jsonObject.optJSONObject("bairro"));
        this.complemento = jsonObject.optString("complementos");
        this.pontoReferencia = jsonObject.optString("ponto_referencia");

    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getEnderecoCompleto() {
        String enderecoCompleto;

        enderecoCompleto = this.endereco + ", " + this.numero;
        if(!this.complemento.isEmpty()) {
            enderecoCompleto += " - " +this.complemento;
        }
        enderecoCompleto += " - " +this.bairro.getNome();
        if (!this.pontoReferencia.isEmpty()) {
            enderecoCompleto += " - " + this.pontoReferencia;
        }

        return enderecoCompleto;
    }
}
