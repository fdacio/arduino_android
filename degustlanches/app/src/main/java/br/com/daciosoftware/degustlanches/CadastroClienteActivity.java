package br.com.daciosoftware.degustlanches;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.BairroEntregaDAO;
import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Bairro;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.util.DeviceInformation;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.util.MaskEditInput;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.BairrosTask;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.ClientesTask;


public class CadastroClienteActivity extends AppCompatActivity implements CallBackTask {

    public static int CREATE = 1;
    public static int UPDATE = 2;
    private int actionType;

    private EditText editTextNome;
    private EditText editTextCelular;
    private EditText editTextEmail;
    private EditText editTextEndereco;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextComplemento;
    private EditText editTextPontoReferencia;
    private ProgressBar progressBar;
    private Button btnSalvar;
    private ImageButton btnBairro;
    private Bairro bairro = new Bairro();
    private List<Bairro> listBairros = new ArrayList<>();

    private int codigo;
    private int bairros_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.inicitializeForm();

        actionType = getIntent().getIntExtra ("ACAO", CREATE);

        if (actionType == CREATE) {
            editTextCelular.setText(getIntent().getStringExtra("CELULAR"));
            editTextEmail.setText(getIntent().getStringExtra("EMAIL"));
        }

        if (actionType == UPDATE) {
            this.loadCliente();
        }
    }

    private void inicitializeForm() {

        editTextNome = findViewById(R.id.editTextNome);
        editTextCelular = findViewById(R.id.editTextCelular);
        editTextCelular.addTextChangedListener(MaskEditInput.mask(editTextCelular, MaskEditInput.FORMAT_CELULAR));
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextPontoReferencia = findViewById(R.id.editTextPontoReferencia);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextBairro.setKeyListener(null);
        btnBairro = findViewById(R.id.btnBairros);
        btnSalvar = findViewById(R.id.btnSalvar);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        btnBairro.setOnClickListener(new BairroOnClickListener());
        btnSalvar.setOnClickListener(new SalvarOnClickListener());
    }

    private void loadCliente() {
        if (DeviceInformation.isNetwork(CadastroClienteActivity.this)) {
            Cliente cliente = new ClienteDAO(CadastroClienteActivity.this).getCliente();
            ClientesTask clientesTask = new ClientesTask(CadastroClienteActivity.this, ActionType.READ_BY_ID, CadastroClienteActivity.this);
            clientesTask.execute(cliente);
        } else {
            new DialogBox(CadastroClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
        }
    }

    private void setClienteForm(Cliente cliente) {
        editTextNome.setText(cliente.getNome());
        editTextCelular.setText(cliente.getCelular());
        editTextEmail.setText(cliente.getEmail());
        editTextEndereco.setText(cliente.getEndereco());
        editTextNumero.setText(cliente.getNumero());
        editTextBairro.setText(cliente.getBairro().getNome());
        editTextComplemento.setText(cliente.getComplemento());
        editTextPontoReferencia.setText(cliente.getPontoReferencia());
    }

    private class BairroOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (DeviceInformation.isNetwork(CadastroClienteActivity.this)) {
                BairrosTask bairrosTask = new BairrosTask(CadastroClienteActivity.this, ActionType.READ, CadastroClienteActivity.this);
                bairrosTask.execute();
            } else {
                new DialogBox(CadastroClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
            }
        }
    }

    private class SalvarOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String nome = editTextNome.getText().toString();
            String celular = editTextCelular.getText().toString().replaceAll("[^0-9]", "");
            String email = editTextEmail.getText().toString();
            String endereco = editTextEndereco.getText().toString();
            String numero = editTextNumero.getText().toString();
            String bairroNome = editTextBairro.getText().toString();

            String complemento = editTextComplemento.getText().toString();
            String pontoReferencia = editTextPontoReferencia.getText().toString();

            if (nome.isEmpty()) {
                editTextNome.setError("Informe seu Nome");
                return;
            }
            if (celular.isEmpty()) {
                editTextCelular.setError("Informe seu Celular");
                return;
            }

            if (celular.length() != 11) {
                editTextCelular.setError("Número de Celular Inválido");
                return;
            }

            if (endereco.isEmpty()) {
                editTextEndereco.setError("Informe seu Endereço");
                return;
            }
            if (numero.isEmpty()) {
                editTextNumero.setError("Informe o Número do seu endereço");
                return;
            }

            if (bairroNome.isEmpty()) {
                editTextBairro.setError("Informe o Bairro");
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setCodigo(codigo);
            cliente.setNome(nome);
            cliente.setCelular(celular);
            cliente.setEmail(email);
            cliente.setEndereco(endereco);
            cliente.setNumero(numero);
            cliente.setBairro(bairro);
            cliente.setBairros_id(bairros_id);
            cliente.setComplemento(complemento);
            cliente.setPontoReferencia(pontoReferencia);

            ClientesTask clientesTask;
            if (actionType == CREATE) {
                clientesTask = new ClientesTask(CadastroClienteActivity.this, ActionType.CREATE, CadastroClienteActivity.this);
            } else {
                clientesTask = new ClientesTask(CadastroClienteActivity.this, ActionType.UPDATE, CadastroClienteActivity.this);
            }
            clientesTask.execute(cliente);
        }
    }

    @Override
    public void post(JSONObject jsonObject) {

        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("message"));
            }

            if (!jsonObject.has("cliente")) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            JSONObject jsonCliente = jsonObject.getJSONObject("cliente");
            JSONObject jsonBairro = jsonCliente.getJSONObject("bairro");
            int codigo = jsonCliente.optInt("id");
            Cliente cliente = new Cliente();
            cliente.setCodigo(codigo);
            new ClienteDAO(CadastroClienteActivity.this).save(cliente);
            Bairro bairroEntrega = new Bairro(jsonBairro);
              new BairroEntregaDAO(CadastroClienteActivity.this).save(bairroEntrega);
            new DialogBox(CadastroClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), jsonObject.optString("message"), true).show();

        } catch (Exception e) {
            new DialogBox(CadastroClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }

    @Override
    public void get(JSONObject jsonObject) {
        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("message"));
            }

            if (jsonObject.has("cliente")) {
                JSONObject jsonCliente = jsonObject.getJSONObject("cliente");
                JSONObject jsonBairro = jsonCliente.getJSONObject("bairro");
                Cliente cliente = new Cliente(jsonCliente);
                codigo = cliente.getCodigo();
                bairros_id = cliente.getBairros_id();
                bairro = new Bairro(jsonBairro);
                setClienteForm(cliente);
            }

            if (jsonObject.has("bairros")) {
                JSONArray jsonArrayBairros = jsonObject.getJSONArray("bairros");
                String[] arrayBairros = new String[jsonArrayBairros.length()];
                for (int i = 0; i < jsonArrayBairros.length(); i++) {
                    Bairro bairro = new Bairro(jsonArrayBairros.getJSONObject(i));
                    listBairros.add(bairro);
                    arrayBairros[i] = bairro.getNome();
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(CadastroClienteActivity.this, R.style.MyAlertDialog);
                builder
                        .setTitle(R.string.informe_bairro)
                        .setItems(arrayBairros, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                builder.create().dismiss();
                                editTextBairro.setText(listBairros.get(position).getNome());
                                Bairro bairro = listBairros.get(position);
                                bairros_id = bairro.getId();
                            }
                        });
                builder.create().show();
            }

        } catch (Exception e) {
            new DialogBox(CadastroClienteActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
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

}
