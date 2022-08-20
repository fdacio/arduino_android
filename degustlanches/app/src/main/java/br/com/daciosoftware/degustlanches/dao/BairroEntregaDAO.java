package br.com.daciosoftware.degustlanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import br.com.daciosoftware.degustlanches.db.ContractDatabase;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Bairro;

public class BairroEntregaDAO {

    private SQLiteDatabase db;

    public BairroEntregaDAO(Context context) {
        this.db = Database.getDatabase(context);
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public Long save(Bairro bairro) throws SQLiteException {
        ContentValues values = new ContentValues();
        values.put(ContractDatabase.BairroEntrega.COLUNA_CODIGO, bairro.getId());
        values.put(ContractDatabase.BairroEntrega.COLUNA_NOME, bairro.getNome());
        values.put(ContractDatabase.BairroEntrega.COLUNA_TAXA_ENTREGA, bairro.getTaxaEntrega());
        return this.getDb().insertOrThrow(ContractDatabase.BairroEntrega.NOME_TABELA, "", values);
    }

    public int update(Bairro bairro) throws SQLiteException {
        ContentValues values = new ContentValues();
        values.put(ContractDatabase.BairroEntrega.COLUNA_CODIGO, bairro.getId());
        values.put(ContractDatabase.BairroEntrega.COLUNA_NOME, bairro.getNome());
        values.put(ContractDatabase.BairroEntrega.COLUNA_TAXA_ENTREGA, bairro.getTaxaEntrega());
        String where = "_id" + ">?";
        String[] whereArgs = new String[]{"0"};
        return this.getDb().update(ContractDatabase.BairroEntrega.NOME_TABELA, values, where, whereArgs);
    }

    public int deleteAll() throws SQLiteException {
        String where = "_id" + ">?";
        String[] whereArgs = new String[]{String.valueOf(0)};
        return db.delete(ContractDatabase.BairroEntrega.NOME_TABELA, where, whereArgs);
    }

    public Bairro getEntity(Cursor c) {
        if (c.getCount() > 0) {
            Bairro bairro = new Bairro();
            bairro.setId(c.getInt(c.getColumnIndex(ContractDatabase.BairroEntrega._ID)));
            bairro.setId(c.getInt(c.getColumnIndex(ContractDatabase.BairroEntrega.COLUNA_CODIGO)));
            bairro.setNome(c.getString(c.getColumnIndex(ContractDatabase.BairroEntrega.COLUNA_NOME)));
            bairro.setTaxaEntrega(c.getDouble(c.getColumnIndex(ContractDatabase.BairroEntrega.COLUNA_TAXA_ENTREGA)));
            return bairro;
        } else {
            return null;
        }
    }

    public Bairro getBairro() {
        Bairro bairro = new Bairro();
        try {
            String where = "_id" + ">?";
            String[] whereArgs = new String[]{"0"};
            Cursor cursor = getCursor(where, whereArgs);
            if (cursor.moveToFirst()) {
                do {
                    bairro = getEntity(cursor);

                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            throw new RuntimeException();

        }
        return bairro;
    }


    private Cursor getCursor(String where, String[] whereArgs) {
        return db.query(ContractDatabase.BairroEntrega.NOME_TABELA,
                ContractDatabase.BairroEntrega.COLUNAS,
                where,
                whereArgs,
                null,
                null,
                null);
    }

}
