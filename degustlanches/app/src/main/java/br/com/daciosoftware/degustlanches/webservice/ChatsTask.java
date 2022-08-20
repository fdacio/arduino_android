package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.ChatActivity;
import br.com.daciosoftware.degustlanches.model.Chat;

public class ChatsTask extends AsyncTask<Chat, Void, JSONObject> {

    private Context context;
    private ProgressBar progressBar;
    private CallBackTask callBack;
    private ActionType action;

    public ChatsTask(Context context, ActionType action) {
        this.context = context;
        this.action = action;
    }

    public ChatsTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
        if((action == ActionType.CREATE)||(action == ActionType.DELETE)) {
            this.progressBar = ((ChatActivity) context).progressBarChat;
        }
    }

    @Override
    protected JSONObject doInBackground(Chat... chats) {
        switch (this.action) {
            case CREATE:
                return new ChatsWS().create(chats[0]);
            case UPDATE:
                return new ChatsWS().update(chats[0]);
            case DELETE:
                return new ChatsWS().delete(chats[0]);
            case READ:
                return new ChatsWS().readClienteById(chats[0]);
            case READ_CLIENTE_NAO_LIDA:
                return null;
                //return new ChatsWS().getNaoLidos(chats[0]);

            default:
                return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (this.action == ActionType.CREATE) {
            this.callBack.post(jsonObject);
        } else {
            if (this.action == ActionType.READ) {
                this.callBack.get(jsonObject);
            }
        }
    }

}
