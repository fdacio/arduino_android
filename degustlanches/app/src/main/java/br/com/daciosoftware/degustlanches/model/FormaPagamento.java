package br.com.daciosoftware.degustlanches.model;

public enum FormaPagamento {

    AINFORMAR("A Informar"),
    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    DINHEIRO_SEM_TROCO("Dinheiro Sem Trôco");

    private String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public static FormaPagamento fromOrdinal(int ordinal) {
        for (FormaPagamento fp : FormaPagamento.values()) {
            if ((fp.ordinal()) == ordinal) {
                return fp;
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }
}
