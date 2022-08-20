package br.com.daciosoftware.degustlanches.model;

import java.io.Serializable;

public class Configuracao implements Serializable {

    private int id;
    private double desconto;

    public Configuracao(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
