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
        //taken from https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        ListView lv = (ListView) findViewById(R.id.lv_pastEmotions);
        final ArrayList<String> elements = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
        for (int i=0; i< posts.size(); i++){
            elements.add(posts.get(i).toString());
        }
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int index = position; //index of which element was clicked.
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewHistory.this);
                builder.setTitle("Edit details:");

                //needed to have more than one edit text per dialog
                // taken from https://stackoverflow.com/questions/12876624/multiple-edittext-objects-in-alertdialog
                LinearLayout layout = new LinearLayout(ViewHistory.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextView lblcomment = new TextView(ViewHistory.this); //creating a label for comment
                lblcomment.setText("Comment:");
                layout.addView(lblcomment);

                final EditText inputcomment = new EditText(ViewHistory.this); //comment entry edittext
                inputcomment.setHint("Enter new comment...");
                layout.addView(inputcomment);

                final TextView lbldate = new TextView(ViewHistory.this); //creating a label for date
                lbldate.setText("Date:");
                layout.addView(lbldate);

                final EditText inputdate = new EditText(ViewHistory.this); //date entry edittext
                inputdate.setText(posts.get(index).getDateS());
                layout.addView(inputdate);

                if (posts.get(index).getComment() != ""){
                    inputcomment.setText(posts.get(index).getComment());
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
            // TODO: Handle the Exception properly later
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
