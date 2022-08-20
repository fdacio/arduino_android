package br.com.daciosoftware.degustlanches.util;


import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import br.com.daciosoftware.degustlanches.R;

public class ProgressDialog {

    public AlertDialog dialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_dialog);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
