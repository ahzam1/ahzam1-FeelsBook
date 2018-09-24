package com.example.ahzam1_feelsbook;

import java.util.Date;

public abstract class Feeling {
    private String comment;
    private Date date;
    private String type;

    Feeling(String type){
        this.date = new Date();
        this.comment = "";
        this.type = type;
    }
    Feeling(String type, String comment){
        this.date = new Date();
        this.comment = comment;
        this.type = type;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
