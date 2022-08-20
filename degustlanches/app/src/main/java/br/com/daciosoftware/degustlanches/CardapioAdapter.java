package br.com.daciosoftware.degustlanches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.CardapioViewHolder> {

    private OnClickItemCardapio clickRecyclerViewInterface;
    private List<CardapioActivity.ItemCardapio> listItemCardapios;

    public CardapioAdapter(List<CardapioActivity.ItemCardapio> listItemCardapios, OnClickItemCardapio clickRecyclerViewInterface) {
        this.listItemCardapios = listItemCardapios;
        this.clickRecyclerViewInterface = clickRecyclerViewInterface;
    }

    @NonNull
    @Override
    public CardapioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_adapter_cardapio, parent, false);

        return new CardapioAdapter.CardapioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardapioViewHolder holder, int position) {
        CardapioActivity.ItemCardapio itemCardapio = listItemCardapios.get(position);
        holder.label.setText(itemCardapio.getLabel());
        holder.icon.setImageDrawable(itemCardapio.getIcon());
    }

    @Override
    public int getItemCount() {
        return listItemCardapios.size();
    }

    protected class CardapioViewHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public ImageView icon;

        public CardapioViewHolder(final View itemView) {

            super(itemView);

            label = (TextView) itemView.findViewById(R.id.labelCardapio);
            icon = (ImageView) itemView.findViewById(R.id.iconCardapio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickRecyclerViewInterface.onClickItemMenu(listItemCardapios.get(getLayoutPosition()));
                }
            });
        }
    }

}
