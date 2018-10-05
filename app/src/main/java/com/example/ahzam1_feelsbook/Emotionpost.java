package com.example.ahzam1_feelsbook;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emotionpost implements Comparable<Emotionpost>{ //what i plan to load and save from files within the full app.
    private Date date;
    private String comment;
    private Feeling feeling;
    Emotionpost(String comment, Feeling feeling){ //used when creating posts.
        this.comment = comment; //possible for comment to be '' in the case that user doesn't enter a comment.
        this.feeling = feeling;
        this.date = new Date(); //instantiate date when post created
    }
    Emotionpost(String comment, Feeling feeling, Date date){ //used when loading posts from save files.
        this.comment = comment; //possible for comment to be '' in the case that user doesn't enter a comment.
        this.feeling = feeling;
        this.date = date; //set date to loaded date from file
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String getDateS() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
        String temp = df.format(this.date);
        return temp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {

        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
            this.date = df.parse(date);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getFeeling() {
        return this.feeling.getType();
    }


    public void setFeeling(Feeling feeling) {
        this.feeling = feeling;
    }

    public String toString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
        String date = df.format(this.date);
        String feeling = this.feeling.getType();
        String comment = this.comment;
        return feeling + " | " + date + " | " + comment;
    }

    @Override
    public int compareTo(Emotionpost o) { //needed to be able to sort by date after someone edits
        //taken from https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
        return o.getDate().compareTo(this.date);
    }
}
