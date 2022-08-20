package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Pastel;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;

public class PasteisTask extends AsyncTask<Pastel, Void, JSONObject> {

    private Context context;
    private AlertDialog progressDialog;
    private CallBackTask callBack;
    private ActionType action;

    public PasteisTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }

    @Override
    protected JSONObject doInBackground(Pastel... pastels) {
        switch (this.action) {
            case READ: return new PasteisWS().read();
            case READ_BY_ID: return new PasteisWS().readById(pastels[0].getId());
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
