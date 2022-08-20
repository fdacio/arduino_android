package br.com.daciosoftware.degustlanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import br.com.daciosoftware.degustlanches.db.ContractDatabase;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Bairro;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.model.EnderecoEntrega;
import br.com.daciosoftware.degustlanches.model.FormaPagamento;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.PedidoSituacao;
import br.com.daciosoftware.degustlanches.util.MyDateUtil;

public class PedidoDAO {

    private SQLiteDatabase db;
    private Context context;

    public PedidoDAO(Context context) {
        this.db = Database.getDatabase(context);
        this.context = context;
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public Long save(Pedido pedido) throws SQLiteException {

        ContentValues values = new ContentValues();
        values.put(ContractDatabase.Pedido.COLUNA_CODIGO, pedido.getCodigo());
        values.put(ContractDatabase.Pedido.COLUNA_DATA_HORA, (pedido.getData_hora() != null) ? MyDateUtil.calendarToDateTimeUS(pedido.getData_hora()) : null);
        values.put(ContractDatabase.Pedido.COLUNA_SITUACAO, pedido.getSituacao().ordinal());
        values.put(ContractDatabase.Pedido.COLUNA_FORMA_PAGAMENTO, pedido.getFormaPagamento().ordinal());
        values.put(ContractDatabase.Pedido.COLUNA_TROCO_PARA, pedido.getTrocaPara());
        values.put(ContractDatabase.Pedido.COLUNA_DESCONTO, pedido.getDesconto());
        values.put(ContractDatabase.Pedido.COLUNA_TAXA_ENTREGA, pedido.getTaxaEntrega());
        values.put(ContractDatabase.Pedido.COLUNA_CLIENTES_ID, pedido.getCliente().getCodigo());
        values.put(ContractDatabase.Pedido.COLUNA_ENDERECO_ENTREGA, pedido.getEnderecoEntrega().getEndereco());
        values.put(ContractDatabase.Pedido.COLUNA_NUMERO_ENTREGA, pedido.getEnderecoEntrega().getNumero());
        values.put(ContractDatabase.Pedido.COLUNA_BAIRROS_ID_ENTREGA, pedido.getEnderecoEntrega().getBairro().getId());
        values.put(ContractDatabase.Pedido.COLUNA_COMPLEMENTO_ENTREGA, pedido.getEnderecoEntrega().getComplemento());
        values.put(ContractDatabase.Pedido.COLUNA_PONTO_REFERENCIA_ENTREGA, pedido.getEnderecoEntrega().getPontoReferencia());

        if (pedido.getId() > 0) {
            String where = "_id" + "=?";
            String[] whereArgs = new String[]{String.valueOf(pedido.getId())};
            return (long) this.getDb().update(ContractDatabase.Pedido.NOME_TABELA, values, where, whereArgs);
        } else {
            for (PedidoProduto pedidoProduto : pedido.getPedidoProdutos()) {

                PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(context, pedido);
                pedidoProdutoDAO.save(pedidoProduto);

            }
            return this.getDb().insertOrThrow(ContractDatabase.Pedido.NOME_TABELA, "", values);
        }
    }

    public int delete(Pedido pedido) throws SQLiteException {

        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(context, pedido);
        pedidoProdutoDAO.deleteAll();

        ProdutoDAO produtoDAO = new ProdutoDAO(context);
        produtoDAO.deleteAll();

        BairroEntregaDAO bairroEntregaDAO = new BairroEntregaDAO(context);
        bairroEntregaDAO.deleteAll();

        String where = "_id" + "=?";
        String[] whereArgs = new String[]{String.valueOf(pedido.getId())};
        return db.delete(ContractDatabase.Pedido.NOME_TABELA, where, whereArgs);
    }


    public Pedido getEntity(Cursor c) {

        if (c.getCount() > 0) {
            Pedido pedido = new Pedido();
            pedido.setId(c.getInt(c.getColumnIndex(ContractDatabase.Pedido._ID)));
            pedido.setCodigo(c.getInt(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_CODIGO)));
            try {
                pedido.setData_hora(MyDateUtil.dateTimeUSToCalendar(c.getString(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_DATA_HORA))));
            } catch (Exception e) {
            }

            int situacao = c.getInt(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_SITUACAO));
            pedido.setSituacao(PedidoSituacao.fromOrdinal(situacao));

            int formaPagamento = c.getInt(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_FORMA_PAGAMENTO));
            pedido.setFormaPagamento(FormaPagamento.fromOrdinal(formaPagamento));

            pedido.setTrocaPara(c.getDouble(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_TROCO_PARA)));

            pedido.setDesconto(c.getDouble(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_DESCONTO)));
            pedido.setTaxaEntrega(c.getDouble(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_TAXA_ENTREGA)));

            EnderecoEntrega enderecoEntrega = new EnderecoEntrega();

            enderecoEntrega.setEndereco(c.getString(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_ENDERECO_ENTREGA)));
            enderecoEntrega.setNumero(c.getString(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_NUMERO_ENTREGA)));
            enderecoEntrega.setComplemento(c.getString(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_COMPLEMENTO_ENTREGA)));
            enderecoEntrega.setPontoReferencia(c.getString(c.getColumnIndex(ContractDatabase.Pedido.COLUNA_PONTO_REFERENCIA_ENTREGA)));
            BairroEntregaDAO bairroEntregaDAO = new BairroEntregaDAO(context);
            Bairro bairro = bairroEntregaDAO.getBairro();
            enderecoEntrega.setBairro(bairro);
            pedido.setEnderecoEntrega(enderecoEntrega);

            ClienteDAO clienteDAO = new ClienteDAO(context);
            Cliente cliente = clienteDAO.getCliente();
            pedido.setCliente(cliente);

            PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(context, pedido);
            pedido.setPedidoProdutos(pedidoProdutoDAO.getPedidoProdutos());

            return pedido;

        } else {
            return null;
        }
    }

    public Pedido getPedido() {

        Pedido pedido = null;

        try {

            String where = "_id" + "=?" + " and situacao " + "=?";
            String[] whereArgs = new String[]{String.valueOf(this.getMaxID()), String.valueOf(PedidoSituacao.INICIADO.ordinal())};
            Cursor cursor = getCursor(where, whereArgs);
            if (cursor.moveToFirst()) {
                do {
                    pedido = getEntity(cursor);

                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return pedido;
    }

    private Cursor getCursor(String where, String[] whereArgs) {
        return db.query(ContractDatabase.Pedido.NOME_TABELA,
                ContractDatabase.Pedido.COLUNAS,
                where,
                whereArgs,
                null,
                null,
                null);
    }

    private int getMaxID() {
        int mx = -1;
        try {
            Cursor cursor = db.rawQuery("SELECT max(_ID) from " + ContractDatabase.Pedido.NOME_TABELA, new String[]{});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    mx = cursor.getInt(0);
                }
                return mx;
            }
        } catch (Exception e) {

            return -1;
        }

        return mx;
    }

}
