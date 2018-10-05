package com.example.ahzam1_feelsbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.ahzam1_feelsbook.Home.posts;

public class ViewHistory extends AppCompatActivity {
    private static final String FILENAME = "feelings.sav";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        
        ListView lv = (ListView) findViewById(R.id.lv_pastEmotions); //get the listview into our code
        final ArrayList<String> elements = new ArrayList<String>(); //create a new arraylist for the actual strings to display
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements); //set the array adapter to take from the new arraylist
        for (int i=0; i< posts.size(); i++){ // populate our elements list from the emotionpost list
            elements.add(posts.get(i).toString()); //calling our helper function toString
        }
        lv.setAdapter(arrayAdapter); //setting the adapter for the listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { //our onclick listener for when a certain element is pressed
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int index = position; //index of which element was clicked.
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewHistory.this); //reusing the dialog for editing/deleting posts
                builder.setTitle("Edit details:");

                //needed to have more than one edit text per dialog, used a linear layout to do so
                // taken from https://stackoverflow.com/questions/12876624/multiple-edittext-objects-in-alertdialog
				//question was answered by user Sam on October 13th 2012
                LinearLayout layout = new LinearLayout(ViewHistory.this); //creating a new linear layout
                layout.setOrientation(LinearLayout.VERTICAL); //making it vertical, not horizontal

                final TextView lblcomment = new TextView(ViewHistory.this); //creating a label for comment
                lblcomment.setText("Comment:");
                layout.addView(lblcomment); //add it to the linear layout

                final EditText inputcomment = new EditText(ViewHistory.this); //comment entry edittext
                inputcomment.setHint("Enter new comment...");
                layout.addView(inputcomment); //add it to the linear layout

                final TextView lbldate = new TextView(ViewHistory.this); //creating a label for date
                lbldate.setText("Date:");
                layout.addView(lbldate); //add it to the linear layout

                final EditText inputdate = new EditText(ViewHistory.this); //date entry edittext
                inputdate.setText(posts.get(index).getDateS());
                layout.addView(inputdate); // add it to the linear layout

                if (posts.get(index).getComment() != ""){
                    inputcomment.setText(posts.get(index).getComment()); // if the current element has a comment, set it as the text in the edit view
                }

                inputdate.setInputType(InputType.TYPE_CLASS_NUMBER); //force user to only use numpad.
                builder.setView(layout); //build the layout

                //setup the dialogs buttons, we will have a save, delete and cancel.
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //save whatever changes they made.
                        final String comment = inputcomment.getText().toString(); //get their comment entry
                        posts.get(index).setComment(comment);                       //set that posts comment to the entry
                        final String date = inputdate.getText().toString();         //get their date entry
                        posts.get(index).setDate(date);                             //set that posts date to the entry

                        elements.set(index, posts.get(index).toString());           //update the post in our elements list
                        Collections.sort(posts);                                    //sorting by date
                        elements.clear();                                           //clean out our old list
                        for (int i=0; i< posts.size(); i++){ //refill our elements.
                            elements.add(posts.get(i).toString());
                        }
                        arrayAdapter.notifyDataSetChanged(); //let our adapter know that we made changes to the data.
                        saveFeelings(); //save changes to our files.
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        posts.remove(index);        //remove it from our posts arraylist
                        elements.remove(index);     //remove it from the listview
                        arrayAdapter.notifyDataSetChanged();    //update the listview
                        saveFeelings();                         //save changes to our files.
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user pressed cancel, therefore do nothing.
                        dialog.cancel();
                    }
                });
                builder.show(); //show the alert dialog.

            }
        });
        Button btn_Clear = (Button) findViewById(R.id.btn_Clear);
        btn_Clear.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) { //when clear posts clicked
                posts.clear(); //empty our posts arraylist
                elements.clear();   //empty our array list
                arrayAdapter.notifyDataSetChanged(); //notify the adapter
                saveFeelings(); //save changes to files.
            }
        });

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
            
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
