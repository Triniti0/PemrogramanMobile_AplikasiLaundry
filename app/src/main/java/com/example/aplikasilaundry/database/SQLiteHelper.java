package com.example.aplikasilaundry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aplikasilaundry.model.ModelPelanggan;

import java.util.ArrayList;
import java.util.List;

import androidx.core.database.sqlite.SQLiteDatabaseKt;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "my_laundry.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PELANGGAN = "pelanggan";
    public static final String KEY_PELANGGAN_ID = "pelanggan_id";
    public static final String KEY_PELANGGAN_NAMA = "nama";
    public static final String KEY_PELANGGAN_EMAIL = "email";
    public static final String KEY_PELANGGAN_HP = "hp";
    private static final String CREATE_TABLE_PELANGGAN = "CREATE TABLE "+   TABLE_PELANGGAN + "(" + KEY_PELANGGAN_ID + " TEXT," + KEY_PELANGGAN_NAMA + " TEXT," + KEY_PELANGGAN_EMAIL + " TEXT," + KEY_PELANGGAN_HP + " TEXT )";


    public SQLiteHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PELANGGAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELANGGAN);
        onCreate(db);
    }

    public boolean insertPelanggan(ModelPelanggan mp){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PELANGGAN_ID, mp.getId());
        contentValues.put(KEY_PELANGGAN_NAMA, mp.getNama());
        contentValues.put(KEY_PELANGGAN_EMAIL, mp.getEmail());
        contentValues.put(KEY_PELANGGAN_HP, mp.getHp());
        long id = database.insert(TABLE_PELANGGAN, null, contentValues);
        database.close();
        if (id != -1){
            return true;
        }else{
            return false;
        }
    }
    public List<ModelPelanggan> getPelanggan(){
        List<ModelPelanggan> pel = new ArrayList<ModelPelanggan>();
        String query = "SELECT * FROM " + TABLE_PELANGGAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                ModelPelanggan k = new ModelPelanggan();
                k.setId(cursor.getString(0));
                k.setNama(cursor.getString(1));
                k.setEmail(cursor.getString(2));
                k.setHp(cursor.getString(3));
                pel.add(k);
            }while (cursor.moveToNext());
        }
        return pel;
    }
}
