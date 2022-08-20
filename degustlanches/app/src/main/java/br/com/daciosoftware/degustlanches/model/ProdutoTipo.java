package br.com.daciosoftware.degustlanches.model;

public enum ProdutoTipo {

    SANDUICHE("Sanduíche"),
    COMBO("Combo"),
    PIZZAS("Pizza"),
    PASTEIS("Pastel"),
    BEBIDAS("Bebida"),
    SUCOS_VITAMINAS("Sucos e Vitamina"),
    SUSHI("Sushi"),
    ACAI("Acai"),
    VARIEDADE("Variedade");


    private String descricao;

    ProdutoTipo(String descricao) {
        this.descricao = descricao;
    }

    public static ProdutoTipo fromOrdinal(int ordinal) {
        for (ProdutoTipo ps : ProdutoTipo.values()) {
            if ((ps.ordinal()) == ordinal) {
                return ps;
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }
}
