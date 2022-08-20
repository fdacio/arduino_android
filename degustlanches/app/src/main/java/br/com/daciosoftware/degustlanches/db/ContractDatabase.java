package br.com.daciosoftware.degustlanches.db;

import android.provider.BaseColumns;

public class ContractDatabase {

    public static final String NOME_BANCO = "degust.db";
    public static final int VERSAO = 2;

    public static abstract class Cliente implements BaseColumns {

        public static final String NOME_TABELA = "cliente";
        public static final String COLUNA_CODIGO = "codigo";
        public static final String[] COLUNAS = {_ID, COLUNA_CODIGO};

        public static final String SQL_CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + Cliente.NOME_TABELA + "(" +
                        Cliente._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Cliente.COLUNA_CODIGO + " INTEGER NOT NULL); ";


        public static final String SQL_DELETA_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    }

    public static abstract class BairroEntrega implements BaseColumns {

        public static final String NOME_TABELA = "bairro_entrega";
        public static final String COLUNA_CODIGO = "codigo";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_TAXA_ENTREGA = "taxa_entrega";
        public static final String[] COLUNAS = {_ID, COLUNA_CODIGO, COLUNA_NOME, COLUNA_TAXA_ENTREGA};

        public static final String SQL_CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + BairroEntrega.NOME_TABELA + "(" +
                        BairroEntrega._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        BairroEntrega.COLUNA_CODIGO + " INTEGER NOT NULL, " +
                        BairroEntrega.COLUNA_NOME + " TEXT NOT NULL, " +
                        BairroEntrega.COLUNA_TAXA_ENTREGA + " REAL NOT NULL " +
                        "); ";


        public static final String SQL_DELETA_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;
    }


    public static abstract class Pedido implements BaseColumns {

        public static final String NOME_TABELA = "pedido";
        public static final String COLUNA_CODIGO = "codigo";
        public static final String COLUNA_DATA_HORA = "data_hora";
        public static final String COLUNA_FORMA_PAGAMENTO = "forma_pagamento";
        public static final String COLUNA_SITUACAO = "situacao";
        public static final String COLUNA_TROCO_PARA = "troco_para";
        public static final String COLUNA_DESCONTO = "desconto";
        public static final String COLUNA_TAXA_ENTREGA = "taxa_entrega";
        public static final String COLUNA_CLIENTES_ID = "clientes_id";
        public static final String COLUNA_ENDERECO_ENTREGA = "endereco_entrega";
        public static final String COLUNA_NUMERO_ENTREGA = "numero_entrega";
        public static final String COLUNA_BAIRROS_ID_ENTREGA = "bairro_entrega";
        public static final String COLUNA_COMPLEMENTO_ENTREGA = "complemento_entrega";
        public static final String COLUNA_PONTO_REFERENCIA_ENTREGA = "ponto_referencia_entrega";

        public static final String[] COLUNAS = {_ID,
                COLUNA_CODIGO, COLUNA_DATA_HORA, COLUNA_FORMA_PAGAMENTO, COLUNA_SITUACAO, COLUNA_DESCONTO,
                COLUNA_TAXA_ENTREGA, COLUNA_TROCO_PARA, COLUNA_CLIENTES_ID, COLUNA_ENDERECO_ENTREGA,
                COLUNA_NUMERO_ENTREGA, COLUNA_BAIRROS_ID_ENTREGA, COLUNA_COMPLEMENTO_ENTREGA,
                COLUNA_PONTO_REFERENCIA_ENTREGA};

        public static final String SQL_CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + Pedido.NOME_TABELA + "(" +
                        Pedido._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Pedido.COLUNA_CODIGO + " INTEGER NULL, " +
                        Pedido.COLUNA_DATA_HORA + " DATETIME NULL, " +
                        Pedido.COLUNA_FORMA_PAGAMENTO + " INTEGER  NULL, " +
                        Pedido.COLUNA_SITUACAO + " INTEGER NOT NULL, " +
                        Pedido.COLUNA_DESCONTO + "  DECIMAL(15,2) NULL, " +
                        Pedido.COLUNA_TAXA_ENTREGA + "  DECIMAL(15,2) NULL, " +
                        Pedido.COLUNA_TROCO_PARA + " DECIMAL(15,2) NULL, " +
                        Pedido.COLUNA_CLIENTES_ID + " INTEGER NOT NULL, " +
                        Pedido.COLUNA_ENDERECO_ENTREGA + " TEXT NULL, " +
                        Pedido.COLUNA_NUMERO_ENTREGA + " TEXT NULL, " +
                        Pedido.COLUNA_BAIRROS_ID_ENTREGA + " INTEGER NULL, " +
                        Pedido.COLUNA_COMPLEMENTO_ENTREGA + " TEXT NULL, " +
                        Pedido.COLUNA_PONTO_REFERENCIA_ENTREGA + " TEXT NULL " +
                        ");";

        public static final String SQL_DELETA_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;

    }

    public static abstract class Produto implements BaseColumns {

        public static final String NOME_TABELA = "produto";
        public static final String COLUNA_CODIGO = "codigo";
        public static final String COLUNA_TIPO = "tipo";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_PRECO = "preco";
        public static final String COLUNA_INGREDIENTES = "ingredientes";

        public static final String[] COLUNAS = {_ID, COLUNA_CODIGO, COLUNA_TIPO, COLUNA_NOME, COLUNA_PRECO, COLUNA_INGREDIENTES};

        public static final String SQL_CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + Produto.NOME_TABELA + "(" +
                        Produto._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Produto.COLUNA_CODIGO + " INTEGER NOT NULL, " +
                        Produto.COLUNA_TIPO + " INTEGER NOT NULL, " +
                        Produto.COLUNA_NOME + " TEXT NOT NULL, " +
                        Produto.COLUNA_INGREDIENTES + " TEXT NULL, " +
                        Produto.COLUNA_PRECO + " DECIMAL(15,2) NOT NULL);";

        public static final String SQL_DELETA_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;

    }

    public static abstract class PedidoProduto implements BaseColumns {

        public static final String NOME_TABELA = "pedido_produto";
        public static final String COLUNA_PEDIDO_ID = "pedido_id";
        public static final String COLUNA_PRODUTO_ID = "produto_id";
        public static final String COLUNA_QUANTIDADE = "quantidade";
        public static final String COLUNA_PRECO = "preco";
        public static final String COLUNA_OBSERVACOES = "observacoes";

        public static final String[] COLUNAS = {_ID, COLUNA_PEDIDO_ID, COLUNA_PRODUTO_ID, COLUNA_QUANTIDADE, COLUNA_PRECO, COLUNA_OBSERVACOES};

        public static final String SQL_CRIAR_TABELA =
                "CREATE TABLE IF NOT EXISTS " + PedidoProduto.NOME_TABELA + "(" +
                        PedidoProduto._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PedidoProduto.COLUNA_PEDIDO_ID + " INTEGER NOT NULL, " +
                        PedidoProduto.COLUNA_PRODUTO_ID + " INTEGER NOT NULL, " +
                        PedidoProduto.COLUNA_OBSERVACOES + " TEXT NULL, " +
                        PedidoProduto.COLUNA_PRECO + " DECIMAL(15,2) NOT NULL, " +
                        PedidoProduto.COLUNA_QUANTIDADE + " INTEGER NOT NULL " + ");";

        public static final String SQL_DELETA_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;

    }

}
