package com.laqa.fastestenglish.Question;

/**
 * Created by Phong le on 3/12/2017.
 */

public class Pack {
    private String tenPack;
    private int idPack;
    private int positionPack;
    private int packSetup;

    public Pack(){

    }

    public Pack(int idPack, String tenPack, int positionPack,int packSetup) {
        this.idPack = idPack;
        this.tenPack = tenPack;
        this.positionPack = positionPack;
        this.packSetup=packSetup;
    }

    public int getPositionPack() {
        return positionPack;
    }

    public void setPositionPack(int positionPack) {
        this.positionPack = positionPack;
    }

    public String getTenPack() {
        return tenPack;
    }

    public void setTenPack(String tenPack) {
        this.tenPack = tenPack;
    }

    public int getIdPack() {
        return idPack;
    }

    public void setIdPack(int idPack) {
        this.idPack = idPack;
    }

    public int getPackSetup() {
        return packSetup;
    }

    public void setPackSetup(int packSetup) {
        this.packSetup = packSetup;
    }

}
