package br.com.daciosoftware.degustlanches.notificacao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.daciosoftware.degustlanches.dao.NotificacaoDAO;

public class AgendaServicoBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (new NotificacaoDAO(context).isNotificaChat()) {
            new AgendaServicoNotificacaoChat().agenda(context, 30);
        }

    }

}
