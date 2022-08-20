package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.dao.ProdutoDAO;
import br.com.daciosoftware.degustlanches.model.AcaiAcompanhamentoAcai;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.model.Sushi;
import br.com.daciosoftware.degustlanches.model.SushiPreco;
import br.com.daciosoftware.degustlanches.model.SushiRecheio;
import br.com.daciosoftware.degustlanches.model.SushiRecheioSushi;
import br.com.daciosoftware.degustlanches.model.SushiTipoSushi;
import br.com.daciosoftware.degustlanches.util.DialogBox;

public class MenuSushiTiposActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Sushi sushi;
    private List<SushiTipoSushi> tipos;
    private ListView listViewRecheios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sushi_tipo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sushi = (Sushi) getIntent().getSerializableExtra("SUSHI");

        TextView codigo = findViewById(R.id.codigo);
        TextView nome = findViewById(R.id.nome);
        TextView unidade = findViewById(R.id.unidade);
        TextView preco = findViewById(R.id.preco);
        ListView listViewTipos = findViewById(R.id.listViewTipos);
        listViewRecheios =  findViewById(R.id.listViewRecheios);
        codigo.setText(String.valueOf(sushi.getCodigo()));
        nome.setText(sushi.getNome());
        SushiPreco sushiPreco = sushi.getPrecos().get(0);
        unidade.setText(sushiPreco.getUnidadeProduto());
        preco.setText(String.format("R$ %.2f", sushiPreco.getPrecoProduto()));

        tipos = new ArrayList<>();
        for (SushiTipoSushi tipo : sushi.getTipos()) {
            tipos.add(tipo);
            //tipo.setQuantidade(getQuantidadePadrao(sushi));
            tipo.setQuantidade(0);
        }
        //tipos.get(tipos.size() - 1).setQuantidade(getQuantidadeRestante(sushi));
        tipos.get(tipos.size() - 1).setQuantidade(0);

        final MenuSushiTiposAdapter adapterTipos = new MenuSushiTiposAdapter(MenuSushiTiposActivity.this, tipos);
        listViewTipos.setAdapter(adapterTipos);

        final MenuSushiRecheiosAdapter adapterRecheio = new MenuSushiRecheiosAdapter(MenuSushiTiposActivity.this, sushi.getRecheios());
        listViewRecheios.setAdapter(adapterRecheio);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new RegistraSushiPedidoOnClickListener(sushi));

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

    private int getQuantidadePadrao(Sushi sushi) {

        int quantidadePadrao = 2;
        boolean isCombo = (sushi.getQuatidadeCombo() > 0);
        if (isCombo) {
            quantidadePadrao = sushi.getQuatidadeCombo() / sushi.getTipos().size();
            if (quantidadePadrao % 2 > 0) {
                quantidadePadrao += 1;
            }
        }

        return quantidadePadrao;
        //return 0;
    }

    private int getQuantidadeRestante(Sushi sushi) {

        int quantidadePadrao = this.getQuantidadePadrao(sushi);
        int quantidadeRestante = 2;
        boolean isCombo = (sushi.getQuatidadeCombo() > 0);
        if (isCombo) {
            int totalPadrao = quantidadePadrao * sushi.getTipos().size();
            if (totalPadrao > sushi.getQuatidadeCombo()) {
                int passou = totalPadrao - sushi.getQuatidadeCombo();
                quantidadeRestante = quantidadePadrao - passou;
            } else if (totalPadrao < sushi.getQuatidadeCombo()) {
                int faltou = sushi.getQuatidadeCombo() - totalPadrao;
                quantidadeRestante = quantidadePadrao + faltou;
            } else {
                quantidadeRestante = quantidadePadrao;
            }
        }

        return quantidadeRestante;

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int position = listViewRecheios.getPositionForView(compoundButton);
        if (position != ListView.INVALID_POSITION) {
            SushiRecheioSushi sushiRecheioSushi = sushi.getRecheios().get(position);
            sushiRecheioSushi.setChecked(b);
        }
    }

    private class RegistraSushiPedidoOnClickListener implements View.OnClickListener {

        private final Sushi sushi;

        public RegistraSushiPedidoOnClickListener(Sushi sushi) {
            this.sushi = sushi;
        }

        @Override
        public void onClick(View view) {

            //validaçao
            int qtdeTotal = 0;
            for (SushiTipoSushi tipo : tipos) {
                qtdeTotal += tipo.getQuantidade();
            }

            boolean isCombo = (sushi.getQuatidadeCombo() > 0);
            boolean isValidade = true;
            String mensagem = "";

            if (qtdeTotal <= 0) {
                isValidade = false;
                mensagem = String.format("Informe as quantidades.");
            } else if (isCombo) {
                if (qtdeTotal < sushi.getQuatidadeCombo()) {
                    isValidade = false;
                    mensagem = String.format("Quantidade informada menor que a quantidade do combo: %d", sushi.getQuatidadeCombo());
                }
                if (qtdeTotal > sushi.getQuatidadeCombo()) {
                    isValidade = false;
                    mensagem = String.format("Quantidade informada maior que a quantidade do combo: %d", sushi.getQuatidadeCombo());
                }
            }

            if (!isValidade) {
                new DialogBox(MenuSushiTiposActivity.this, DialogBox.DialogBoxType.INFORMATION, MenuSushiTiposActivity.this.getResources().getString(R.string.app_name), mensagem, false).show();
                return;
            }

            //Aqui adicionar o produto ao pedido
            PedidoDAO pedidoDAO = new PedidoDAO(MenuSushiTiposActivity.this);
            Pedido pedido = pedidoDAO.getPedido();
            ProdutoDAO produtoDAO = new ProdutoDAO(MenuSushiTiposActivity.this);

            SushiPreco sushiPreco = (SushiPreco) sushi.getPrecos().get(0);
            Produto produto = new Produto();
            produto.setCodigo(sushiPreco.getProdutoId());
            produto.setTipo(sushi.getTipo());
            String nome = String.format("%s - %s (%s)", sushi.getCodigo(), sushi.getNome(), sushiPreco.getUnidadeProduto());
            produto.setNome(nome);
            produto.setPreco(sushiPreco.getPrecoProduto());
            produto.setIngredientes(sushi.getIngredientes());
            produtoDAO.save(produto);

            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setId(produto.getCodigo());
            pedidoProduto.setPedido(pedido);
            pedidoProduto.setProduto(produto);

            int quantidade = (isCombo) ? 1 : qtdeTotal;

            pedidoProduto.setQuantidade(quantidade);
            pedidoProduto.setPreco(sushiPreco.getPrecoProduto());
            String observacoes = "";
            for (SushiTipoSushi tipo : tipos) {
                observacoes += String.format("%s: %d unidades", tipo.getSushiTipo().getNome(), tipo.getQuantidade()) + "\n";
            }
            observacoes += "\n";

            //Recheio aqui
            observacoes += "Recheios:\n";
            for (SushiRecheioSushi recheioSushi : sushi.getRecheios()) {
                if(recheioSushi.isChecked()) {
                    observacoes += recheioSushi.getSushiRecheio().getNome() + "\n";
                }
            }
            observacoes += "\n";

            pedidoProduto.setObservacoes(observacoes);

            pedido.addProduto(pedidoProduto);

            PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(MenuSushiTiposActivity.this, pedido);
            pedidoProdutoDAO.save(pedidoProduto);
            
            Snackbar.make(MenuSushiTiposActivity.this.findViewById(R.id.contentMenu), nome + " Adicionado ao Pedido ", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            MenuSushiTiposActivity.this.finish();

        }

    }

}