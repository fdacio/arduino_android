package br.com.daciosoftware.degustlanches.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperDatabase extends SQLiteOpenHelper {

    public SQLiteHelperDatabase(Context context) {
        super(context, ContractDatabase.NOME_BANCO, null, ContractDatabase.VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ContractDatabase.Cliente.SQL_CRIAR_TABELA);
        db.execSQL(ContractDatabase.Pedido.SQL_CRIAR_TABELA);
        db.execSQL(ContractDatabase.Produto.SQL_CRIAR_TABELA);
        db.execSQL(ContractDatabase.PedidoProduto.SQL_CRIAR_TABELA);
        db.execSQL(ContractDatabase.BairroEntrega.SQL_CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {

        if (novaVersao > versaoAntiga) {
            db.execSQL(ContractDatabase.Cliente.SQL_DELETA_TABELA);
            db.execSQL(ContractDatabase.Pedido.SQL_DELETA_TABELA);
            db.execSQL(ContractDatabase.Produto.SQL_DELETA_TABELA);
            db.execSQL(ContractDatabase.PedidoProduto.SQL_DELETA_TABELA);
            db.execSQL(ContractDatabase.BairroEntrega.SQL_DELETA_TABELA);
            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
