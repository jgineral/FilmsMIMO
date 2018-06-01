package com.example.jagin.infomovie.servicios;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import com.example.jagin.infomovie.R;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener{
    MediaPlayer m_mediaPlayer;
    private static final int NOTIFICATION_ID = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        enableForegroundMode();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread musicThread = new Thread(new Runnable() {
            public void run() {
                play_music();
            }
        });

        musicThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disableForegroundMode();
        if (m_mediaPlayer != null) {
            m_mediaPlayer.stop();
            m_mediaPlayer = null;
        }
    }

    private void play_music() {
        if (m_mediaPlayer != null) {
            m_mediaPlayer.release();
        }
        m_mediaPlayer = MediaPlayer.create(this, R.raw.song);
        m_mediaPlayer.start();

        m_mediaPlayer.setOnCompletionListener(this);
    }

    private void enableForegroundMode() {
        startForeground(NOTIFICATION_ID, getForegroundNotification());
    }

    private void disableForegroundMode() {
        stopForeground(true);
    }

    private Notification getForegroundNotification() {

        Notification.Builder notif = new Notification.Builder(this)
                .setContentTitle("Guerrera")
                .setContentText("C. Tangana & Dellafuente")
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ic_info))
                .setStyle(new Notification.BigTextStyle());
                //.setStyle(new Notification.BigPictureStyle()
                //        .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.song)));

        return notif.build();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        disableForegroundMode();
    }
}

