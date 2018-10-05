package com.example.ahzam1_feelsbook;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//Emotionpost is my class that I will be using mainly throughout my program. Each object will be an entry by the user which
//will be saved and loaded. 
public class Emotionpost implements Comparable<Emotionpost>{ //implements comparable, so that I can sort it, look at function compareTo
    private Date date;
    private String comment;
    private Feeling feeling; //from my Feeling Class
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

    public void setComment(String comment) { //comment setter
        this.comment = comment;
    }

    public String getComment() { //comment getter
        return comment;
    }

    public String getDateS() { //quick helper function to get my date object in the required format
        DateFormat reformatter = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
        String temp = reformatter.format(this.date);
        return temp;
    }

    public Date getDate() { //date getter
        return date;
    }

    public void setDate(String date) {

        try {
            DateFormat reformatter = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss"); //ISO 8601 format
            this.date = reformatter.parse(date); //rearrange our date format
        } catch (java.text.ParseException e) { //was getting an error until I put this in, never had an exception occur though.
            e.printStackTrace();
        }
    }

    public String getFeeling() { //feeling getter
        return this.feeling.getType();
    }


    public void setFeeling(Feeling feeling) { //feeling setter
        this.feeling = feeling;
    }

    public String toString() { //helper function, to give me a string containing all the data I want to display in my listview
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy'T'HH:mm:ss");
        String date = df.format(this.date);
        String feeling = this.feeling.getType();
        String comment = this.comment;
        return feeling + " | " + date + " | " + comment;
    }

    @Override
    public int compareTo(Emotionpost o) { //needed to be able to sort by date after someone edits
        //taken from https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
		//question was answered by user Domchi on May 8th 2011
        return o.getDate().compareTo(this.date);
    }
}
