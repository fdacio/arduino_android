package br.com.daciosoftware.degustlanches.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fdacio on 21/01/17.
 */
public class NotificacaoDAO {

    private Context context;
    public static final String SHARED_PREF = "DegustSharedPref";

    public static final String NOTIFICA_CHAT = "NOTIFICA_CHAT";
    public static final boolean NOTIFICA_CHAT_DEFAULT = false;

    public static final String NOTIFICA_PEDIDOS = "NOTIFICA_PEDIDOS";
    public static final boolean NOTIFICA_PEDIDOS_DEFAULT = false;

    public static final String NOTIFICA_PROMOCOES = "NOTIFICA_PROMOCOES";
    public static final boolean NOTIFICA_PROMOCOES_DEFAULT = false;

    public NotificacaoDAO(Context context) {
        this.context = context;
    }

    public void setNotificaChat(boolean isNotifica) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICA_CHAT, isNotifica);
        editor.apply();
    }

    public boolean isNotificaChat() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, 0);
        return sharedPreferences.getBoolean(NOTIFICA_CHAT, NOTIFICA_CHAT_DEFAULT);
    }

    public void setNotificaPedidos(boolean isNotifica) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICA_PEDIDOS, isNotifica);
        editor.apply();
    }

    public boolean isNotificaPedido() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, 0);
        return sharedPreferences.getBoolean(NOTIFICA_PEDIDOS, NOTIFICA_PEDIDOS_DEFAULT);
    }

    public void setNotificaPromocoes(boolean isNotifica) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICA_PROMOCOES, isNotifica);
        editor.apply();
    }

    public boolean isNotificaPromocoes() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, 0);
        return sharedPreferences.getBoolean(NOTIFICA_PROMOCOES, NOTIFICA_PEDIDOS_DEFAULT);
    }

    public void saveDefaultValues() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NOTIFICA_CHAT, NOTIFICA_CHAT_DEFAULT);
        editor.putBoolean(NOTIFICA_PEDIDOS, NOTIFICA_PEDIDOS_DEFAULT);
        editor.putBoolean(NOTIFICA_PROMOCOES, NOTIFICA_PROMOCOES_DEFAULT);
        editor.apply();
    }


}
