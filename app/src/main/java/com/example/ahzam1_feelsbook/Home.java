package com.example.ahzam1_feelsbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SharedMemory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private static final String FILENAME = "feelings.sav"; //define my filename early on as static so can be used anywhere
    static ArrayList<Emotionpost> posts = new ArrayList<Emotionpost>(); //my array for posts. posts will be mainly stored in here.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFeelings(); //load the saved feelings from the file.
    }
    public void viewHistory(View view){ //when view history pressed, create a new activity for history
        Intent intent = new Intent(this, ViewHistory.class);
        startActivity(intent);
    }

    public void viewTotals(View view){ //when view totals pressed, create a new activity for totals
        Intent intent = new Intent(this, ViewTotals.class);
        startActivity(intent);
    }
    public Emotionpost newpost(String feeling, String comment){
        //create an emotion post for me
        Feeling temp = new Feeling(feeling); //create a feeling object from the given feeling string
        Emotionpost temp1 = new Emotionpost(comment, temp); //create the emotion post with the feeling and comment
        return temp1; //return the object
    }

    public void AddFeeling(View v){ //Will be called by my buttons.
        String feeling = v.getTag().toString(); //using the tags of the button, figure out which button was pressed. 
		//taken from https://stackoverflow.com/questions/5706942/possibility-to-add-parameters-in-button-xml
		//question was answered by user OcuS on April 18th 2011.
        Emotionpost addedfeeling = newpost(feeling, "");//call newpost function to get out emotionpost object
        posts.add(0, addedfeeling); //add the new post to our arraylist
        saveFeelings(); //save the new added feelings to our save file
    }

    public void getComment(View v){
        final String feeling = v.getTag().toString();
		//wanted to be able to add a comment without having to start a new activity, used again in viewHistory
		//learned from https://stackoverflow.com/questions/10903754/input-text-dialog-android
		//question was answered by user Aaron on June 5th 2012
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your comment:"); //setting title of the popup


        final EditText input = new EditText(this); //create my edit text

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT); //set the input type to normal text
        builder.setView(input); //set the view to be focused on the edit text, as that is all that it has


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { //the ok button and what will happen when pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String comment = input.getText().toString(); //get their comment
                AddFeelingC(comment,feeling); //create a post using their comment
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { //the cancel button and what will happen when pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
				//do nothing if the dialog was cancelled
                dialog.cancel();
            }
        });

        builder.show(); //open the popup

    }
    public void AddFeelingC(String comment, String feeling){ //function when the comment button is pressed and a comment is entered
        Emotionpost addedfeeling = newpost(feeling,comment); //create an emotionpost with the feeling and comment
        posts.add(0, addedfeeling); //add it to the start of the arraylist to maintain time order.
        saveFeelings(); //save to our file again
    }

    public void saveFeelings(){
        //taken from https://github.com/shidahe/lonelyTwitter/
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE); // open a FOS using our filename
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson(); //using google's GSON
            gson.toJson(posts, out); //convert our arraylist into JSON
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void loadFeelings(){
        try {
            FileInputStream fis = openFileInput(FILENAME); //using our file name, open an input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson(); //using googles gson
 
            Type listType = new TypeToken<ArrayList<Emotionpost>>(){}.getType();
            posts = gson.fromJson(in, listType); //convert the JSON into our list again

        } catch (FileNotFoundException e) {
            ArrayList<Emotionpost> posts = new ArrayList<Emotionpost>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public void clearposts(){//helper functions for clearing all posts
        posts.clear(); //clear our arraylist and
        saveFeelings(); //save the empty array list
    }
}

