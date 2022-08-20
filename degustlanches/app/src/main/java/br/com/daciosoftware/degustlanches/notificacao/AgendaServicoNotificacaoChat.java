package br.com.daciosoftware.degustlanches.notificacao;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AgendaServicoNotificacaoChat {

    public AgendaServicoNotificacaoChat() {
    }

    private PendingIntent getIntentService(Context context) {

        Intent intent = new Intent("CHAT_SERVICE");
        int requstCode = 0;
        int flags = 0;
        PendingIntent pendingIntent = PendingIntent.getService(context, requstCode, intent, flags);
        return pendingIntent;
    }

    public void agenda(Context context, int segundos) {

        /*
        Prepara o tempo para o agendamento
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, segundos);
        long tempo = calendar.getTimeInMillis();

        long intervalo = 60 * 1000; //60 segundos

        /*
         Realiza o agendamento
         */
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tempo, intervalo, getIntentService(context));
    }

    public void cancelarAgendamento(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getIntentService(context));
    }

}
