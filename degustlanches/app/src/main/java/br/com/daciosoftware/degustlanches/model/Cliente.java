package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int id;
    private int codigo;
    private String nome;
    private String celular;
    private String email;
    private String endereco;
    private String numero;
    private String complemento;
    private String pontoReferencia;
    private Bairro bairro;
    private int bairros_id;

    public Cliente() {
    }

    public Cliente(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("id");
        this.nome = jsonObject.optString("nome");
        this.celular = jsonObject.optString("celular");
        this.email = jsonObject.optString("email");
        this.endereco = jsonObject.optString("endereco");
        this.numero = jsonObject.optString("numero");
        this.complemento = jsonObject.optString("complemento");
        this.pontoReferencia = jsonObject.optString("ponto_referencia");
        this.bairros_id = jsonObject.optInt("bairros_id");
        this.bairro = new Bairro(jsonObject.optJSONObject("bairro"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public int getBairros_id() {
        return bairros_id;
    }

    public void setBairros_id(int bairros_id) {
        this.bairros_id = bairros_id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", pontoReferencia='" + pontoReferencia + '\'' +
                ", bairros_id='" + bairros_id + '\'' +
                '}';
    }
}
