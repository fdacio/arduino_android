package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.AcaiAcompanhamentoAcai;

public class MenuAcaiAcompanhamentoAdapter extends BaseAdapter {

    private Context context;
    private List<AcaiAcompanhamentoAcai> acompanhamentos;

    public MenuAcaiAcompanhamentoAdapter(Context context, List<AcaiAcompanhamentoAcai> acompanhamentos) {
        this.context = context;
        this.acompanhamentos = acompanhamentos;
    }

    @Override
    public int getCount() {
        return acompanhamentos.size();
    }

    @Override
    public AcaiAcompanhamentoAcai getItem(int i) {
        return acompanhamentos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AcaiAcompanhamentoAcai acaiAcompanhamento = acompanhamentos.get(i);
        ListViewItemViewHolder viewHolder = null;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_adapter_acais_acompanhamentos, null);
            CheckBox listItemCheckbox = view.findViewById(R.id.cbxAcompanhamento);
            listItemCheckbox.setOnCheckedChangeListener((MenuAcaiAcompanhamentosActivity)context);
            viewHolder = new ListViewItemViewHolder(view);
            viewHolder.setItemCheckbox(listItemCheckbox);
            viewHolder.getItemCheckbox().setChecked(acompanhamentos.get(i).isChecked());
            viewHolder.getItemCheckbox().setText(acaiAcompanhamento.getAcaiAcompanhamento().getNome());
            view.setTag(viewHolder);
        } else {
            viewHolder = (ListViewItemViewHolder) view.getTag();
            viewHolder.getItemCheckbox().setChecked(acompanhamentos.get(i).isChecked());
            viewHolder.getItemCheckbox().setText(acaiAcompanhamento.getAcaiAcompanhamento().getNome());
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
