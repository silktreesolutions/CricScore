/*
    NAME: Interface1.java
    AUTHOR: Joel Julian
    DATE: 08-JUL-2017
    DESCRIPTION: This file will hold all the functions related to first interface
* */
package com.silktree.project.cricscore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.silktree.utilities.Utility;

public class Interface1 extends AppCompatActivity {

    //member variables to hold the team names
    private String battingTeamName;
    private String bowlingTeamName;
    private int total_no_players;

    private static String regExPattern = "^[a-zA-Z0-9_ ]*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface1);

        //Array Adapter created to bind the values with the spinner view
        Spinner totalplayersSpinner = (Spinner)findViewById(R.id.total_player_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.interface_no_of_players_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalplayersSpinner.setAdapter(adapter);

    }

    /*
    * This function closes the application
    * @param curent view
    * */

    public void close(View view){
        //Calling closeApp function from Utility class
        Utility.closeApp();
    }

    /*
    * This function is called when user clicks the submit button
    * @param current view
    * */

    public void submit(View view){
        //Extracting teh batting team name
        EditText battingTeam = (EditText) findViewById(R.id.batting_team_edittext);
        battingTeamName = battingTeam.getText().toString();

        //Extracting the bowling team name
        EditText bowlingTeam = (EditText) findViewById(R.id.bowling_team_edittext);
        bowlingTeamName = bowlingTeam.getText().toString();

        //Checking if the batting team name is entered
        if(!battingTeamName.equals("")){
            //Checking if bowling team name is entered
            if(!bowlingTeamName.equals("")){
                if(Utility.RegExMatcher(regExPattern, battingTeamName) && Utility.RegExMatcher(regExPattern, bowlingTeamName)){

                    //Extracting the spinner object
                    Spinner no_of_players = (Spinner) findViewById(R.id.total_player_spinner);
                    //Getting total no. of player
                    String total = no_of_players.getSelectedItem().toString();
                    total_no_players = Integer.parseInt(total);

                    //Toast to display the names
                    //Toast.makeText(this, battingTeamName + " " + bowlingTeamName + " " + total, Toast.LENGTH_LONG).show();

                    //Creating intent to move to Interface 2
                    Intent interface2 = new Intent(this, Interface2.class);
                    //Passing additional information to Interface 2
                    interface2.putExtra("battingTeamName", battingTeamName);
                    interface2.putExtra("bowlingTeamName", bowlingTeamName);
                    interface2.putExtra("totalNoOfPlayers", total_no_players);
                    //passing control to interface 2
                    startActivity(interface2);

                }else{
                    //Toast to display the error message if team name contains
                    //characters other than alpha numeric
                    Toast.makeText(this, R.string.interface1_team_name_regex_error_msg, Toast.LENGTH_LONG).show();
                }

            }else{
                //Toast to display error message if bowling team name is left blank
                Toast.makeText(this, R.string.interface1_blank_bowling_team_name_error_msg, Toast.LENGTH_LONG).show();
            }
        }else{
            //Toast to display error message if batting team name is left blank
            Toast.makeText(this, R.string.interface1_blank_batting_team_name_error_msg, Toast.LENGTH_LONG).show();
        }
    }

    /*
    * Method to retrieve the batting team name
    * @return - the batting team name
    * */
    public String getBattingTeamName(){
        return battingTeamName;
    }

    /*
    * Method to retrieve the bowling team name
    * @return - bowling team name
    * */
    public String getBowlingTeamName(){
        return bowlingTeamName;
    }

    /*
    * Method to retrieve the total no. of players in each side
    * @return - total no. of players
    * */

    public int getTotalNoOfPlayers(){
        return total_no_players;
    }
}
