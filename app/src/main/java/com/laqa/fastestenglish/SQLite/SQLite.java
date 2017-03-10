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
    public static String DB_ENGLISH = "databaseenglish";
    public static int DB_VERSION = 1;

    public static String TABLE_WORDS = "tablewords"; //Bảng từ
    public static String ID_WORDS = "id_words"; //STT trong bảng
    public static String WORDS = "words"; // từ
    public static String NEW_ID_PACKS = "new_id_packs";// STT của từ trong gói
    public static String IN_PACKS = "packs";// từ nằm trong gói nào

    public static String TABLE_PACKS = "tablepacks"; //Bảng gói
    public static String ID_PACKS = "id_packs"; //STT gói
    public static String PACKS_NAME = "packs_name"; // tên gói

    public static String TABLE_SCORES = "tablescores"; //Bảng điểm
    public static String ID_SCORES = "id_scores"; //STT điểm
    public static String NAME_SCORES = "name_scores"; // tên người chơi
    public static String SCORE_IN_PACKS = "score_inpacks"; // điểm này trong packs nào
    public static String SCORES = "scores"; // số điểm đạt được

    public static String TABLE_CONTROLLER = "table_controller"; // Bảng điều khiển
    public static String ID_CONTROLLER = "id_controller"; // id điều khiển
    public static String POSITION_PACKS = "position_packs"; // Người chơi đến từ nào trong packs
    public static String PACKS_NUM = "packs_num"; // Packs nào

    public static String TABLE_CHECK_PACKS = "table_check_packs";
    public static String CURRENT_PACKS="current_packs";

    Context ctx;

    public SQLite(Context context) {
        super(context, DB_ENGLISH, null, DB_VERSION);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " +TABLE_WORDS+ " ( "
                + ID_WORDS +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORDS + " TEXT,"
                + NEW_ID_PACKS + " INTEGER,"
                + IN_PACKS + " INTEGER"
                + ");";
        sqLiteDatabase.execSQL(createTable);

        createTable = "CREATE TABLE " +TABLE_PACKS+ " ( "
                + ID_PACKS +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PACKS_NAME + " TEXT"
                + ");";
        sqLiteDatabase.execSQL(createTable);

        createTable = "CREATE TABLE " +TABLE_SCORES+ " ( "
                + ID_SCORES +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_SCORES + " TEXT,"
                + SCORE_IN_PACKS + " INTEGER,"
                + SCORES + " INTEGER"
                + ");";
        sqLiteDatabase.execSQL(createTable);

        createTable = "CREATE TABLE " +TABLE_CONTROLLER+ " ( "
                + ID_CONTROLLER +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PACKS_NUM + " INTEGER,"
                + POSITION_PACKS + " INTEGER"
                + ");";
        sqLiteDatabase.execSQL(createTable);

        createTable = "CREATE TABLE " +TABLE_CHECK_PACKS+ " ( "
                + CURRENT_PACKS + " INTEGER"
                + ");";
        sqLiteDatabase.execSQL(createTable);
        readAndExecuteSQLScript(sqLiteDatabase, ctx, R.raw.sql);
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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_WORDS);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PACKS);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_SCORES);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTROLLER);
        onCreate(sqLiteDatabase);
    }
}
