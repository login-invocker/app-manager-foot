package com.example.quanlymonan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import androidx.annotation.Nullable;

import com.example.quanlymonan.Model.Foods;

import java.util.ArrayList;


public class MonAnDataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DB_FOODS";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "Foods";
    public static final String COL_FOOD_ID = "id";
    public static final String COL_NAME_FOOD = "name_food";
    public static final String COL_PRICE_FOOD = "price_food";
    public static final String COL_TYPE_FOOD = "type_food";

    public MonAnDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + DB_TABLE + "("+COL_FOOD_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME_FOOD + " text NOT NULL,"
                + COL_PRICE_FOOD + " INTEGER,"
                + COL_TYPE_FOOD + " text NOT NULL"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP table if EXISTS " + DB_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }
    // phuong thuc them 1 ghi chu moi
    public Long addFood(Foods food){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_NAME_FOOD, food.getNameFood());
        contentValues.put(COL_PRICE_FOOD, food.getPriceFood());
        contentValues.put(COL_TYPE_FOOD, food.getTypeFood());

        Long res =  db.insert(DB_TABLE,null, contentValues);

        db.close();
        return res;
    }

    public int updateNote(Foods food){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_NAME_FOOD, food.getNameFood());
        contentValues.put(COL_PRICE_FOOD, food.getPriceFood());
        contentValues.put(COL_TYPE_FOOD, food.getTypeFood());

        int res =  db.update(DB_TABLE,contentValues,COL_FOOD_ID+ " = ? ", new String[]{
                String.valueOf(food.getId())
        });
        db.close();
        return res;
    }

    public ArrayList<Foods> getAllNote(){
        String sql = "select * from " + DB_TABLE;
        ArrayList<Foods> foods = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cu = db.rawQuery(sql,null);
        while (cu.moveToNext()){
            Foods food = new Foods(cu.getInt(0),cu.getString(1), cu.getInt(2), cu.getString(3));
            foods.add(food);
        }
        db.close();
        return  foods;
    }

    public void deleteNote(Foods food){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,COL_FOOD_ID+ " = ?", new String[] {
                String.valueOf(food.getId())
        });
        db.close();
    }
}
