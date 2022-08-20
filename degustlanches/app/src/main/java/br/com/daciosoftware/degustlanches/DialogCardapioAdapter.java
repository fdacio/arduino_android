package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DialogCardapioAdapter extends BaseAdapter {

    private Context context;
    private List<DialogCardapio.ItemCardapio> lista;
    private final OnClickItemCardapio onClickItemCardapio;

    public DialogCardapioAdapter(Context context, List<DialogCardapio.ItemCardapio> lista, OnClickItemCardapio onClickItemCardapio) {
        this.context = context;
        this.lista = lista;
        this.onClickItemCardapio = onClickItemCardapio;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_dialog_adapter_cardapio, null);
        }
        TextView label = view.findViewById(R.id.labelCardapio);
        label.setText( lista.get(i).getLabel());

        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemCardapio.onClickItemMenu(lista.get(i));
            }
        });
        return view;
    }
}
