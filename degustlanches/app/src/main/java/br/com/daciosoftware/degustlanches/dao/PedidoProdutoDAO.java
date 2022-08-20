package br.com.daciosoftware.degustlanches.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.db.ContractDatabase;
import br.com.daciosoftware.degustlanches.db.Database;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.Produto;

public class PedidoProdutoDAO {

    private SQLiteDatabase db;
    private Context context;
    private Pedido pedido;

    public PedidoProdutoDAO(Context context, Pedido pedido) {
        this.db = Database.getDatabase(context);
        this.context = context;
        this.pedido = pedido;
    }

    public SQLiteDatabase getDb() {
        return this.db;
    }

    public Long save(PedidoProduto pedidoProduto) throws SQLiteException {
        ContentValues valuesItens = new ContentValues();
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_PEDIDO_ID, pedidoProduto.getPedido().getId());
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_PRODUTO_ID, pedidoProduto.getProduto().getCodigo());
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_QUANTIDADE, pedidoProduto.getQuantidade());
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_PRECO, pedidoProduto.getPreco());
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_OBSERVACOES, pedidoProduto.getObservacoes());
        return this.getDb().insertOrThrow(ContractDatabase.PedidoProduto.NOME_TABELA, "", valuesItens);

    }

    public int updade(PedidoProduto pedidoProduto) throws SQLiteException {
        ContentValues valuesItens = new ContentValues();

        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_OBSERVACOES, pedidoProduto.getObservacoes());
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_QUANTIDADE, pedidoProduto.getQuantidade());
        valuesItens.put(ContractDatabase.PedidoProduto.COLUNA_PRECO, pedidoProduto.getPreco());

        String where = "_id" + "=?";
        String[] whereArgs = new String[]{String.valueOf(pedidoProduto.getId())};
        return this.getDb().update(ContractDatabase.PedidoProduto.NOME_TABELA, valuesItens, where, whereArgs);

    }

    public int delete(PedidoProduto pedidoProduto) throws SQLiteException {
        String where = "_id" + "=?";
        String[] whereArgs = new String[]{String.valueOf(pedidoProduto.getId())};
        return db.delete(ContractDatabase.PedidoProduto.NOME_TABELA, where, whereArgs);
    }

    public int deleteAll() throws SQLiteException {
        String where = "_id" + ">?";
        String[] whereArgs = new String[]{String.valueOf(0)};
        return db.delete(ContractDatabase.PedidoProduto.NOME_TABELA, where, whereArgs);
    }

    public PedidoProduto getPedidoProduto(int id) {

        PedidoProduto pedidoProduto = null;

        try {

            String where = "_id" + "=?";
            String[] whereArgs = new String[]{String.valueOf(String.valueOf(id))};
            Cursor cursor = getCursor(where, whereArgs);
            if (cursor.moveToFirst()) {
                do {
                    pedidoProduto = getEntity(cursor);

                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return pedidoProduto;
    }

    public List<PedidoProduto> getPedidoProdutos() {

        List<PedidoProduto> pedidoProdutos = new ArrayList<>();

        try {

            String where = "pedido_id" + "=?";
            String[] whereArgs = new String[]{String.valueOf(String.valueOf(pedido.getId()))};
            Cursor cursor = getCursor(where, whereArgs);
            if (cursor.moveToFirst()) {
                do {
                    pedidoProdutos.add(getEntity(cursor));

                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return pedidoProdutos;
    }

    private Cursor getCursor(String where, String[] whereArgs) {
        return db.query(ContractDatabase.PedidoProduto.NOME_TABELA,
                ContractDatabase.PedidoProduto.COLUNAS,
                where,
                whereArgs,
                null,
                null,
                null);
    }

    public PedidoProduto getEntity(Cursor c) {

        if (c.getCount() > 0) {
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setId(c.getInt(c.getColumnIndex(ContractDatabase.PedidoProduto._ID)));
            pedidoProduto.setPedido(pedido);
            int produtoId = c.getInt(c.getColumnIndex(ContractDatabase.PedidoProduto.COLUNA_PRODUTO_ID));
            ProdutoDAO produtoDAO = new ProdutoDAO(context);
            Produto produto = produtoDAO.getProduto(produtoId);
            pedidoProduto.setProduto(produto);
            pedidoProduto.setQuantidade(c.getInt(c.getColumnIndex(ContractDatabase.PedidoProduto.COLUNA_QUANTIDADE)));
            pedidoProduto.setPreco(c.getDouble(c.getColumnIndex(ContractDatabase.PedidoProduto.COLUNA_PRECO)));
            pedidoProduto.setObservacoes(c.getString(c.getColumnIndex(ContractDatabase.PedidoProduto.COLUNA_OBSERVACOES)));
            return pedidoProduto;
        } else {
            return null;
        }
    }
}
