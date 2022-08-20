package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Sanduiche;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;

public class SanduichesTask extends AsyncTask<Sanduiche, Void, JSONObject> {

    private Context context;
    private AlertDialog progressDialog;
    private CallBackTask callBack;
    private ActionType action;

    public SanduichesTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }
    @Override
    protected JSONObject doInBackground(Sanduiche... sanduiches) {
        switch (this.action) {
            case READ: return new SanduichesWS().read();
            case READ_BY_ID: return new SanduichesWS().readById(sanduiches[0].getId());
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
