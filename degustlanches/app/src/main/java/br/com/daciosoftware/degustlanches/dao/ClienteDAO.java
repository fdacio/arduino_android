package br.com.daciosoftware.degustlanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import br.com.daciosoftware.degustlanches.db.ContractDatabase;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Cliente;

/**
 * Created by fdacio on 06/07/17.
 */
public class ClienteDAO {

    private SQLiteDatabase db;

    public ClienteDAO(Context context) {
        this.db = Database.getDatabase(context);
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public Long save(Cliente cliente) throws SQLiteException {
        ContentValues values = new ContentValues();
        values.put(ContractDatabase.Cliente.COLUNA_CODIGO, cliente.getCodigo());
        return this.getDb().insertOrThrow(ContractDatabase.Cliente.NOME_TABELA, "", values);
    }

    public int update() throws SQLiteException {
        ContentValues values = new ContentValues();
        values.put(ContractDatabase.Cliente.COLUNA_CODIGO, 1);
        String where = "_id" + ">?";
        String[] whereArgs = new String[]{"0"};
        return this.getDb().update(ContractDatabase.Cliente.NOME_TABELA, values, where, whereArgs);
    }

    public Cliente getEntity(Cursor c) {
        if (c.getCount() > 0) {
            Cliente cliente = new Cliente();
            cliente.setId(c.getInt(c.getColumnIndex(ContractDatabase.Cliente._ID)));
            cliente.setCodigo(c.getInt(c.getColumnIndex(ContractDatabase.Cliente.COLUNA_CODIGO)));
            return cliente;
        } else {
            return null;
        }
    }

    public Cliente getCliente() {
        Cliente cliente = null;
        try {
            String where = "_id" + ">?";
            String[] whereArgs = new String[]{"0"};
            Cursor cursor = getCursor(where, whereArgs);
            if (cursor.moveToFirst()) {
                do {
                    cliente = getEntity(cursor);

                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return cliente;
    }


    private Cursor getCursor(String where, String[] whereArgs) {
        return db.query(ContractDatabase.Cliente.NOME_TABELA,
                ContractDatabase.Cliente.COLUNAS,
                where,
                whereArgs,
                null,
                null,
                null);
    }

}
