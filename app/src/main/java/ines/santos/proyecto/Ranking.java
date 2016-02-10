package ines.santos.proyecto;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Ranking extends ListActivity {

    Drawer dr;
    RankingHelper rankinghelper;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        dr = new Drawer();
        setTitle("Ranking");
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        name = settings.getString("nombre", "");
        rankinghelper = new RankingHelper(getApplicationContext());
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        setListAdapter(adapter);
        Cursor c = rankinghelper.getScoreByName(name);
        if (c.moveToFirst()) {
            do {
                adapter.add(c.getString(c.getColumnIndex("score")));
            } while (c.moveToNext());
        }
    }
    public void reset(View v){
        rankinghelper.deleteScoreByName(name);
        startActivity(new Intent(this, Ranking.class));
    }

}
