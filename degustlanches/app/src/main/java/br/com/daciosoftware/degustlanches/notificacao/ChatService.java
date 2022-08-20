package br.com.daciosoftware.degustlanches.notificacao;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.R;
import br.com.daciosoftware.degustlanches.model.Chat;
import br.com.daciosoftware.degustlanches.util.MyDateUtil;


/**
 * Created by fdacio on 20/01/17.
 */
public class ChatService extends Service implements Runnable {

    private Context context;

    List<Chat> listChat = new ArrayList<>();

    @Override
    public void run() {

        try {

            listChat = new ChatNaoLido(context).get();

        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
            return;
        }

        if (listChat.size() > 0) {

            String mensagemBarraStatus = "Nova Messagem";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                int idNotificacao = R.string.app_name;
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle(mensagemBarraStatus);

                for (Chat chat : listChat) {
                    inboxStyle.addLine(getResources().getString(R.string.app_name));
                    inboxStyle.addLine(chat.getMensagem());
                    inboxStyle.addLine(MyDateUtil.calendarToDateTimeBr(chat.getDataHora()));
                }

                new ChatNotificacao(context).notificar(idNotificacao, inboxStyle);

            } else {
                for (Chat chat : listChat) {
                    String titulo = getResources().getString(R.string.app_name);
                    String subtitulo = chat.getMensagem() + "  " + MyDateUtil.calendarToDateTimeBr(chat.getDataHora());
                    int idNotificacao = 10000 * chat.getId();
                    new ChatNotificacao(context).notificar(idNotificacao, mensagemBarraStatus, titulo, subtitulo);
                }
            }

        } else {

            stopSelf();
        }

    }

    @Override
    public void onCreate() {
        this.context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
