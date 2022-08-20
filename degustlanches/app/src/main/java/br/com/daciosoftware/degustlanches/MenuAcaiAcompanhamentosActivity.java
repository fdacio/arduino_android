package br.com.daciosoftware.degustlanches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.dao.ProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Acai;
import br.com.daciosoftware.degustlanches.model.AcaiAcompanhamento;
import br.com.daciosoftware.degustlanches.model.AcaiAcompanhamentoAcai;
import br.com.daciosoftware.degustlanches.model.AcaiAcompanhamentoPoteSeparado;
import br.com.daciosoftware.degustlanches.model.AcaiPreco;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.util.MaskMoneyEditInput;

public class MenuAcaiAcompanhamentosActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Acai acai;
    private AcaiPreco acaiPreco;
    private AcaiAcompanhamentoPoteSeparado poteSeparado;
    private ListView listViewAcompanhamentos;
    private EditText edtValorCompra;
    private CheckBox checkBoxPoteSeparado;
    private double precoPote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_acai_acompanhamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        acai = (Acai) getIntent().getSerializableExtra("ACAI");

        TextView codigo = findViewById(R.id.codigo);
        TextView nome = findViewById(R.id.nome);
        TextView unidade =findViewById(R.id.unidade);
        TextView preco = findViewById(R.id.preco);
        edtValorCompra = findViewById(R.id.edtValorCompra);
        edtValorCompra.addTextChangedListener(MaskMoneyEditInput.mask(edtValorCompra));
        edtValorCompra.requestFocus();
        final TextView textViewCalculoCompra = findViewById(R.id.textViewCalculoCompra);
        Button btnCalcular = findViewById(R.id.btnCalcular);

        codigo.setText(String.valueOf(acai.getCodigo()));
        nome.setText(acai.getNome());
        acaiPreco = acai.getPrecos().get(0);
        unidade.setText(acaiPreco.getUnidadeProduto());
        preco.setText(String.format("R$ %.2f", acaiPreco.getPrecoProduto()));
        textViewCalculoCompra.setText(String.format("%d gramas", 0));

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cleanString = edtValorCompra.getText().toString().replaceAll("[R$,.]", "");
                cleanString = MaskMoneyEditInput.getOnlyNumbers(cleanString);
                if (cleanString.isEmpty()) { return; }
                Double parsed = Double.parseDouble(cleanString);
                Double valorCompra = parsed / 100;
                Double preco = acaiPreco.getPrecoProduto();
                Double gramas = valorCompra * 1000 / preco;
                textViewCalculoCompra.setText(String.format("%.0f gramas", gramas));
            }
        });

        poteSeparado = acai.getPoteSeparado();
        checkBoxPoteSeparado = findViewById(R.id.checkBoxPoteSeparado);
        checkBoxPoteSeparado.setText(String.format("%s R$%.2f", poteSeparado.getDescricao(), poteSeparado.getPreco()));
        precoPote = poteSeparado.getPreco();

        listViewAcompanhamentos = findViewById(R.id.listViewAcompanhamentos);
        MenuAcaiAcompanhamentoAdapter acaiAcompanhamentoAdapter = new MenuAcaiAcompanhamentoAdapter(this, acai.getAcompanhamentos());
        acaiAcompanhamentoAdapter.notifyDataSetChanged();
        listViewAcompanhamentos.setAdapter(acaiAcompanhamentoAdapter);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new OnClickRegistrarListener());
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int position = listViewAcompanhamentos.getPositionForView(compoundButton);
        if (position != ListView.INVALID_POSITION) {
            AcaiAcompanhamentoAcai acaiAcompanhamentoAcai = acai.getAcompanhamentos().get(position);
            acaiAcompanhamentoAcai.setChecked(b);
        }
    }

    private class OnClickRegistrarListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            //validaçao
            boolean isValidade = true;
            String mensagem = "";
            String cleanString = edtValorCompra.getText().toString().replaceAll("[R$,.]", "");
            cleanString = MaskMoneyEditInput.getOnlyNumbers(cleanString);
            if (cleanString.isEmpty()) {
                mensagem = "Informe o valor da compra";
                isValidade = false;
            }
            Double parsed = Double.parseDouble(cleanString);
            if(parsed == 0) {
                mensagem = "Informe o valor da compra";
                isValidade = false;
            }
            if (!isValidade) {
                new DialogBox(MenuAcaiAcompanhamentosActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), mensagem, false).show();
                return;
            }

            Double valorCompra = parsed / 100;
            Double preco = acaiPreco.getPrecoProduto();
            Double gramas = valorCompra * 1000 / preco;

            //Aqui adicionar o produto ao pedido
            PedidoDAO pedidoDAO = new PedidoDAO(MenuAcaiAcompanhamentosActivity.this);
            Pedido pedido = pedidoDAO.getPedido();
            ProdutoDAO produtoDAO = new ProdutoDAO(MenuAcaiAcompanhamentosActivity.this);

            Produto produto = new Produto();
            produto.setCodigo(acaiPreco.getProdutoId());
            produto.setTipo(acai.getTipo());
            String nome = String.format("%s - %s (%s)", acai.getCodigo(), acai.getNome(), acaiPreco.getUnidadeProduto());
            produto.setNome(nome);
            produto.setPreco(acaiPreco.getPrecoProduto());
            produto.setIngredientes("");
            produtoDAO.save(produto);

            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setId(produto.getCodigo());
            pedidoProduto.setPedido(pedido);
            pedidoProduto.setProduto(produto);
            pedidoProduto.setQuantidade(1);

            String observacoes = String.format("Peso: %.0f gramas\n\n", gramas);
            if (checkBoxPoteSeparado.isChecked()) {
                valorCompra += precoPote;
                observacoes += String.format("%s R$%.2f\n\n", poteSeparado.getDescricao(), precoPote);
            }
            observacoes += "Acompanhamentos:\n";
            for (AcaiAcompanhamentoAcai acompanhamento : acai.getAcompanhamentos()) {
                if(acompanhamento.isChecked()) {
                    observacoes += acompanhamento.getAcaiAcompanhamento().getNome() + "\n";
                }
            }
            pedidoProduto.setObservacoes(observacoes);
            pedidoProduto.setPreco(valorCompra);
            pedido.addProduto(pedidoProduto);

            PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(MenuAcaiAcompanhamentosActivity.this, pedido);
            pedidoProdutoDAO.save(pedidoProduto);

            Toast.makeText(MenuAcaiAcompanhamentosActivity.this, nome + " Adicioanado ao Pedido", Toast.LENGTH_LONG).show();

            finish();

        }
    }

}