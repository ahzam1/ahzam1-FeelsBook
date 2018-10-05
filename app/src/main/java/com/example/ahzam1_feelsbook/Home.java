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
    private static final String FILENAME = "feelings.sav";
    static ArrayList<Emotionpost> posts = new ArrayList<Emotionpost>(); //my array for posts. posts will be mainly stored in here.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFeelings();
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
        //basically create an emotion post for me
        Feeling temp = new Feeling(feeling); //create a feeling object from the given feeling string
        Emotionpost temp1 = new Emotionpost(comment, temp); //create the emotion post with the feeling and comment
        return temp1; //return the object
    }

    public void AddFeeling(View v){ //Will be called by my buttons.
        String feeling = v.getTag().toString(); //using the tags of the button, figure out which button was pressed. Cited above //CITE
        Emotionpost addedfeeling = newpost(feeling, "");//call newpost function to get out emotionpost object
        posts.add(0, addedfeeling); //add the new post to our arraylist
        saveFeelings();
    }

    public void getComment(View v){
        final String feeling = v.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your comment:");


        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String comment = input.getText().toString();
                AddFeelingC(comment,feeling);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();

    }
    public void AddFeelingC(String comment, String feeling){
        Emotionpost addedfeeling = newpost(feeling,comment);
        posts.add(0, addedfeeling);
        saveFeelings();
    }

    public void saveFeelings(){
        //taken from https://github.com/shidahe/lonelyTwitter/
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(posts, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void loadFeelings(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<Emotionpost>>(){}.getType();
            posts = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            ArrayList<Emotionpost> posts = new ArrayList<Emotionpost>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public void clearposts(){
        posts.clear();
        saveFeelings();
    }
}

