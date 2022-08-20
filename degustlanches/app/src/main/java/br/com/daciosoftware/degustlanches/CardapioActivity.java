package br.com.daciosoftware.degustlanches;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.BairroEntregaDAO;
import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.model.EnderecoEntrega;
import br.com.daciosoftware.degustlanches.model.FormaPagamento;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoSituacao;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.ClientesTask;

public class CardapioActivity extends AppCompatActivity implements OnClickItemCardapio, CallBackTask {

    private RecyclerView recyclerView;
    private List menuCardapio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuCardapio = new ArrayList<ItemCardapio>();

        ItemCardapio itemCardapio1 = new ItemCardapio();
        itemCardapio1.setId(1);
        itemCardapio1.setLabel(getResources().getString(R.string.sanduiches));
        itemCardapio1.setIcon(getResources().getDrawable(R.mipmap.ic_sanduches));

        ItemCardapio itemCardapio2 = new ItemCardapio();
        itemCardapio2.setId(2);
        itemCardapio2.setLabel(getResources().getString(R.string.combos));
        itemCardapio2.setIcon(getResources().getDrawable(R.mipmap.ic_combos));

        ItemCardapio itemCardapio3 = new ItemCardapio();
        itemCardapio3.setId(3);
        itemCardapio3.setLabel(getResources().getString(R.string.pizzas));
        itemCardapio3.setIcon(getResources().getDrawable(R.mipmap.ic_pizzas));

        ItemCardapio itemCardapio4 = new ItemCardapio();
        itemCardapio4.setId(4);
        itemCardapio4.setLabel(getResources().getString(R.string.pasteis));
        itemCardapio4.setIcon(getResources().getDrawable(R.mipmap.ic_pasteis));

        ItemCardapio itemCardapio5 = new ItemCardapio();
        itemCardapio5.setId(5);
        itemCardapio5.setLabel(getResources().getString(R.string.bebidas));
        itemCardapio5.setIcon(getResources().getDrawable(R.mipmap.ic_bebidas));

        ItemCardapio itemCardapio6 = new ItemCardapio();
        itemCardapio6.setId(6);
        itemCardapio6.setLabel(getResources().getString(R.string.sucos_vitaminas));
        itemCardapio6.setIcon(getResources().getDrawable(R.mipmap.ic_sucosvitaminas));

        ItemCardapio itemCardapio7 = new ItemCardapio();
        itemCardapio7.setId(7);
        itemCardapio7.setLabel(getResources().getString(R.string.sushis));
        itemCardapio7.setIcon(getResources().getDrawable(R.mipmap.ic_sushi));

        ItemCardapio itemCardapio8 = new ItemCardapio();
        itemCardapio8.setId(8);
        itemCardapio8.setLabel(getResources().getString(R.string.acais));
        itemCardapio8.setIcon(getResources().getDrawable(R.mipmap.ic_acais));

        ItemCardapio itemCardapio9 = new ItemCardapio();
        itemCardapio9.setId(9);
        itemCardapio9.setLabel(getResources().getString(R.string.variedades));
        itemCardapio9.setIcon(getResources().getDrawable(R.mipmap.ic_variedades));

        menuCardapio.add(itemCardapio1);
        menuCardapio.add(itemCardapio2);
        menuCardapio.add(itemCardapio3);
        menuCardapio.add(itemCardapio4);
        menuCardapio.add(itemCardapio7);
        menuCardapio.add(itemCardapio8);
        menuCardapio.add(itemCardapio9);
        menuCardapio.add(itemCardapio5);
        menuCardapio.add(itemCardapio6);


        CardapioAdapter adapter = new CardapioAdapter(menuCardapio, CardapioActivity.this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.btnPedido);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PedidoActivity.class));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Cliente cliente = new ClienteDAO(CardapioActivity.this).getCliente();
        ClientesTask clientesTask = new ClientesTask(CardapioActivity.this, ActionType.READ_BY_ID, CardapioActivity.this);
        clientesTask.execute(cliente);
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
    public void onClickItemMenu(ItemCardapio itemCardapio) {
        switch (itemCardapio.getId()) {
            case 1:
                startActivity(new Intent(getApplicationContext(), MenuSanduichesActivity.class));
                break;

            case 2:
                startActivity(new Intent(getApplicationContext(), MenuCombosActivity.class));
                break;

            case 3:
                startActivity(new Intent(getApplicationContext(), MenuPizzasActivity.class));
                break;

            case 4:
                startActivity(new Intent(getApplicationContext(), MenuPasteisActivity.class));
                break;

            case 5:
                startActivity(new Intent(getApplicationContext(), MenuBebidasActivity.class));
                break;

            case 6:
                startActivity(new Intent(getApplicationContext(), MenuSucosVitaminasActivity.class));
                break;

            case 7:
                startActivity(new Intent(getApplicationContext(), MenuSushisActivity.class));
                break;

            case 8:
                startActivity(new Intent(getApplicationContext(), MenuAcaisActivity.class));
                break;
            case 9:
                startActivity(new Intent(getApplicationContext(), MenuVariedadesActivity.class));
                break;
        }
    }

    @Override
    public void onClickItemMenu(DialogCardapio.ItemCardapio itemCardapio) {

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

            /**
             * Aqui cria-se o PEDIDO
             */
            if (jsonObject.has("cliente")) {

                PedidoDAO pedidoDAO = new PedidoDAO(CardapioActivity.this);

                if (pedidoDAO.getPedido() == null) {

                    JSONObject jsonCliente = jsonObject.getJSONObject("cliente");
                    Cliente cliente = new Cliente(jsonCliente);

                    Pedido pedido = new Pedido();
                    pedido.setCliente(cliente);
                    pedido.setSituacao(PedidoSituacao.INICIADO);
                    pedido.setFormaPagamento(FormaPagamento.AINFORMAR);
                    EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
                    enderecoEntrega.setEndereco(cliente.getEndereco());
                    enderecoEntrega.setNumero(cliente.getNumero());
                    enderecoEntrega.setComplemento(cliente.getComplemento());
                    enderecoEntrega.setBairro(cliente.getBairro());
                    enderecoEntrega.setPontoReferencia(cliente.getPontoReferencia());
                    pedido.setEnderecoEntrega(enderecoEntrega);
                    pedido.setDesconto(0);
                    pedido.setTaxaEntrega(cliente.getBairro().getTaxaEntrega());
                    pedidoDAO.save(pedido);
                    BairroEntregaDAO bairroEntregaDAO = new BairroEntregaDAO(CardapioActivity.this);
                    bairroEntregaDAO.save(cliente.getBairro());
                }
            }

        } catch (Exception e) {
            new DialogBox(CardapioActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }

    public class ItemCardapio {
        private int id;
        private String label;
        private Drawable icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }

}
