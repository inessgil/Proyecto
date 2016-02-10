package ines.santos.proyecto;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LogIn extends AppCompatActivity {
    EditText et_name;
    EditText et_psw;
    LoginHelper loginhelper;
    String nombre;
    String psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        et_name = (EditText) findViewById(R.id.et_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        loginhelper = new LoginHelper(getApplicationContext());
        setTitle("Log In");
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        String s = settings.getString("nombre", "");
        if(s!=""){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void Log_in(View v){
        nombre = et_name.getText().toString();
        psw = et_psw.getText().toString();
        if(nombre.isEmpty() || psw.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please introduce name and password", Toast.LENGTH_LONG).show();
        }
        else {
            Cursor c;
            boolean existe=false;
            boolean log=false;
            c = loginhelper.getAllUsers();
            if (c.moveToFirst()) {
                do {
                    if (Objects.equals(c.getString(c.getColumnIndex("name")), nombre)) {
                        if (!Objects.equals(c.getString(c.getColumnIndex("password")), psw)) { //logea con psw diferente
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                            et_psw.setText("");
                        }
                        else{//logea bien
                            Toast.makeText(getApplicationContext(), "Log In succesful", Toast.LENGTH_LONG).show();
                            log=true;
                        }
                        existe= true;
                    }
                } while (c.moveToNext());
            }
            if(!existe){//se registra
                ContentValues values = new ContentValues();
                values.put("name", nombre);
                values.put("password", psw);
                values.put("address", "");
                values.put("image", "");
                loginhelper.create_user(values, "Users");
                Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                log=true;
            }
            if(log){
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("nombre", nombre);
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", nombre);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nombre= savedInstanceState.getString("nombre");
    }
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
