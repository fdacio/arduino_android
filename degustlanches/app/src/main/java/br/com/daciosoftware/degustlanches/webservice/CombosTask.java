package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Combo;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;

public class CombosTask extends AsyncTask<Combo, Void, JSONObject> {

    private Context context;
    private AlertDialog progressDialog;
    private CallBackTask callBack;
    private ActionType action;

    public CombosTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }

    @Override
    protected JSONObject doInBackground(Combo... combos) {
        switch (this.action) {
            case READ: return new CombosWS().read();
            case READ_BY_ID: return new CombosWS().readById(combos[0].getId());
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
