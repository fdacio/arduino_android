package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Configuracao;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;

public class ConfiguracoesTask extends AsyncTask<Void, Void, JSONObject> {

    private Context context;
    private AlertDialog progressDialog;
    private CallBackTask callBack;
    private ActionType action;

    public ConfiguracoesTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        switch (this.action) {
            case READ: return new ConfiguracoesWS().read();
            case READ_BY_ID: return new ConfiguracoesWS().readById(1);
            default: return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog().dialog(context);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        progressDialog.dismiss();
        this.callBack.get(jsonObject);
    }
}
