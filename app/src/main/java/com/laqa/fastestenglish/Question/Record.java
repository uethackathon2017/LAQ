package com.laqa.fastestenglish.Question;

import java.io.Serializable;

/**
 * Created by Phong le on 3/11/2017.
 */

public class Record implements Serializable {
    private String ten;
    private int id;
    private int diem;

    public Record(){

    }

    public Record(String ten, int diem) {
        this.ten = ten;
        this.diem = diem;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
}