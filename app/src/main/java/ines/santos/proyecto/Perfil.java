package ines.santos.proyecto;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Objects;

public class Perfil extends Drawer {
    TextView tv_name;
    TextView et_address;
    TextView tv_intentos;
    LoginHelper loginhelper;
    RankingHelper rankinghelper;
    ImageView image;
    String s;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        loginhelper = new LoginHelper(getApplicationContext());
        rankinghelper = new RankingHelper(getApplicationContext());
        setTitle("Profile");

        tv_name = (TextView) findViewById(R.id.name);
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        s = settings.getString("nombre","");
        tv_name.setText(s);



        et_address = (TextView) findViewById(R.id.direccion);
        Cursor c =  loginhelper.getAddrByName(s);

        if(c.moveToFirst()) {
            do{
                et_address.setText(c.getString(c.getColumnIndex("address")));
            } while (c.moveToNext());
        }
        else et_address.setText("");



        tv_intentos = (TextView) findViewById(R.id.intentos);
        int n=0;
        c = rankinghelper.getScoreByName(s);
        if(c.moveToFirst()){
            n=c.getInt(c.getColumnIndex("score"));
            do {
                if(c.getInt(c.getColumnIndex("score"))<n)n=c.getInt(c.getColumnIndex("score"));
            } while (c.moveToNext());
        }
        tv_intentos.setText("Nº intentos: " + String.valueOf(n));

        image = (ImageView) findViewById(R.id.imageView);
        c = loginhelper.getImageByName(s);
        if(c.moveToFirst()){
            do{
                if(!Objects.equals(c.getString(c.getColumnIndex("image")), "")){
                    String imagen = c.getString(c.getColumnIndex("image"));
                    Uri path = Uri.parse(imagen);
                    Bitmap bm = null;
                    try {
                        bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.setImageBitmap(bm);
                }
                else{
                    image.setImageResource(R.drawable.usuario);
                }
            }while (c.moveToNext());
        }
    }

    public void cambiar_direccion(View view){
        tv_name = (TextView) findViewById(R.id.name);
        et_address = (TextView) findViewById(R.id.direccion);
        startActivity(new Intent(this, Direccion.class));

    }
    public void cambiar_imagen (View view){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Abrir con"), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            if(resultCode == Activity.RESULT_OK){
                Uri path = data.getData();
                image.setImageURI(path);
                loginhelper.updateImage(String.valueOf(path), s);
            }
        }
    }

    public void logout(View v){
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("nombre", "");
        editor.apply();
        startActivity(new Intent(this, LogIn.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
