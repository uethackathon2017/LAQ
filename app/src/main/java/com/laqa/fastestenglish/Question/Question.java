package com.laqa.fastestenglish.Question;

/**
 * Created by Phong le on 3/11/2017.
 */

public class Question {
    private int id; //hiển nhiên là id rồi
    private String english;//từ tiếng anh
    private int wrong;//số lần sai


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }
    public String toString(){
        return "english: "+getEnglish()+"\n"
                +"wrong: "+getWrong()+"'n"
                +"newid: "+getId();
    }
}