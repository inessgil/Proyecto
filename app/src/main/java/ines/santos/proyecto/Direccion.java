package ines.santos.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Direccion extends AppCompatActivity {
    LoginHelper loginhelper;
    EditText direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);
        direccion = (EditText) findViewById(R.id.direccion);
        setTitle("Change address");
    }

    public void guardar_direccion(View view){
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        String s = settings.getString("nombre", "");
        loginhelper = new LoginHelper(getApplicationContext());
        loginhelper.updateAddress(direccion.getText().toString(), s);
        startActivity(new Intent(this, Perfil.class));
    }
}
