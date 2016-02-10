package ines.santos.proyecto;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Player extends Drawer {
    ImageButton play;
    boolean first=true;
    boolean playing=false;

    MyService mService;
    boolean bound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            bound = true;
            setTitle("Media Player");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        play = (ImageButton) findViewById(R.id.b_play);

    }

    public void play(View view) {
        if(playing){
            mService.pause();
            playing=false;
            play.setImageResource(R.drawable.play);
        }
        else{
            if(first){
                first=false;
                mService.iniciar();
            }
            else mService.reiniciar();
            playing=true;
            play.setImageResource(R.drawable.pause);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(Player.this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
