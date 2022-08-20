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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.BairroEntregaDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Bairro;
import br.com.daciosoftware.degustlanches.model.EnderecoEntrega;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.util.DeviceInformation;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.BairrosTask;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;

public class EnderecoEntregaActivity extends AppCompatActivity implements CallBackTask {

    private EditText editTextEndereco;
    private EditText editTextNumero;
    private EditText editTextBairro;
    private EditText editTextComplemento;
    private EditText editTextPontoReferencia;
    private Button btnAlterar;
    private ImageButton btnBairro;
    private Bairro bairro;
    private List<Bairro> listBairros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco_entrega);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextPontoReferencia = findViewById(R.id.editTextPontoReferencia);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextBairro.setKeyListener(null);
        btnBairro = findViewById(R.id.btnBairros);
        btnAlterar = findViewById(R.id.btnAlterar);

        btnBairro.setOnClickListener(new BairroOnClickListener());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Pedido pedido = (Pedido) bundle.getSerializable("pedido");
            editTextEndereco.setText(pedido.getEnderecoEntrega().getEndereco());
            editTextNumero.setText(pedido.getEnderecoEntrega().getNumero());
            bairro = new BairroEntregaDAO(EnderecoEntregaActivity.this).getBairro();
            editTextBairro.setText(bairro.getNome());
            editTextComplemento.setText(pedido.getEnderecoEntrega().getComplemento());
            editTextPontoReferencia.setText(pedido.getEnderecoEntrega().getPontoReferencia());
        }

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endereco = editTextEndereco.getText().toString();
                String numero = editTextNumero.getText().toString();
                String bairroNome = editTextBairro.getText().toString();
                String complemento = editTextComplemento.getText().toString();
                String pontoReferencia = editTextPontoReferencia.getText().toString();

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

                Pedido pedido = new PedidoDAO(EnderecoEntregaActivity.this).getPedido();
                EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
                enderecoEntrega.setEndereco(endereco);
                enderecoEntrega.setNumero(numero);
                enderecoEntrega.setComplemento(complemento);
                enderecoEntrega.setBairro(bairro);
                enderecoEntrega.setPontoReferencia(pontoReferencia);
                pedido.setEnderecoEntrega(enderecoEntrega);

                new PedidoDAO(EnderecoEntregaActivity.this).save(pedido);

                new BairroEntregaDAO(EnderecoEntregaActivity.this).update(bairro);

                finish();

                Toast.makeText(EnderecoEntregaActivity.this, getResources().getString(R.string.msg_altera_endereco_entrega), Toast.LENGTH_LONG).show();

            }
        });

    }

    private class BairroOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (DeviceInformation.isNetwork(EnderecoEntregaActivity.this)) {
                BairrosTask bairrosTask = new BairrosTask(EnderecoEntregaActivity.this, ActionType.READ, EnderecoEntregaActivity.this);
                bairrosTask.execute();
            } else {
                new DialogBox(EnderecoEntregaActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
            }
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

    @Override
    public void post(JSONObject jsonObject) {

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

            if (jsonObject.has("bairros")) {
                JSONArray jsonArrayBairros = jsonObject.getJSONArray("bairros");
                String[] arrayBairros = new String[jsonArrayBairros.length()];
                for (int i = 0; i < jsonArrayBairros.length(); i++) {
                    Bairro bairro = new Bairro(jsonArrayBairros.getJSONObject(i));
                    listBairros.add(bairro);
                    arrayBairros[i] = bairro.getNome();
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(EnderecoEntregaActivity.this, R.style.MyAlertDialog);
                builder
                        .setTitle(R.string.informe_bairro)
                        .setItems(arrayBairros, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                editTextBairro.setText(listBairros.get(position).getNome());
                                bairro = listBairros.get(position);
                                builder.create().dismiss();
                             }
                        });
                builder.create().show();
            }

        } catch (Exception e) {
            new DialogBox(EnderecoEntregaActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }
}
