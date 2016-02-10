package ines.santos.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void salto(View view) {
        switch (view.getId()){
            case R.id.b_calc:
                startActivity(new Intent(this, Calc.class));
                break;
            case R.id.b_mem:
                startActivity(new Intent(this, Memory.class));
                break;
            case R.id.b_out:
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("nombre", "");
                editor.apply();
                startActivity(new Intent(this, LogIn.class));
                break;
            case R.id.b_perf:
                startActivity(new Intent(this, Perfil.class));
                break;
            case R.id.b_rank:
                startActivity(new Intent(this, Ranking.class));
                break;
            case R.id.b_play:
                startActivity(new Intent(this, Player.class));
                break;
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
