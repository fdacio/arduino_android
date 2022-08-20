package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;

public class IngredientesTask extends AsyncTask<Produto, Void, JSONObject> {

    private Context context;
    private AlertDialog progressDialog;
    private CallBackTask callBack;
    private ActionType action;

    public IngredientesTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }

    @Override
    protected JSONObject doInBackground(Produto... produto) {
        switch (this.action) {
            case READ_INGREDIENTE_TIPO: return new IngredientesWS().readIngredienteTipo(produto[0].getTipo().ordinal()+1);
            case READ_BY_ID: return new IngredientesWS().readById(produto[0].getCodigo());
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
