package br.com.daciosoftware.degustlanches.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class MaskMoneyEditInput {

    public static TextWatcher mask(final EditText ediTxt) {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    Locale myLocale = new Locale("pt", "BR");
                    //Nesse bloco ele monta a maskara para money
                    ediTxt.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[R$,.]", "");
                    cleanString = getOnlyNumbers(cleanString);
                    Double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance(myLocale).format((parsed / 100));
                    current = formatted;
                    ediTxt.setText(formatted);
                    ediTxt.setSelection(formatted.length());
                    ediTxt.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public static String getOnlyNumbers(String texto) {
        String number = "";
        for (int i = 0; i < texto.length(); i++) {
            String ch = String.valueOf(texto.charAt(i));
            if (Pattern.matches("[0-9]+", ch)) {
                number += ch;
            }
        }
        return number;
    }
}