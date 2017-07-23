package com.silktree.project.cricscore;

/*
    NAME: Interface2.java
    AUTHOR: Joel Julian
    DATE: 22-JUL-2017
    DESCRIPTION: This file will hold all the functions related to third interface
* */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Interface3 extends AppCompatActivity {

    //member variables
    private String battingTeamName;
    private String bowlingTeamName;
    private int target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface3);

        //Extracting all the data passed from interface 2
        Intent intent = getIntent();
        //Extracting the batting team name
        battingTeamName = intent.getStringExtra("bowling_team_name");
        //Extracting the bowling team name
        bowlingTeamName = intent.getStringExtra("batting_team_name");
        //Extracting the target
        target = intent.getIntExtra("target", 0);

        //Displaying the information
        Toast.makeText(this, battingTeamName + " " + bowlingTeamName + " " + target, Toast.LENGTH_LONG).show();

    }
}
