package br.com.daciosoftware.degustlanches.model;

import java.util.List;

public interface ProdutoEntity<T, P> {

    int getId();
    int getCodigo();
    ProdutoTipo getTipo();
    String getNome();
    String getIngredientes();
    String getFoto();
    List<P> getPrecos();
}
