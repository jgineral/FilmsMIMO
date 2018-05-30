package com.example.jagin.infomovie.servicios;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import com.example.jagin.infomovie.R;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener{
    MediaPlayer m_mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*Thread musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                play_music();
            }
        });
        musicThread.start();*/
        GetMusicTask getMusicTask = new GetMusicTask();
        getMusicTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(m_mediaPlayer != null){
            m_mediaPlayer.stop();
            m_mediaPlayer = null;
        }
    }

    private void play_music() {
        if(m_mediaPlayer != null){
            m_mediaPlayer.release();
        }


        m_mediaPlayer = MediaPlayer.create(this,R.raw.song);
        m_mediaPlayer.start();

        m_mediaPlayer.setOnCompletionListener(this);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mp){
    }


    private class GetMusicTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            play_music();
            return null;
        }
    }
}
