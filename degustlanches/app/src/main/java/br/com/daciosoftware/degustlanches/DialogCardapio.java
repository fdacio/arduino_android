package br.com.daciosoftware.degustlanches;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class DialogCardapio implements OnClickItemCardapio {

    private AlertDialog dialog;
    private Activity activity;
    private View view;
    private ListView listView;
    private List menuCardapio;

    public DialogCardapio(Activity activity) {

        this.activity = activity;

        menuCardapio = new ArrayList<ItemCardapio>();

        ItemCardapio itemCardapio1 = new ItemCardapio();
        itemCardapio1.setId(1);
        itemCardapio1.setLabel(this.activity.getResources().getString(R.string.sanduiches));

        ItemCardapio itemCardapio2 = new ItemCardapio();
        itemCardapio2.setId(2);
        itemCardapio2.setLabel(this.activity.getResources().getString(R.string.combos));

        ItemCardapio itemCardapio3 = new ItemCardapio();
        itemCardapio3.setId(3);
        itemCardapio3.setLabel(this.activity.getResources().getString(R.string.pizzas));

        ItemCardapio itemCardapio4 = new ItemCardapio();
        itemCardapio4.setId(4);
        itemCardapio4.setLabel(this.activity.getResources().getString(R.string.pasteis));

        ItemCardapio itemCardapio5 = new ItemCardapio();
        itemCardapio5.setId(5);
        itemCardapio5.setLabel(this.activity.getResources().getString(R.string.bebidas));

        ItemCardapio itemCardapio6 = new ItemCardapio();
        itemCardapio6.setId(6);
        itemCardapio6.setLabel(this.activity.getResources().getString(R.string.sucos_vitaminas));

        ItemCardapio itemCardapio7 = new ItemCardapio();
        itemCardapio7.setId(7);
        itemCardapio7.setLabel(this.activity.getResources().getString(R.string.sushis));

        ItemCardapio itemCardapio8 = new ItemCardapio();
        itemCardapio8.setId(8);
        itemCardapio8.setLabel(this.activity.getResources().getString(R.string.acais));

        ItemCardapio itemCardapio9 = new ItemCardapio();
        itemCardapio9.setId(9);
        itemCardapio9.setLabel(this.activity.getResources().getString(R.string.variedades));

        menuCardapio.add(itemCardapio1);
        menuCardapio.add(itemCardapio2);
        menuCardapio.add(itemCardapio3);
        menuCardapio.add(itemCardapio4);
        menuCardapio.add(itemCardapio7);
        menuCardapio.add(itemCardapio8);
        menuCardapio.add(itemCardapio9);
        menuCardapio.add(itemCardapio5);
        menuCardapio.add(itemCardapio6);


        view = this.activity.getLayoutInflater().inflate(R.layout.dialog_cardapio, null);
        listView = view.findViewById(R.id.listViewCardapio);
        DialogCardapioAdapter adapter = new DialogCardapioAdapter(this.activity, menuCardapio, this);
        listView.setAdapter(adapter);

    }

    public void show() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.MyAlertDialog);
        alertDialog.setCancelable(true);
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();
    }


    @Override
    public void onClickItemMenu(CardapioActivity.ItemCardapio itemCardapio) {

    }

    @Override
    public void onClickItemMenu(ItemCardapio itemCardapio) {

        switch (itemCardapio.getId()) {
            case 1:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuSanduichesActivity.class));
                break;

            case 2:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuCombosActivity.class));
                break;

            case 3:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuPizzasActivity.class));
                break;

            case 4:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuPasteisActivity.class));
                break;

            case 5:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuBebidasActivity.class));
                break;

            case 6:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuSucosVitaminasActivity.class));
                break;

            case 7:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuSushisActivity.class));
                break;

            case 8:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuAcaisActivity.class));
                break;
            case 9:
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuVariedadesActivity.class));
                break;
        }

        activity.finish();

    }

    public class ItemCardapio {

        private int id;
        private String label;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }
}
