package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

import br.com.daciosoftware.degustlanches.model.ProdutoTipo;

public class Produto implements Serializable {

    private int codigo;
    private ProdutoTipo tipo;
    private String nome;
    private String ingredientes;
    private double preco;

    public Produto(JSONObject jsonObject) {
        this.codigo = jsonObject.optInt("produtos_id");
        this.tipo =  getTipo(jsonObject.optInt("tipo"));
        this.nome = jsonObject.optString("nome");
        this.ingredientes = jsonObject.optString("ingredientes");
        this.preco = jsonObject.optDouble("preco");
    }

    public Produto(){}

    private ProdutoTipo getTipo(int tipo) {
        switch (tipo) {
            case 1: return ProdutoTipo.SANDUICHE;
            case 2: return ProdutoTipo.COMBO;
            case 3: return ProdutoTipo.PIZZAS;
            case 4: return ProdutoTipo.PASTEIS;
            case 6: return ProdutoTipo.SUCOS_VITAMINAS;
            case 7: return ProdutoTipo.BEBIDAS;
            case 9: return ProdutoTipo.VARIEDADE;
            default: return null;
        }
    }

    public ProdutoTipo getTipo() {
        return tipo;
    }

    public void setTipo(ProdutoTipo tipo) {
        this.tipo = tipo;
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

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}