package com.example.pr26;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


public class MainActivity extends AppCompatActivity {

    private TextView mInfoTextView;
    private NotificationBroadcastReceiver mReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle("NotificationListenerService Demo");

        mInfoTextView = (TextView) findViewById(R.id.txt_view);
        mReceiver = new NotificationBroadcastReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void onButtonClicked(View view){
        NotificationChannel channel = new NotificationChannel("My notification", "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);

        if(view.getId() == R.id.button_clear){
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My notification");
            builder.setContentTitle("Кот");
            builder.setContentText("Погладь кота");
            builder.setTicker("Пора проснуться");
            builder.setSmallIcon(R.drawable.cat);
            builder.setAutoCancel(true);
            //manager.notify((int) System.currentTimeMillis(), builder.build());
        }
        else if(view.getId() == R.id.button_clear){
            Intent intent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
            intent.putExtra("command", "clearall");
            sendBroadcast(intent);
        }
        else if(view.getId() == R.id.button_clear){
            Intent intent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
            intent.putExtra("command", "list");
            sendBroadcast(intent);
        }
    }

    class NotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("notification_event") + "\\n" + mInfoTextView.getText();
            mInfoTextView.setText(temp);
        }
    }
}