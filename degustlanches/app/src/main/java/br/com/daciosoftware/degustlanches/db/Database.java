package br.com.daciosoftware.degustlanches.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {

    private static SQLiteHelperDatabase helper;
    private static SQLiteDatabase db;

    public static synchronized SQLiteDatabase getDatabase(Context contexto) {
        if (helper == null) {
            helper = new SQLiteHelperDatabase(contexto);
        }
        return db = helper.getWritableDatabase();
    }

    public static void encerrarSessao() {
        if (db != null) {
            db.close();
        }
        if (helper != null) {
            helper.close();
        }
    }
}
