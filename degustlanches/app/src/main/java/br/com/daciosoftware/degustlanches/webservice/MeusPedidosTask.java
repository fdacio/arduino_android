package br.com.daciosoftware.degustlanches.webservice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.util.ProgressDialog;

public class MeusPedidosTask extends AsyncTask<Cliente, Void, JSONObject> {

    private AlertDialog progressDialog;
    private Context context;
    private CallBackTask callBack;
    private ActionType action;

    public MeusPedidosTask(Context context, ActionType action, CallBackTask callBack) {
        this.context = context;
        this.callBack = callBack;
        this.action = action;
    }

    @Override
    protected JSONObject doInBackground(Cliente... clientes) {
        switch (this.action) {
            case READ_BY_CLIENTE:
                return new PedidosWS().readByCliente(clientes[0].getCodigo());
            default:
                return null;
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
        if ((this.action == ActionType.CREATE) || (this.action == ActionType.UPDATE)) {
            this.callBack.post(jsonObject);
        } else {
            this.callBack.get(jsonObject);
        }
    }
}
