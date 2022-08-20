package br.com.daciosoftware.degustlanches.notificacao;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.model.Chat;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.webservice.ChatsWS;


public class ChatNaoLido {

    private Context contex;

    public ChatNaoLido(Context context) {
        this.contex = context;
    }

    public List<Chat> get() {

        List<Chat> chats = new ArrayList<>();

        try {

            Cliente cliente = new ClienteDAO(contex).getCliente();
            JSONObject jsonObject = new ChatsWS().getNaoLidos(cliente);
            JSONArray chatsJson = jsonObject.getJSONArray("chats");

            for (int i = 0; i < chatsJson.length(); i++) {
                JSONObject chatJson = chatsJson.getJSONObject(i);
                Chat chat1 = new Chat(chatJson);
                chats.add(chat1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chats;

    }
}
