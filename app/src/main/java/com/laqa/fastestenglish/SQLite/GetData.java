package com.laqa.fastestenglish.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;

import com.laqa.fastestenglish.Question.Question;
import com.laqa.fastestenglish.Question.Record;

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

    public void updatePosition(){
        String strSQL = "UPDATE "+SQLite.TABLE_SETUP+" SET "+SQLite.POSITION_SETUP+" = "+ (getPosition()+1)+" WHERE "+SQLite.PACKS_SETUP+" = "+getCurrentPacks();
        sqLiteDatabase.execSQL(strSQL);
    }

    public Question getQuestion(int id){
        String get = "SELECT * FROM "+SQLite.TABLE_English1000+" WHERE "+SQLite.NEW_ID_ENGLISH1000+" = "+id +" AND "+SQLite.PACKS_NUMBER+ " = "+getCurrentPacks();
        Cursor cursor = sqLiteDatabase.rawQuery(get,null);
        cursor.moveToFirst();
        String eng =cursor.getString(cursor.getColumnIndex(SQLite.English_English1000));
        Question questionNew = new Question();
        questionNew.setId(id);
        questionNew.setEnglish(eng);
        return questionNew;
    }

    public int getPosition(){
        String getPosition = "SELECT * FROM "+SQLite.TABLE_SETUP +" WHERE " +SQLite.PACKS_SETUP+" = "+getCurrentPacks();
        Cursor cursor = sqLiteDatabase.rawQuery(getPosition,null);
        cursor.moveToFirst();
        int position =cursor.getInt(cursor.getColumnIndex(SQLite.POSITION_SETUP));
        return position;
    }

    public Question[] getWrongQuestion(int id){
        int position = getPosition();//getPosition();
        int random=0;
        Question[] wrongAnswers = new Question[3];
        int[] randomNumber = new int[3];

        for(int i=0;i<3;i++) {
            do {
                Random r = new Random();
                random = r.nextInt(position+1 - 1) + 1;
            }while(random==randomNumber[0] || random==randomNumber[1] || random==randomNumber[2] || random == id);
            randomNumber[i] = random;
            String getTrue = "SELECT * FROM " + SQLite.TABLE_English1000 + " WHERE " + SQLite.NEW_ID_ENGLISH1000 + " = " + random+" AND "+ SQLite.PACKS_NUMBER+" = "+getCurrentPacks();
            Cursor cursor = sqLiteDatabase.rawQuery(getTrue, null);
            cursor.moveToFirst();
            Question questionNew = new Question();
            questionNew.setId(cursor.getInt(cursor.getColumnIndex(SQLite.NEW_ID_ENGLISH1000)));//set ID theo new ID
            questionNew.setEnglish(cursor.getString(cursor.getColumnIndex(SQLite.English_English1000)));
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

    public List<Question> getAll(){
        List<Question> listQuestion = new ArrayList<Question>();
        String cautruyvan = "SELECT * FROM "+SQLite.TABLE_English1000 +" WHERE "+SQLite.NEW_ID_ENGLISH1000+" <= "+getPosition() +" AND "+
                SQLite.PACKS_NUMBER +" = "+getCurrentPacks();
        Cursor cursor = sqLiteDatabase.rawQuery(cautruyvan,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //int id = cursor.getInt(0);
            int id = cursor.getInt(cursor.getColumnIndex(SQLite.NEW_ID_ENGLISH1000));
            String english = cursor.getString(cursor.getColumnIndex(SQLite.English_English1000));
            int newId = cursor.getInt(cursor.getColumnIndex(SQLite.NEW_ID_ENGLISH1000));
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

    public boolean themDuLieu(Record rc){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLite.TEN_RECORD, rc.getTen());
        contentValues.put(SQLite.DIEM_RECORD, rc.getDiem());
        contentValues.put(SQLite.PACK_RECORD, getCurrentPacks());
        long kiemTra = sqLiteDatabase.insert(SQLite.TABLE_RECORD,null,contentValues);
        if(kiemTra!=0){
            return true;
        }
        return false;
    }

    public void xoaDuLieu(int id){
        String cautruyvan = "DELETE FROM "+SQLite.TABLE_RECORD+" WHERE "+SQLite.ID_RECORD+"="+id+" AND "+SQLite.PACK_RECORD+" = "+getCurrentPacks();
        sqLiteDatabase.execSQL(cautruyvan);
    }

    public ArrayList<Record> layRecord(){
        ArrayList<Record> duLieu = new ArrayList<Record>();
        String cautruyvan = "SELECT * FROM "+SQLite.TABLE_RECORD+" WHERE "+SQLite.PACK_RECORD+" = "+getCurrentPacks()+" ORDER BY "+SQLite.DIEM_RECORD+" DESC"; // LIMIT 10
        Cursor cursor = sqLiteDatabase.rawQuery(cautruyvan, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex(SQLite.ID_RECORD));
            String ten = cursor.getString(cursor.getColumnIndex(SQLite.TEN_RECORD));
            int diem = cursor.getInt(cursor.getColumnIndex(SQLite.DIEM_RECORD));
            Record rc = new Record();
            rc.setId(id);
            rc.setTen(ten);
            rc.setDiem(diem);
            duLieu.add(rc);
            cursor.moveToNext();
        }
        return duLieu;
    }

    public int countData(){
        int count=0;
        String cautruyvan = "SELECT * FROM "+SQLite.TABLE_English1000 +" WHERE " + SQLite.PACKS_NUMBER +" = "+getCurrentPacks();
        Cursor cursor = sqLiteDatabase.rawQuery(cautruyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            count++;
            cursor.moveToNext();
        }
        return count;
    }

//    public int getMusic(){
//        int music=1;
//        String getPosition = "SELECT "+SQLite.MUSIC_ON+" FROM "+SQLite.TABLE_SOUND;
//        Cursor cursor = sqLiteDatabase.rawQuery(getPosition,null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>=1){
//            music =cursor.getInt(cursor.getColumnIndex(SQLite.MUSIC_ON));
//        }
//        return music;
//    }
//
//
//
//    public int getSound(){
//        int sound=1;
//        String getPosition = "SELECT "+SQLite.SOUND_ON+" FROM "+SQLite.TABLE_SOUND;
//        Cursor cursor = sqLiteDatabase.rawQuery(getPosition,null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>=1){
//            sound =cursor.getInt(cursor.getColumnIndex(SQLite.SOUND_ON));
//        }
//        return sound;
//    }
//
//    public void changeMusic(){
//        if(getMusic()==1){
//            String strSQL = "UPDATE "+SQLite.TABLE_SOUND+" SET "+SQLite.MUSIC_ON+" = 0";
//            sqLiteDatabase.execSQL(strSQL);
//        }
//        else{
//            String strSQL = "UPDATE "+SQLite.TABLE_SOUND+" SET "+SQLite.MUSIC_ON+" = 1";
//            sqLiteDatabase.execSQL(strSQL);
//        }
//    }
//
//    public void changeSound(){
//        if(getSound()==1){
//            String strSQL = "UPDATE "+SQLite.TABLE_SOUND+" SET "+SQLite.SOUND_ON+" = 0";
//            sqLiteDatabase.execSQL(strSQL);
//        }
//        else{
//            String strSQL = "UPDATE "+SQLite.TABLE_SOUND+" SET "+SQLite.SOUND_ON+" = 1";
//            sqLiteDatabase.execSQL(strSQL);
//        }
//    }

}
