package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.SushiRecheioSushi;

public class MenuSushiRecheiosAdapter extends BaseAdapter {

    private Context context;
    private List<SushiRecheioSushi> sushiRecheios;

    public MenuSushiRecheiosAdapter(Context context, List<SushiRecheioSushi> sushiRecheios) {
        this.context = context;
        this.sushiRecheios = sushiRecheios;
    }

    @Override
    public int getCount() {
        return sushiRecheios.size();
    }

    @Override
    public Object getItem(int i) {
        return sushiRecheios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SushiRecheioSushi sushiRecheioSushi = sushiRecheios.get(i);
        MenuSushiRecheiosAdapter.ListViewItemViewHolder viewHolder = null;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_adapter_sushi_recheios, null);
            CheckBox listItemCheckbox = view.findViewById(R.id.cbxRecheio);
            listItemCheckbox.setOnCheckedChangeListener((MenuSushiTiposActivity) context);
            viewHolder = new MenuSushiRecheiosAdapter.ListViewItemViewHolder(view);
            viewHolder.setItemCheckbox(listItemCheckbox);
            viewHolder.getItemCheckbox().setChecked(sushiRecheios.get(i).isChecked());
            viewHolder.getItemCheckbox().setText(sushiRecheioSushi.getSushiRecheio().getNome());
            view.setTag(viewHolder);
        } else {
            viewHolder = (MenuSushiRecheiosAdapter.ListViewItemViewHolder) view.getTag();
            viewHolder.getItemCheckbox().setChecked(sushiRecheios.get(i).isChecked());
            viewHolder.getItemCheckbox().setText(sushiRecheioSushi.getSushiRecheio().getNome());
        }

        return view;

    }

    private class ListViewItemViewHolder extends RecyclerView.ViewHolder {

        private CheckBox itemCheckbox;

        public ListViewItemViewHolder(View itemView) {
            super(itemView);
        }

        public CheckBox getItemCheckbox() {
            return itemCheckbox;
        }

        public void setItemCheckbox(CheckBox itemCheckbox) {
            this.itemCheckbox = itemCheckbox;
        }
    }
}
