package br.com.daciosoftware.degustlanches;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.model.FormaPagamento;
import br.com.daciosoftware.degustlanches.model.Ingrediente;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.ProdutoTipo;
import br.com.daciosoftware.degustlanches.util.DeviceInformation;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.ClientesTask;
import br.com.daciosoftware.degustlanches.webservice.ConfiguracoesTask;
import br.com.daciosoftware.degustlanches.webservice.IngredientesTask;
import br.com.daciosoftware.degustlanches.webservice.PedidosTask;
import br.com.daciosoftware.degustlanches.webservice.ProdutosTask;


public class PedidoActivity extends AppCompatActivity implements CallBackTask, OnClickItemProdutoPedido {

    private TextView textViewNome;
    private TextView textViewEndereco;
    private TextView textViewBairro;
    private TextView textViewValorTotal;
    private TextView textViewTaxaEntrega;
    private TextView textViewDesconto;
    private TextView textViewValorFinal;
    private Button btnFormaPagamento;
    private Button btnTrocoPara;
    private RecyclerView recyclerViewPedidoProdutos;
    private PedidoAdapter pedidoAdapter;
    private PedidoProduto pedidoProduto;
    private double taxaDesconto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewNome = findViewById(R.id.textViewNome);
        textViewEndereco = findViewById(R.id.textViewEndereco);
        textViewBairro = findViewById(R.id.textViewBairro);
        textViewValorTotal = findViewById(R.id.textViewValorTotal);
        textViewDesconto = findViewById(R.id.textViewDesconto);
        textViewTaxaEntrega = findViewById(R.id.textViewTaxaEntrega);
        textViewValorFinal = findViewById(R.id.textViewValorFinal);
        btnFormaPagamento = findViewById(R.id.buttonFormaPagamento);
        btnTrocoPara = findViewById(R.id.buttonTrocoPara);
        recyclerViewPedidoProdutos = findViewById(R.id.recyclerViewPedidoProdutos);
        recyclerViewPedidoProdutos.setHasFixedSize(true);
        recyclerViewPedidoProdutos.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabCardapio = findViewById(R.id.fabCardapio);
        fabCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCardapio dialogCardapio = new DialogCardapio(PedidoActivity.this);
                dialogCardapio.show();
            }
        });

        /*
        FloatingActionButton fabSanduiches = findViewById(R.id.fab_sanduiches);
        fabSanduiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuSanduichesActivity.class));
                finish();
            }
        });

        FloatingActionButton fabCombos = findViewById(R.id.fab_combos);
        fabCombos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuCombosActivity.class));
                finish();
            }
        });

        FloatingActionButton fabPizzas = findViewById(R.id.fab_pizzas);
        fabPizzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuPizzasActivity.class));
                finish();
            }
        });

        FloatingActionButton fabPasteis = findViewById(R.id.fab_pasteis);
        fabPasteis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuPasteisActivity.class));
                finish();
            }
        });

        FloatingActionButton fabSushis = findViewById(R.id.fab_sushis);
        fabSushis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuSushisActivity.class));
                finish();
            }
        });

        FloatingActionButton fabAcais = findViewById(R.id.fab_acais);
        fabAcais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuAcaisActivity.class));
                finish();
            }
        });

        FloatingActionButton fabVariedades = findViewById(R.id.fab_variedades);
        fabVariedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuVariedadesActivity.class));
                finish();
            }
        });

        FloatingActionButton fabBebidas = findViewById(R.id.fab_bebidas);
        fabBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuBebidasActivity.class));
                finish();
            }
        });

        FloatingActionButton fabSucosVitaminas = findViewById(R.id.fab_sucos_vitaminas);
        fabSucosVitaminas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuSucosVitaminasActivity.class));
                finish();
            }
        });

        */

        Cliente cliente = new ClienteDAO(PedidoActivity.this).getCliente();
        ClientesTask clientesTask = new ClientesTask(PedidoActivity.this, ActionType.READ_BY_ID, PedidoActivity.this);
        clientesTask.execute(cliente);

        ConfiguracoesTask configuracoesTask = new ConfiguracoesTask(PedidoActivity.this, ActionType.READ_BY_ID, PedidoActivity.this);
        configuracoesTask.execute();

        btnFormaPagamento.setOnClickListener(new DialogFormaPagamento(PedidoActivity.this));
        btnTrocoPara.setOnClickListener(new DialogTrocoPara(PedidoActivity.this));
        ImageButton btnEnderecoEntrega = findViewById(R.id.btnEnderecoEntrega);
        btnEnderecoEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedidoActivity.this, EnderecoEntregaActivity.class);
                Pedido pedido = new PedidoDAO(PedidoActivity.this).getPedido();
                intent.putExtra("pedido", pedido);
                startActivity(intent);
            }
        });
        Button btnEnviaPedido = findViewById(R.id.buttonEnviar);
        btnEnviaPedido.setOnClickListener(new EnviaPedidoOnClickListener());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.encerrarSessao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDadosPedido();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_degust, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

            if (!jsonObject.has("pedido")) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
            Pedido pedido = pedidoDAO.getPedido();
            pedidoDAO.delete(pedido);

            new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), jsonObject.optString("message"), true).show();

        } catch (Exception e) {
            new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
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
                Cliente cliente = new Cliente(jsonCliente);
                setDadosClente(cliente);
            }

            if (jsonObject.has("produto")) {

                JSONObject jsonProduto = jsonObject.getJSONObject("produto");
                JSONArray jsonIngredientes = jsonProduto.getJSONArray("ingredientes");
                List<Ingrediente> ingredientes = new ArrayList<>();
                for (int i = 0; i < jsonIngredientes.length(); i++) {
                    JSONObject jsonIngrediente = jsonIngredientes.getJSONObject(i);
                    if (jsonIngrediente.getJSONObject("ingrediente").getBoolean("ativo")) {
                        Ingrediente ingrediente = new Ingrediente(jsonIngrediente.getJSONObject("ingrediente"));
                        ingredientes.add(ingrediente);
                    }
                }

                DialogDelIngrediente dialogDelIngrediente = new DialogDelIngrediente(PedidoActivity.this, ingredientes, this.pedidoProduto);
                dialogDelIngrediente.show();
            }

            if (jsonObject.has("ingredientes")) {

                JSONArray jsonIngredientes = jsonObject.getJSONArray("ingredientes");
                List<Ingrediente> ingredientes = new ArrayList<>();
                for (int i = 0; i < jsonIngredientes.length(); i++) {
                    JSONObject jsonIngrediente = jsonIngredientes.getJSONObject(i);
                    if (jsonIngrediente.getBoolean("ativo")) {
                        Ingrediente ingrediente = new Ingrediente(jsonIngrediente);
                        ingredientes.add(ingrediente);
                    }
                }

                DialogAddIngrediente dialogAddIngrediente = new DialogAddIngrediente(PedidoActivity.this, ingredientes, this.pedidoProduto);
                dialogAddIngrediente.show();
            }

            if (jsonObject.has("configuracao")) {

                JSONObject jsonConfiguracao = jsonObject.getJSONObject("configuracao");
                taxaDesconto = jsonConfiguracao.optDouble("desconto");
                setDadosPedido();

            }

        } catch (Exception e) {
            new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }

    public void setDadosClente(Cliente cliente) {

        textViewNome.setText(cliente.getNome() + " - " + cliente.getCelular());
        String enderecoCompleto = cliente.getEndereco() + ", " + cliente.getNumero();
        textViewEndereco.setText(enderecoCompleto);
        String bairroNome = cliente.getBairro().getNome();
        if (!cliente.getComplemento().isEmpty()) {
            bairroNome = cliente.getComplemento() + " - " + bairroNome;
        }
        textViewBairro.setText(bairroNome);
    }

    public void setDadosPedido() {

        PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
        Pedido pedido = pedidoDAO.getPedido();

        String formaPagamento = getResources().getString(R.string.forma_pagamento);
        formaPagamento = String.format(formaPagamento, pedido.getFormaPagamento().getDescricao());
        btnFormaPagamento.setText(formaPagamento);

        String trocoPara = getResources().getString(R.string.troco_para);
        trocoPara = String.format(trocoPara, pedido.getTrocaPara());
        btnTrocoPara.setText(trocoPara);

        this.pedidoAdapter = new PedidoAdapter(pedido.getPedidoProdutos(), PedidoActivity.this);
        this.recyclerViewPedidoProdutos.setAdapter(pedidoAdapter);

        String valorTotal = getResources().getString(R.string.valor_total);
        valorTotal = String.format(valorTotal, pedido.getValorTotal());
        textViewValorTotal.setText(valorTotal);

        double taxaEntregaBairro = 0;
        if ((pedido.getEnderecoEntrega().getBairro() != null) && (pedido.getQtdeItens() > 0)) {
            taxaEntregaBairro = pedido.getEnderecoEntrega().getBairro().getTaxaEntrega();
        }
        pedido.setTaxaEntrega(taxaEntregaBairro);

        String taxaEntrega = getResources().getString(R.string.taxa_entrega);
        taxaEntrega = String.format(taxaEntrega, taxaEntregaBairro);
        textViewTaxaEntrega.setText(taxaEntrega);

        double descontoPedido = ((pedido.getValorTotal() + taxaEntregaBairro) * taxaDesconto/100);
        descontoPedido = descontoPedido * 100;
        descontoPedido = Math.round(descontoPedido);//Arredondamento;
        descontoPedido = descontoPedido / 100;
        pedido.setDesconto(descontoPedido);

        pedidoDAO.save(pedido);

        String desconto = getResources().getString(R.string.desconto);
        desconto = String.format(desconto, descontoPedido);
        textViewDesconto.setText(desconto);

        String valorFinal = getResources().getString(R.string.valor_final);
        valorFinal = String.format(valorFinal, pedido.getValorTotal() + taxaEntregaBairro - descontoPedido);
        textViewValorFinal.setText(valorFinal);

    }

    @Override
    public void deletePedidoProduto(PedidoProduto pedidoProduto) {

        PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
        Pedido pedido = pedidoDAO.getPedido();
        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(PedidoActivity.this, pedido);
        pedidoProdutoDAO.delete(pedidoProduto);
        setDadosPedido();
    }

    @Override
    public void delIngrediente(PedidoProduto pedidoProduto) {
        //Consultar ingrediente do produto pela api
        this.pedidoProduto = pedidoProduto;
        if (this.pedidoProduto.getProduto().getTipo() == ProdutoTipo.COMBO) {
            DialogDelIngredienteCombo dialogDelIngredienteCombo = new DialogDelIngredienteCombo(PedidoActivity.this, pedidoProduto);
            dialogDelIngredienteCombo.show();
        } else {
            if (DeviceInformation.isNetwork(PedidoActivity.this)) {
                ProdutosTask produtosTask = new ProdutosTask(PedidoActivity.this, ActionType.READ_BY_ID, PedidoActivity.this);
                produtosTask.execute(pedidoProduto.getProduto());
            } else {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
            }
        }
    }

    @Override
    public void addIngrediente(PedidoProduto pedidoProduto) {
        //Consultar ingrediente do produto pela api
        this.pedidoProduto = pedidoProduto;
        if (this.pedidoProduto.getProduto().getTipo() == ProdutoTipo.COMBO) {

            DialogAddCoberturaCombo dialogAddCoberturaCombo = new DialogAddCoberturaCombo(PedidoActivity.this, pedidoProduto);
            dialogAddCoberturaCombo.show();

        } else {
            if (DeviceInformation.isNetwork(PedidoActivity.this)) {
                IngredientesTask ingredientesTask = new IngredientesTask(PedidoActivity.this, ActionType.READ_INGREDIENTE_TIPO, PedidoActivity.this);
                ingredientesTask.execute(pedidoProduto.getProduto());
            } else {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
            }
        }

    }

    @Override
    public void delObservacoes(PedidoProduto pedidoProduto) {
        PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
        Pedido pedido = pedidoDAO.getPedido();
        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(PedidoActivity.this, pedido);
        pedidoProduto.setObservacoes(null);
        pedidoProduto.setPreco(pedidoProduto.getProduto().getPreco());
        pedidoProdutoDAO.updade(pedidoProduto);
        setDadosPedido();
    }

    @Override
    public void addObservacoes(PedidoProduto pedidoProduto) {
        if (pedidoProduto.getProduto().getTipo() == ProdutoTipo.SUSHI) {
            return;
        }
        DialogAddObservacoes dialogAddObservacoes = new DialogAddObservacoes(PedidoActivity.this, pedidoProduto);
        dialogAddObservacoes.show();

    }

    private class EnviaPedidoOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            PedidoDAO pedidoDAO = new PedidoDAO(PedidoActivity.this);
            Pedido pedido = pedidoDAO.getPedido();

            if (pedido.getPedidoProdutos().size() == 0) {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), "Informe pelo menos um Produto", false).show();
                return;
            }

            if (pedido.getFormaPagamento() == FormaPagamento.AINFORMAR) {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), "Informe a Forma de Pagamento", false).show();
                return;
            }

            if ((pedido.getFormaPagamento() == FormaPagamento.DINHEIRO) && (pedido.getTrocaPara() == 0)) {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), "Informe a Troco Para", false).show();
                return;
            }

            if ((pedido.getFormaPagamento() == FormaPagamento.DINHEIRO) && (pedido.getTrocaPara() < (pedido.getValorTotal() + pedido.getTaxaEntrega() - pedido.getDesconto()))) {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), "Valor do Troco Para Menor que Valor do Pedido", false).show();
                return;
            }

            if (!DeviceInformation.isNetwork(PedidoActivity.this)) {
                new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
                return;
            }

            String enderecoEntrega = pedido.getEnderecoEntrega().getEndereco() + ", " + pedido.getEnderecoEntrega().getNumero();
            if (pedido.getEnderecoEntrega().getComplemento() != null) {
                enderecoEntrega += "\n" + pedido.getEnderecoEntrega().getComplemento();
            }
            enderecoEntrega += "\n" + pedido.getEnderecoEntrega().getBairro().getNome();

            if (pedido.getEnderecoEntrega().getPontoReferencia() != null) {
                enderecoEntrega += "\n" + pedido.getEnderecoEntrega().getPontoReferencia();
            }

            new DialogBox(PedidoActivity.this, DialogBox.DialogBoxType.QUESTION,
                    "Confirmar o envio do pedido para?",
                    enderecoEntrega,
                    new OnClickYesDialog(),
                    new OnClickNoDialog()).show();
        }
    }

    private class OnClickYesDialog implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            /*Envio do Pedido Aqui*/
            Pedido pedido = new PedidoDAO(PedidoActivity.this).getPedido();
            new PedidosTask(PedidoActivity.this, ActionType.CREATE, PedidoActivity.this).execute(pedido);
        }
    }

    private class OnClickNoDialog implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

}
