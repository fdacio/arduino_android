package br.com.daciosoftware.degustlanches;

import android.content.DialogInterface;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.model.EnderecoEntrega;
import br.com.daciosoftware.degustlanches.model.FormaPagamento;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.PedidoSituacao;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.util.MyDateUtil;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.MeusPedidosTask;
import br.com.daciosoftware.degustlanches.webservice.PedidosTask;

public class MeusPedidosActivity extends AppCompatActivity implements OnClickItemPedido, CallBackTask {

    private RecyclerView recyclerViewMeusPedidos;
    private List<Pedido> pedidosList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pedidos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewMeusPedidos = findViewById(R.id.recycleViewMeusPedidos);
        recyclerViewMeusPedidos.setHasFixedSize(true);
        recyclerViewMeusPedidos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loadPedidos();
    }

    private void loadPedidos() {

        Cliente cliente = new ClienteDAO(MeusPedidosActivity.this).getCliente();
        MeusPedidosTask meusPedidosTask = new MeusPedidosTask(MeusPedidosActivity.this, ActionType.READ_BY_CLIENTE, MeusPedidosActivity.this);
        meusPedidosTask.execute(cliente);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_meus_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_reload:
                loadPedidos();
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

            if (jsonObject.has("pedidos")) {
                pedidosList.clear();
                JSONArray jsonPedidos = jsonObject.getJSONArray("pedidos");
                for (int i = 0; i < jsonPedidos.length(); i++) {
                    JSONObject jsonPedido = jsonPedidos.getJSONObject(i);
                    Pedido pedido = new Pedido();
                    pedido.setId(jsonPedido.optInt("id"));
                    pedido.setData_hora(MyDateUtil.dateTimeUSToCalendar(jsonPedido.getString("data_hora")));
                    pedido.setSituacao(PedidoSituacao.fromOrdinal(jsonPedido.getInt("situacao")));
                    pedido.setFormaPagamento(FormaPagamento.fromOrdinal(jsonPedido.getInt("forma_pagamento")));
                    pedido.setTrocaPara(jsonPedido.getDouble("troco_para"));
                    pedido.setPrevisaoEntraga(jsonPedido.getInt("previsao_entrega"));
                    pedido.setDesconto(jsonPedido.getDouble("desconto"));
                    pedido.setTaxaEntrega(jsonPedido.getDouble("taxa_entrega"));
                    pedido.setEnderecoEntrega(new EnderecoEntrega(jsonPedido.getJSONObject("endereco_entrega")));
                    JSONArray jsonPedidoProdutos = jsonPedido.getJSONArray("produtos_pedido");
                    for (int j = 0; j < jsonPedidoProdutos.length(); j++) {
                        JSONObject jsonPedidoProduto = jsonPedidoProdutos.getJSONObject(j);
                        Produto produto = new Produto();
                        produto.setNome(jsonPedidoProduto.getString("nome"));
                        produto.setIngredientes(jsonPedidoProduto.getString("ingredientes"));
                        PedidoProduto pedidoProduto = new PedidoProduto();
                        pedidoProduto.setObservacoes(jsonPedidoProduto.getString("observacoes"));
                        pedidoProduto.setPreco(jsonPedidoProduto.getDouble("preco"));
                        pedidoProduto.setProduto(produto);
                        pedido.addProduto(pedidoProduto);
                    }
                    pedidosList.add(pedido);
                }

                MeusPedidosAdapter adapter = new MeusPedidosAdapter(pedidosList, MeusPedidosActivity.this, MeusPedidosActivity.this);
                recyclerViewMeusPedidos.setAdapter(adapter);
            }

            if (jsonObject.has("pedido")) {

                new DialogBox(MeusPedidosActivity.this, DialogBox.DialogBoxType.INFORMATION,
                        getResources().getString(R.string.app_name),
                        jsonObject.getString("message"),
                        new OnClickYesReloadDialog()
                        ).show();
            }

        } catch (Exception e) {
            new DialogBox(MeusPedidosActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onClickItem(Pedido pedido) {

        new DialogBox(MeusPedidosActivity.this, DialogBox.DialogBoxType.QUESTION,
                "Cancelar Pedido",
                "Confirmar o Cancelamento do Pedido?",
                new MeusPedidosActivity.OnClickYesDialog(pedido),
                new MeusPedidosActivity.OnClickNoDialog()).show();

    }

    private class OnClickYesDialog implements DialogInterface.OnClickListener {

        private Pedido pedido;

        public OnClickYesDialog(Pedido pedido) {
            this.pedido = pedido;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            PedidosTask pedidosTask = new PedidosTask(MeusPedidosActivity.this, ActionType.CANCEL, MeusPedidosActivity.this);
            pedidosTask.execute(pedido);
        }
    }

    private class OnClickNoDialog implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private class OnClickYesReloadDialog implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            loadPedidos();
        }
    }

}
