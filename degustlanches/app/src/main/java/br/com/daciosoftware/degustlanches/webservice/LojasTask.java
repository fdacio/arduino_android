package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Loja;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;


public class LojasTask extends AsyncTask<Loja, Void, JSONObject> {

    private Context context;
    private AlertDialog progressDialog;
    private CallBackTask callBack;
    private ActionType action;

    public LojasTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }

    @Override
    protected JSONObject doInBackground(Loja... lojas) {
        switch (this.action) {
            case READ: return new LojasWS().read();
            case READ_BY_ID: return new LojasWS().readById(lojas[0].getId());
            case READ_HF: return new LojasWS().horarioFuncionamento(lojas[0].getId());
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
