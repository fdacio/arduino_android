package br.com.daciosoftware.degustlanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import br.com.daciosoftware.degustlanches.db.ContractDatabase;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.model.ProdutoTipo;

public class ProdutoDAO {

    private SQLiteDatabase db;
    private Context context;

    public ProdutoDAO(Context context) {
        this.db = Database.getDatabase(context);
        this.context = context;
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public Long save(Produto produto) throws SQLiteException {
        ContentValues values = new ContentValues();
        values.put(ContractDatabase.Produto.COLUNA_CODIGO, produto.getCodigo());
        values.put(ContractDatabase.Produto.COLUNA_TIPO, produto.getTipo().ordinal());
        values.put(ContractDatabase.Produto.COLUNA_NOME, produto.getNome());
        values.put(ContractDatabase.Produto.COLUNA_INGREDIENTES, produto.getIngredientes());
        values.put(ContractDatabase.Produto.COLUNA_PRECO, produto.getPreco());
        return this.getDb().insertOrThrow(ContractDatabase.Produto.NOME_TABELA, "", values);
    }

    public int deleteAll() throws SQLiteException {
        String where = "_id" + ">?";
        String[] whereArgs = new String[]{String.valueOf(0)};
        return db.delete(ContractDatabase.Produto.NOME_TABELA, where, whereArgs);
    }

    public Produto getProduto(int id) {

        Produto produto = new Produto();

        try {

            String where = "codigo" + "=?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            Cursor cursor = getCursor(where, whereArgs);
            if (cursor.moveToFirst()) {
                do {
                    produto = getEntity(cursor);

                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return produto;
    }

    public Produto getEntity(Cursor c) {

        if (c.getCount() > 0) {
            Produto produto = new Produto();
            produto.setCodigo(c.getInt(c.getColumnIndex(ContractDatabase.Produto.COLUNA_CODIGO)));
            produto.setTipo(ProdutoTipo.fromOrdinal(c.getInt(c.getColumnIndex(ContractDatabase.Produto.COLUNA_TIPO))));
            produto.setNome(c.getString(c.getColumnIndex(ContractDatabase.Produto.COLUNA_NOME)));
            produto.setIngredientes(c.getString(c.getColumnIndex(ContractDatabase.Produto.COLUNA_INGREDIENTES)));
            produto.setPreco(c.getDouble(c.getColumnIndex(ContractDatabase.Produto.COLUNA_PRECO)));

            return produto;

        } else {

            return null;
        }
    }

    private Cursor getCursor(String where, String[] whereArgs) {
        return db.query(ContractDatabase.Produto.NOME_TABELA,
                ContractDatabase.Produto.COLUNAS,
                where,
                whereArgs,
                null,
                null,
                null);
    }


}
