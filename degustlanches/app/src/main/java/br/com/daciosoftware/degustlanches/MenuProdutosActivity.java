package br.com.daciosoftware.degustlanches;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.dao.ProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.ProdutoPrecoEntity;
import br.com.daciosoftware.degustlanches.model.ProdutoTipo;

public class MenuProdutosActivity extends AppCompatActivity implements OnClickItemProduto, OnClickItemPreco {

    protected RecyclerView recyclerViewMenu;
    private MenuProdutosPrecosAdapter menuProdutosPrecosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_produtos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabPedido);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PedidoActivity.class));
                finish();
            }
        });

        FloatingActionButton fabCardapio = findViewById(R.id.fabCardapio);
        fabCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCardapio dialogCardapio = new DialogCardapio(MenuProdutosActivity.this);
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
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItemProduto(ProdutoEntity produtoEntity) {

        View view = getLayoutInflater().inflate(R.layout.alert_menu_produtos_precos, null);

        TextView codigo = view.findViewById(R.id.codigo);
        TextView nome = view.findViewById(R.id.nome);
        codigo.setText(String.valueOf(produtoEntity.getCodigo()));
        nome.setText(produtoEntity.getNome());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyAlertDialog);
        alertDialog.setTitle(produtoEntity.getTipo().getDescricao());
        alertDialog.setCancelable(false);
        alertDialog.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setView(view);
        Dialog dialog = alertDialog.create();
        dialog.show();

        ListView precosList = view.findViewById(R.id.precos);
        precosList.setOnItemClickListener(new OnProdutoPrecoClick(produtoEntity, dialog));

        menuProdutosPrecosAdapter = new MenuProdutosPrecosAdapter(this, produtoEntity.getPrecos(), this);
        precosList.setAdapter(menuProdutosPrecosAdapter);


    }

    //Click em um item da lista de precos
    @Override
    public void onClickItemPreco(ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity) {
        addProdutoPedido(produtoEntity, produtoPrecoEntity);
    }

    // Click em um item do dialogo de preços
    private class OnProdutoPrecoClick implements AdapterView.OnItemClickListener {

        private ProdutoEntity produtoEntity;
        private Dialog dialog;

        public OnProdutoPrecoClick(ProdutoEntity produtoEntity, Dialog dialog) {
            this.produtoEntity = produtoEntity;
            this.dialog = dialog;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            ProdutoPrecoEntity produtoPrecoEntity = menuProdutosPrecosAdapter.getItem(position);
            dialog.dismiss();
            addProdutoPedido(produtoEntity, produtoPrecoEntity);
        }
    }

    private void addProdutoPedido(ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity) {

        PedidoDAO pedidoDAO = new PedidoDAO(MenuProdutosActivity.this);
        Pedido pedido = pedidoDAO.getPedido();

        ProdutoDAO produtoDAO = new ProdutoDAO(MenuProdutosActivity.this);
        Produto produto = new Produto();
        produto.setCodigo(produtoPrecoEntity.getProdutoId());
        produto.setTipo(produtoEntity.getTipo());
        String nome = String.format("%s - %s (%s)", produtoEntity.getCodigo(), produtoEntity.getNome(), produtoPrecoEntity.getUnidadeProduto());
        produto.setNome(nome);
        produto.setPreco(produtoPrecoEntity.getPrecoProduto());
        produto.setIngredientes(produtoEntity.getIngredientes());
        produtoDAO.save(produto);

        PedidoProduto pedidoProduto = new PedidoProduto();
        pedidoProduto.setId(produto.getCodigo());
        pedidoProduto.setPedido(pedido);
        pedidoProduto.setProduto(produto);
        pedidoProduto.setQuantidade(1);
        pedidoProduto.setPreco(produtoPrecoEntity.getPrecoProduto());
        if (pedidoProduto.getProduto().getTipo() == ProdutoTipo.COMBO) {
            pedidoProduto.setObservacoes("Cobertura Batata: NENHUMA");
        }
        pedido.addProduto(pedidoProduto);

        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(MenuProdutosActivity.this, pedido);
        pedidoProdutoDAO.save(pedidoProduto);

        Snackbar.make(findViewById(R.id.contentMenu), nome + " Adicionado ao Pedido ", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }

}
