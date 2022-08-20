package br.com.daciosoftware.degustlanches.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import br.com.daciosoftware.degustlanches.R;


/**
 * Created by Dácio Braga on 22/06/2016.
 */
public class DialogBox {

    private static final String TEXTO_BUTTON_OK = "OK";
    private static final String TEXTO_BUTTON_YES = "SIM";
    private static final String TEXTO_BUTTON_NO = "NÃO";
    private static final boolean CANCELABLE = false;
    private AlertDialog.Builder dialogBox;
    private DialogInterface.OnClickListener onClickListenerOK;
    private DialogInterface.OnClickListener onClickListenerYES;
    private DialogInterface.OnClickListener onClickListenerNO;
    private Context context;
    private boolean finishActivity = false;

    /**
     * @param context       - Contexto da App
     * @param dialogBoxType INFORMATION ou QUESTION
     * @param title         Titulo do diálogo
     * @param message       Mensagem do fiálogo
     */
    public DialogBox(Context context, DialogBoxType dialogBoxType, String title, String message) {
        this.context = context;

        dialogBox = new AlertDialog.Builder(context, R.style.MyAlertDialog);
        dialogBox.setTitle(title);
        dialogBox.setMessage(message);
        dialogBox.setCancelable(CANCELABLE);

        switch (dialogBoxType) {
            case INFORMATION:
                dialogBox.setIcon(R.drawable.ic_information);
                setOnClickListenerOK();
                dialogBox.setNeutralButton(TEXTO_BUTTON_OK, onClickListenerOK);
                break;
            case QUESTION:
                dialogBox.setIcon(R.drawable.ic_help);
                break;
        }
    }

    /**
     * @param context
     * @param dialogBoxType
     * @param title
     * @param message
     * @param onClickListenerYES
     * @param onClickListenerNO
     */
    public DialogBox(Context context, DialogBoxType dialogBoxType, String title, String message, DialogInterface.OnClickListener onClickListenerYES, DialogInterface.OnClickListener onClickListenerNO) {
        this(context, dialogBoxType, title, message);
        dialogBox.setPositiveButton(TEXTO_BUTTON_YES, onClickListenerYES);
        dialogBox.setNegativeButton(TEXTO_BUTTON_NO, onClickListenerNO);

    }

    public DialogBox(Context context, DialogBoxType dialogBoxType, String title, String message, DialogInterface.OnClickListener onClickListenerNEUTRAL) {
        this(context, dialogBoxType, title, message);
        dialogBox.setNeutralButton(TEXTO_BUTTON_OK, onClickListenerNEUTRAL);
    }


    /**
     * @param context
     * @param dialogBoxType
     * @param title
     * @param message
     * @param finishActivity
     */
    public DialogBox(Context context, DialogBoxType dialogBoxType, String title, String message, boolean finishActivity) {
        this(context, dialogBoxType, title, message);
        this.finishActivity = finishActivity;
    }

    private void setOnClickListenerOK() {
        this.onClickListenerOK = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (finishActivity) {
                    ((Activity) context).finish();
                }
            }
        };
    }

    public void setOnClickListenerYES(DialogInterface.OnClickListener onClickListenerYES) {
        this.onClickListenerYES = onClickListenerYES;
    }

    public void setOnClickListenerNO(DialogInterface.OnClickListener onClickListenerNO) {
        this.onClickListenerNO = onClickListenerNO;
    }

    public void show() {
        dialogBox.create();
        dialogBox.show();
    }

    public enum DialogBoxType {INFORMATION, QUESTION}


}
