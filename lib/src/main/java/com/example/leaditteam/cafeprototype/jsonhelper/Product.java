package com.example.leaditteam.cafeprototype.jsonhelper;

import java.io.Serializable;

public class Product implements Serializable
{
    private int COINS;

    private String DESCRIPTION;

    private String IMG;

    private String INFO;

    private String TITLE;
    

    public void setCOINS(int COINS) {
        this.COINS = COINS;
    }

    public int getCOINS() {

        return COINS;
    }

    public void setDESCRIPTION(String DESCRIPRION){
        this.DESCRIPTION = DESCRIPRION;
    }
    public String getDESCRIPTION(){
        return this.DESCRIPTION;
    }
    public void setIMG(String IMG){
        this.IMG = IMG;
    }
    public String getIMG(){
        return this.IMG;
    }
    public void setINFO(String INFO){
        this.INFO = INFO;
    }
    public String getINFO(){
        return this.INFO;
    }
    public void setTITLE(String TITLE){
        this.TITLE = TITLE;
    }
    public String getTITLE(){
        return this.TITLE;
    }


}
