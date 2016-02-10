package ines.santos.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Inés on 03/02/2016.
 */
public class RankingHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "ranking";
    public static final String TABLE = "Score";


    public RankingHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (name TEXT,  score INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createScore(ContentValues values, String tablename){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tablename, null, values);
    }

    public Cursor getScoreByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"score"};
        String[] where = {name};
        Cursor c = db.query(TABLE, columns, "name=?",where, null, null, null);
        return c;
    }
    public void deleteScoreByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE name = '" + name + "';");
    }

}
