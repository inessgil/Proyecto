package ines.santos.proyecto;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;


/**
 * Created by Inés on 01/02/2016.
 */
public class MyService extends Service {
    private final IBinder mBinder = new LocalBinder();
    MediaPlayer mediaPlayer;
    File song;
    private boolean playing;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer= new MediaPlayer();

    }
    public MyService(){

    }

    public void iniciar() {
        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void reiniciar() {
        mediaPlayer.start();
    }

    public boolean isPlaying() {
        return playing;
    }


    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
