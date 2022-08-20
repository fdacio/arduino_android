package br.com.daciosoftware.degustlanches;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.dao.BairroEntregaDAO;
import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.util.DeviceInformation;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.util.MaskEditInput;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.ClientesTask;


public class BuscarClienteActivity extends AppCompatActivity implements CallBackTask {

    private EditText editTextCelular;
    private EditText editTextEmail;
    private ProgressBar progressBar;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextCelular = (EditText) findViewById(R.id.editTextCelular);
        editTextCelular.addTextChangedListener(MaskEditInput.mask(editTextCelular, MaskEditInput.FORMAT_CELULAR));
        editTextCelular.setText("(85)");
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String celular = editTextCelular.getText().toString();
                String email = editTextEmail.getText().toString();

                celular = celular.replaceAll("[^0-9]", "");

                if (celular.length() != 11) {
                    new DialogBox(BuscarClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.celular_invalido), false).show();
                    return;
                }

                Cliente cliente = new Cliente();
                cliente.setCelular(celular);
                cliente.setEmail(email);

                if (!celular.isEmpty()) {

                    if (DeviceInformation.isNetwork(BuscarClienteActivity.this)) {
                        new ClientesTask(BuscarClienteActivity.this, ActionType.READ_BY_CELULAR, BuscarClienteActivity.this).execute(cliente);
                    } else {
                        new DialogBox(BuscarClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
                    }

                } else if (!email.isEmpty()) {
                    if (DeviceInformation.isNetwork(BuscarClienteActivity.this)) {
                        new ClientesTask(BuscarClienteActivity.this, ActionType.READ_BY_EMAIL, BuscarClienteActivity.this).execute(cliente);
                    } else {
                        new DialogBox(BuscarClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
                    }
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.encerrarSessao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_degust, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void post(JSONObject jsonObject) {

    }

    @Override
    public void get(JSONObject jsonObject) {

        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            if (jsonObject.getBoolean("success")) {
                Cliente cliente = new Cliente(jsonObject.getJSONObject("cliente"));
                ClienteDAO clienteDAO = new ClienteDAO(BuscarClienteActivity.this);
                clienteDAO.save(cliente);
                BairroEntregaDAO bairroEntregaDAO = new BairroEntregaDAO(BuscarClienteActivity.this);
                bairroEntregaDAO.save(cliente.getBairro());
                Intent intent = new Intent(BuscarClienteActivity.this, CadastroClienteActivity.class);
                intent.putExtra("ACAO", CadastroClienteActivity.UPDATE);
                startActivity(intent);

            } else {
                Intent intent = new Intent(BuscarClienteActivity.this, CadastroClienteActivity.class);
                intent.putExtra("ACAO", CadastroClienteActivity.CREATE);
                intent.putExtra("CELULAR", editTextCelular.getText().toString());
                intent.putExtra("EMAIL", editTextEmail.getText().toString());
                startActivity(intent);
            }

            finish();

        } catch (Exception e) {
            new DialogBox(BuscarClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.error_geral), false).show();
            e.printStackTrace();
        }
    }

    private class OnClickYesDialog implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(BuscarClienteActivity.this, CadastroClienteActivity.class);
            intent.putExtra("ACAO", 1);
            startActivity(intent);
            BuscarClienteActivity.this.finish();
        }
    }

    private class OnClickNoDialog implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

}
