package ines.santos.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Inés on 03/02/2016.
 */
public class LoginHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "users";
    public static final String TABLE = "Users";

    public LoginHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (name TEXT PRIMARY KEY UNIQUE, password TEXT, address TEXT, image TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void create_user(ContentValues values, String tablename){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tablename, null, values);
    }

    public Cursor getAddrByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"address"};
        String[] where = {name};
        return db.query(TABLE, columns, "name=?",where, null, null, null);
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns ={"name", "password"};
        return db.query(TABLE, columns, null, null, null, null, null);
    }

    public void updateAddress(String address, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("address", address);
        db.execSQL("UPDATE " + TABLE + " SET address = '" + address + "' WHERE name = '" + name + "';");
    }

    public Cursor getImageByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"image"};
        String[] where = {name};
        Cursor c = db.query(TABLE, columns, "name=?",where, null, null, null);
        return c;
    }

    public void updateImage(String path, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("image", path);
//        db.update(TABLE, cv, "name=?", new String[] {});
        db.execSQL("UPDATE " + TABLE + " SET image = '" + path + "' WHERE name = '" + name + "';");
    }
}
