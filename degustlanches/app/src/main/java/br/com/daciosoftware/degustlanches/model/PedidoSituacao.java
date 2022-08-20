package br.com.daciosoftware.degustlanches.model;

public enum PedidoSituacao {

    AINFORMAR("A Informar"),
    INICIADO("Iniciado"),
    ENVIADO("Enviado"),
    PREPARANDO("Preparando"),
    PRONTO("Pronto"),
    CAMINHO("A Caminho"),
    ENTREGUE_AO_CLIENTE("Entregue ao Cliente"),
    CONFIRMADO_O_RECEBIMENTO_PELO_CLIENTE("Confirmado o Recebimento pelo Cliente"),
    FINALIZADO("Finalizado"),
    CANCELADO_PELA_LOJA("Cancelado pela Loja"),
    CANCELADO_PELO_CLIENTE("Cancelado pelo Cliente");

    private String descricao;

    PedidoSituacao(String descricao) {
        this.descricao = descricao;
    }

    public static PedidoSituacao fromOrdinal(int ordinal) {
        for (PedidoSituacao ps : PedidoSituacao.values()) {
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
