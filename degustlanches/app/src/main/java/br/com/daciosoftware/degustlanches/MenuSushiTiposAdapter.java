package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.SushiTipoSushi;

public class MenuSushiTiposAdapter extends BaseAdapter {

    private Context context;
    private List<SushiTipoSushi> sushiTipos;

    public MenuSushiTiposAdapter(Context context, List<SushiTipoSushi> sushiTipos) {
        this.context = context;
        this.sushiTipos = sushiTipos;
    }

    @Override
    public int getCount() {
        return sushiTipos.size();
    }

    @Override
    public Object getItem(int i) {
        return sushiTipos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_adapter_sushi_tipos, null);
        }
        TextView tipo = view.findViewById(R.id.tipo);
        NumberPicker numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setMin(0);
        numberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                sushiTipos.get(position).setQuantidade(value);
            }
        });
        SushiTipoSushi sushiTipo = sushiTipos.get(position);
        tipo.setText(sushiTipo.getSushiTipo().getNome());
        numberPicker.setValue(sushiTipo.getQuantidade());
        return view;
    }

}
