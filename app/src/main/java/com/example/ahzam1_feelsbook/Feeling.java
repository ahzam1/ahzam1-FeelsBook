package com.example.ahzam1_feelsbook;

import java.util.Date;
//Class for holding a feeling, initially I had 6 different classes that extended this but realized the redundancy
public class Feeling {
    private String type;
    Feeling(String type){ //constructor
        this.type = type;
    }

    public String getType() { //getter for the feeling.
        return type;
    }
}
