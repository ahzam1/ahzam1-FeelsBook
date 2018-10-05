package com.example.ahzam1_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.ahzam1_feelsbook.Home.posts;
public class ViewTotals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_totals);
        ArrayList<Integer> totals = new ArrayList<Integer>();
        //order goes as such: Love, Joy, Surprise, Anger, Sadness, Fear.
        // when referring to index.
        for(int i=0; i< 6; i++){
            totals.add(0);
        }
        for(int i=0; i<posts.size();i++){ //this loop will go through my post arraylist, and count how much of each emotion exists.
            String feeling = posts.get(i).getFeeling(); //get the emotion
            Integer newVal;
            switch (feeling){ //use a switch case statement to increment the appropriate counters
                case "Love":
                    newVal = totals.get(0) +1;
                    totals.set(0, newVal);
                    break;
                case "Joy":
                    newVal = totals.get(1) +1;
                    totals.set(1, newVal);
                    break;
                case "Surprise":
                    newVal = totals.get(2) +1;
                    totals.set(2, newVal);
                    break;
                case "Anger":
                    newVal = totals.get(3) +1;
                    totals.set(3, newVal);
                    break;
                case "Sadness":
                    newVal = totals.get(4) +1;
                    totals.set(4, newVal);
                    break;
                case "Fear":
                    newVal = totals.get(5) +1;
                    totals.set(5, newVal);
                    break;
                default:
                    break;
            }
        }

        TextView joycounter = (TextView) findViewById(R.id.tv_Joycounter);
        TextView lovecounter = (TextView) findViewById(R.id.tv_LoveCounter);
        TextView surprisecounter = (TextView) findViewById(R.id.tv_SurpriseCounter);
        TextView angercounter = (TextView) findViewById(R.id.tv_AngerCounter);
        TextView sadnesscounter = (TextView) findViewById(R.id.tv_SadnessCounter);
        TextView fearcounter = (TextView) findViewById(R.id.tv_FearCounter);
        lovecounter.setText(totals.get(0).toString());
        joycounter.setText(totals.get(1).toString());
        surprisecounter.setText(totals.get(2).toString());
        angercounter.setText(totals.get(3).toString());
        sadnesscounter.setText(totals.get(4).toString());
        fearcounter.setText(totals.get(5).toString());

    }
}
