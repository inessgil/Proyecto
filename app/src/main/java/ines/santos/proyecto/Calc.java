package ines.santos.proyecto;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Calc extends Drawer {
    double numero = 0;
    double numero2 = 0;
    String pantalla;
    double res;
    int op = 0;
    boolean nuevo = true;
    int not=0;
    EditText texto;
    View layout;
    DecimalFormat df;
    boolean decimal;
    int ndecimales;
    Double n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        texto = (EditText) findViewById(R.id.texto);
        texto.setText("0");
        layout = findViewById(R.id.layout);
        setTitle("Calculator");
        SharedPreferences settings = getSharedPreferences("PREFS_NOT", 0);
        not=settings.getInt("notificacion", 0);
        df = new DecimalFormat("#.####");
        decimal=false;
        ndecimales=0;
        n= (double) 0;
        pantalla="";
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void operacion(View v) {
        numero = numero2;
        switch (v.getId()){
            case R.id.suma:
                op=1;
                break;
            case R.id.resta:
                op=2;
                break;
            case R.id.multi:
                op=3;
                break;
            case R.id.div:
                op=4;
                break;
        }
        nuevo = true;
        decimal=false;
        pantalla="";
    }

    public void resultado(View v) {
        switch (op) {
            case 0:
                res = numero2;
                break;
            case 1:
                res = numero + numero2;
                break;
            case 2:
                res = numero - numero2;
                break;
            case 3:
                res = numero * numero2;
                break;
            case 4:
                if (numero2 == 0) {
                    crear_notificacion();
                    res=0;
                } else res = numero / numero2;
                break;
        }
        pantalla = String.valueOf(df.format(res));
        texto.setText(pantalla);
//        texto.setText(String.valueOf(df.format(res)));
        numero = 0;
        numero2 = 0;
        op = 0;
        nuevo = true;
        decimal=false;
        pantalla="";
    }

    private void crear_notificacion() {
        switch (not) {
            case 1:
                Toast.makeText(getApplicationContext(), "MATH ERROR", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Snackbar.make(layout, "MATH ERROR", Snackbar.LENGTH_LONG).show();
                break;
            case 3:
                int id=1;
                NotificationManager mNotificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("MATH ERROR")
                        .setContentTitle("MATH ERROR");
                Notification noti = mBuilder.build();
                mNotificationManager.notify(id, noti);
                break;
        }
    }

    public void numero(View v) {
        if (nuevo) {
            nuevo = false;
            numero2 = 0;
        }
        int p=0;
        switch (v.getId()) {
            case R.id.button0:
                n = (double) 0;
                p=0;
                break;
            case R.id.button1:
                n = (double) 1;
                p=1;
                break;
            case R.id.button2:
                n = (double) 2;
                p=2;
                break;
            case R.id.button3:
                n = (double) 3;
                p=3;
                break;
            case R.id.button4:
                n = (double) 4;
                p=4;
                break;
            case R.id.button5:
                n = (double) 5;
                p=5;
                break;
            case R.id.button6:
                n = (double) 6;
                p=6;
                break;
            case R.id.button7:
                n = (double) 7;
                p=7;
                break;
            case R.id.button8:
                n = (double) 8;
                p=8;
                break;
            case R.id.button9:
                n = (double) 9;
                p=9;
                break;
        }
        if(!decimal)numero2 = numero2 * 10 + n;
        else{
            numero2= numero2+ n/ndecimales;
            ndecimales*=10;
        }
        pantalla=pantalla+String.valueOf(p);
        texto.setText(pantalla);
//        texto.setText(String.valueOf(df.format(numero2)));
    }

    public void borrar(View v) {
        String s = String.valueOf(numero2);
        s=s.substring(0, s.length()-1);
        numero2=Double.parseDouble(s);
        ndecimales/=10;
        if(pantalla.length()>1)pantalla =pantalla.substring(0, pantalla.length()-1);
        else pantalla="0";
        texto.setText(pantalla);
    }

    public void eliminar(View view) {
        texto.setText(String.valueOf(df.format(0)));
        nuevo=true;
        pantalla="";
    }

    public void guardar(View view) {
        texto.setText(String.valueOf(df.format(res)));
        numero2 = res;
    }

    public void punto(View view){
        if(!decimal){
            decimal = true;
            ndecimales=10;
            pantalla=pantalla+",";
            texto.setText(pantalla);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("op", String.valueOf(op));
        outState.putDouble("numero", (Double) numero);
        Log.v("op", String.valueOf(op));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        op= Integer.parseInt(savedInstanceState.getString("op"));
        numero = savedInstanceState.getInt("numero");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toast:
                not=1;
                break;
            case R.id.snackbar:
                not=2;
                break;
            case R.id.notificacion:
                not=3;
                break;
            case R.id.call:
                llamar();
                break;
            case R.id.internet:
                navegar();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        SharedPreferences settings = getSharedPreferences("PREFS_NOT", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("notificacion", not);
        editor.apply();
        return false;
    }

    private void navegar() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.es"));
        startActivity(intent);
    }



    private void llamar() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:636573107"));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
