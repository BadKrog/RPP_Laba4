package com.example.rpp_laba4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CallDataSend extends BroadcastReceiver {

    final String CHANNEL_ID="MyChannel_one";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        // Создание каналов для уведомления
        createNotificationChannel();

        // Подготовка к переходу в главное активити
        Intent goActInt = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, goActInt, 0);

        Toast.makeText(context, "Уведомление добавлено", Toast.LENGTH_SHORT).show();

        // Создаем строитель для уведомления
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("УРА!")
                .setContentText("День наконец-то настал")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Создаем уведомление
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
