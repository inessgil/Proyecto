package ines.santos.proyecto;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

public class Memory extends Drawer implements OnFragmentInteractionListener{
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        name = settings.getString("nombre","");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
                Memory.this));


        // Give the TabLayout the ViewPager (material)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(Color.DKGRAY, Color.BLACK);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Drawable getImage(int id){
        return getDrawable(id);
    }
    public String getName(){
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        return settings.getString("nombre", "");
    }
    public Context getContext(){
        return getApplicationContext();
    }

    public void reset_memory(){
        startActivity(new Intent(getApplicationContext(),Memory.class));
    }

    public void openDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Congratulations, game ended");
        alertDialogBuilder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset_memory();

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void delay(final View primera, final View segunda, final int n, final boolean b){
        new Thread() {
            @Override
            public void run() {
                try {
                     Thread.sleep(n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(b){
                            CoolImageFlipper coolImageFlipper = new CoolImageFlipper(getContext());
                            Drawable dr = getImage(R.drawable.reverso);
                            coolImageFlipper.flipImage(dr, (ImageView) segunda);
                            coolImageFlipper.flipImage(dr, (ImageView) primera);
                        }
                        else{
                            primera.setVisibility(View.INVISIBLE);
                            segunda.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }.start();
    }



}
