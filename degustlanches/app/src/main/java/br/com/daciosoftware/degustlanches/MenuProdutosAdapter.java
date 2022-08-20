package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.webservice.WebService;

public class MenuProdutosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnClickItemProduto onClickItemProduto;
    private List<ProdutoEntity> produtoEntities;
    private static final int FOOTER_VIEW = 1;

    public MenuProdutosAdapter(Context context, List<ProdutoEntity> produtoEntities, OnClickItemProduto onClickItemProduto) {
        this.context = context;
        this.produtoEntities = produtoEntities;
        this.onClickItemProduto = onClickItemProduto;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == FOOTER_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_footer, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        }

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_menu_produtos, parent, false);
        return new MenuProdutosAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof MenuViewHolder) {

            final MenuViewHolder holder = (MenuViewHolder) viewHolder;
            ProdutoEntity produtoEntity = produtoEntities.get(position);

            holder.codigo.setText(String.valueOf(produtoEntity.getCodigo()));
            holder.nome.setText(produtoEntity.getNome());

            /**Ingredientes do ProdutoMenu*/
            if (produtoEntity.getIngredientes() != null) {
                holder.ingredientes.setVisibility(View.VISIBLE);
                holder.ingredientes.setText(produtoEntity.getIngredientes());
            } else {
                holder.ingredientes.setVisibility(View.GONE);
            }

            holder.foto.setVisibility(View.GONE);
            /**Foto do ProdutoMenu*/
            if (produtoEntity.getFoto() != null) {
                String urlFoto = WebService.getUrlRootHttps() + "/img/" + produtoEntity.getFoto();
                Picasso.get().load(urlFoto).into(holder.foto, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.foto.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.foto.setVisibility(View.GONE);
                    }
                });
            }

            holder.listViewPrecos.setAdapter(new MenuProdutosPrecosNestedAdapter(context, produtoEntity.getPrecos(), produtoEntity, (OnClickItemPreco) context));
        }

        else if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder vh = (FooterViewHolder) viewHolder;
        }
    }

    @Override
    public int getItemCount() {
        if (produtoEntities == null) return 0;
        if (produtoEntities.size() == 0) return 1;
        return produtoEntities.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == produtoEntities.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    protected class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView codigo;
        public TextView nome;
        public TextView ingredientes;
        public ImageView foto;
        public ListView listViewPrecos;

        public MenuViewHolder(final View itemView) {

            super(itemView);

            codigo = itemView.findViewById(R.id.codigo);
            nome = itemView.findViewById(R.id.nome);
            ingredientes = itemView.findViewById(R.id.ingredientes);
            foto = itemView.findViewById(R.id.foto);
            listViewPrecos = itemView.findViewById(R.id.listViewPrecos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemProduto.onClickItemProduto(produtoEntities.get(getLayoutPosition()));
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item
                }
            });
        }
    }
}
