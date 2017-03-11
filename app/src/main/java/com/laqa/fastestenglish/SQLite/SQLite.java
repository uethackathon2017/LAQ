package com.laqa.fastestenglish.SQLite;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.laqa.fastestenglish.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Phong le on 3/11/2017.
 */

public class SQLite extends SQLiteOpenHelper{
    public static String DB_English1000 = "Database1000";
    public static int DB_VERSION = 1;

    //Chứa đống từ
    public static String TABLE_English1000 = "WordsDatabase";
    public static String ID_English1000 = "id";
    public static String English_English1000 = "english";
    public static String NEW_ID_ENGLISH1000 = "newid";
    public static String PACKS_NUMBER = "packsNumber";


    //Chứa các thông tin cài đặt packs
    public static String TABLE_SETUP = "Setup";
    public static String ID_SETUP = "id";
    public static String POSITION_SETUP = "position";
    public static String PACKS_SETUP ="pack";

    public static String TABLE_CHECK_PACKS = "CheckPacks";
    public static String CURRENT_PACKS = "current";


    Context ctx;

    public SQLite(Context context) {
        super(context, DB_English1000, null, DB_VERSION);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " +TABLE_English1000+ " ( "
                + ID_English1000 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + English_English1000 + " TEXT,"
                + NEW_ID_ENGLISH1000 + " INTEGER,"
                + PACKS_NUMBER + " INTEGER"
                + ");";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " +TABLE_SETUP+ " ( "
                + ID_SETUP +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + POSITION_SETUP + " INTEGER,"
                + PACKS_SETUP +" INTEGER"
                + ");";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " +TABLE_CHECK_PACKS+ " ( "
                + CURRENT_PACKS + " INTEGER"
                + ");";
        db.execSQL(createTable);

        readAndExecuteSQLScript(db, ctx, R.raw.sql);
    }

    private void readAndExecuteSQLScript(SQLiteDatabase db, Context ctx,
                                         Integer sqlScriptResId) {
        Resources res = ctx.getResources();

        try {
            InputStream is = res.openRawResource(sqlScriptResId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            executeSQLScript(db, reader);

            reader.close();
            isr.close();
            is.close();

        } catch (IOException e) {
            throw new RuntimeException("Unable to read SQL script", e);
        }
    }

    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader)
            throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                db.execSQL(statement.toString());
                statement = new StringBuilder();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_English1000);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SETUP);
        onCreate(db);
    }
}
