package com.laqa.fastestenglish.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.laqa.fastestenglish.Question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void updatePosition(int packs){
        String strSQL = "UPDATE "+SQLite.TABLE_CONTROLLER+" SET "+SQLite.POSITION_PACKS+" = "+ (getPosition(packs)+1)+" " +
                "WHERE "+ SQLite.PACKS_NUM+" = "+packs;
        sqLiteDatabase.execSQL(strSQL);
    }

    public Question getQuestion(int id,int packs){
        String get = "SELECT * FROM "+SQLite.TABLE_WORDS+" WHERE "+SQLite.NEW_ID_PACKS+" = "+id+"" +
                " AND "+ SQLite.IN_PACKS+ " = "+packs;
        Cursor cursor = sqLiteDatabase.rawQuery(get,null);
        cursor.moveToFirst();
        String eng =cursor.getString(cursor.getColumnIndex(SQLite.WORDS));
        Question questionNew = new Question();
        questionNew.setId(id);
        questionNew.setEnglish(eng);
        return questionNew;
    }

    public int getPosition(int packs){
        String getPosition = "SELECT "+SQLite.POSITION_PACKS+" FROM "+SQLite.TABLE_CONTROLLER+" WHERE "+
                SQLite.PACKS_NUM +" = "+packs;
        Cursor cursor = sqLiteDatabase.rawQuery(getPosition,null);
        cursor.moveToFirst();
        int position =cursor.getInt(cursor.getColumnIndex(SQLite.POSITION_PACKS));
        return position;
    }

    public Question[] getWrongQuestion(int id, int packs){
        int position = getPosition(packs);//getPosition();
        int random=0;
        Question[] wrongAnswers = new Question[3];
        int[] randomNumber = new int[3];

        for(int i=0;i<3;i++) {
            do {
                Random r = new Random();
                random = r.nextInt(position+1 - 1) + 1;
            }while(random==randomNumber[0] || random==randomNumber[1] || random==randomNumber[2] || random == id);
            randomNumber[i] = random;
            String getTrue = "SELECT * FROM " + SQLite.TABLE_WORDS + " WHERE " + SQLite.NEW_ID_PACKS + " = " + random;
            Cursor cursor = sqLiteDatabase.rawQuery(getTrue, null);
            cursor.moveToFirst();
            Question questionNew = new Question();
            questionNew.setId(cursor.getInt(cursor.getColumnIndex(SQLite.NEW_ID_PACKS)));//set ID theo new ID
            questionNew.setEnglish(cursor.getString(cursor.getColumnIndex(SQLite.WORDS)));
            wrongAnswers[i] = questionNew;
        }
        return wrongAnswers;
    }

    public void switchQuestion(List<Question> question, int i, int j){
        Question questionCache = new Question();
        questionCache = question.get(i);
        Question questionCache2 = new Question();
        questionCache2 = question.get(j);
        question.remove(i);
        question.add(i,questionCache2);
        question.remove(j);
        question.add(j,questionCache);
    }

    public List<Question> getAll(int packs){
        List<Question> listQuestion = new ArrayList<Question>();
        String cautruyvan = "SELECT * FROM "+SQLite.TABLE_WORDS +" WHERE "+SQLite.NEW_ID_PACKS+" <= "+getPosition(packs);
        Cursor cursor = sqLiteDatabase.rawQuery(cautruyvan,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //int id = cursor.getInt(0);
            int id = cursor.getInt(cursor.getColumnIndex(SQLite.NEW_ID_PACKS));
            String english = cursor.getString(cursor.getColumnIndex(SQLite.WORDS));

            Question question = new Question();
            question.setId(id);
            question.setEnglish(english);
            listQuestion.add(question);
            cursor.moveToNext();
        }

        //Đánh rối các thứ tự
        for(int i =0 ;i<listQuestion.size();i++){
            Random r = new Random();
            int randomNumber = r.nextInt(listQuestion.size()-0)-0;
            Random r2 = new Random();
            int randomNumber2 = r2.nextInt(listQuestion.size()-0)-0;
            switchQuestion(listQuestion,randomNumber,randomNumber2);
        }

        return listQuestion;
    }

}
