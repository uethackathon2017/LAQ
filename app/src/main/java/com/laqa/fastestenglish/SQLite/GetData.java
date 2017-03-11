package com.laqa.fastestenglish.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Phong le on 3/11/2017.
 */

public class GetData {
    Context context;
    private SQLite sqLite;
    private SQLiteDatabase sqLiteDatabase;

    public GetData(Context context){
        sqLite = new SQLite(context);
    }

    public void open(){
        sqLiteDatabase = sqLite.getWritableDatabase();
    }

    public void close(){
        sqLite.close();
    }

    public int getCurrentPacks(){
        int num = 1;
        String getCurrentPacks = "SELECT "+SQLite.CURRENT_PACKS+" FROM "+SQLite.TABLE_CHECK_PACKS;
        Cursor cursor = sqLiteDatabase.rawQuery(getCurrentPacks,null);
        cursor.moveToFirst();
        if(cursor.getCount()>=1){
            num = cursor.getInt(cursor.getColumnIndex(SQLite.CURRENT_PACKS));
        }
        return num;
    }

    public void setCurrentPacks(int newCurrentsPack){
        String strSQL = "UPDATE "+SQLite.TABLE_CHECK_PACKS+" SET "+SQLite.CURRENT_PACKS+" = "+ newCurrentsPack;
        sqLiteDatabase.execSQL(strSQL);
    }

}
