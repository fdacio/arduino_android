package br.com.daciosoftware.degustlanches.notificacao;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import br.com.daciosoftware.degustlanches.ChatActivity;
import br.com.daciosoftware.degustlanches.R;


/**
 * Created by fdacio on 24/01/17.
 */
public class ChatNotificacao {

    private NotificationManager notificationManager;
    private Context context;
    private Class<?> activity;

    /**
     * @param context Context
     */
    public ChatNotificacao(Context context) {
        this.context = context;
        this.activity = ChatActivity.class;
    }

    private NotificationCompat.Builder getNotificacaoPadrao() {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, new Intent(context, activity), 0);
        long[] vibrate = new long[]{100, 250, 100, 500};

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);


        }

        return new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.ic_degust_lanches))
                .setVibrate(vibrate)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }

    private NotificationCompat.Builder getNotificationBuilder(NotificationCompat.InboxStyle inboxStyle) {

        return getNotificacaoPadrao()
                .setTicker(context.getResources().getString(R.string.app_name))
                .setStyle(inboxStyle);
    }

    private NotificationCompat.Builder getNotificationBuilder(String mensagemBarraStatus, String titulo, String subtexto) {

        return getNotificacaoPadrao()
                .setContentTitle(mensagemBarraStatus)
                .setContentText(titulo)
                .setSubText(subtexto);
    }


    /**
     * @param idNotificacao int
     * @param inboxStyle    NotificationCompat.InboxStyle
     */
    public void notificar(int idNotificacao, NotificationCompat.InboxStyle inboxStyle) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(idNotificacao, getNotificationBuilder(inboxStyle).build());
    }

    /**
     * @param idNotificacao       in
     * @param mensagemBarraStatus String
     * @param titulo              String
     * @param subtexto            String
     */
    public void notificar(int idNotificacao, String mensagemBarraStatus, String titulo, String subtexto) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(idNotificacao, getNotificationBuilder(mensagemBarraStatus, titulo, subtexto).build());
    }

}
