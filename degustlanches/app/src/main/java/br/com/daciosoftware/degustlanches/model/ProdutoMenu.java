package br.com.daciosoftware.degustlanches.model;

import java.io.Serializable;

public abstract class ProdutoMenu implements Serializable {

    protected int id;
    protected String nome;
    protected int codigo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
