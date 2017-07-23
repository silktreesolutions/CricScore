/*
    NAME: Interface2.java
    AUTHOR: Joel Julian
    DATE: 22-JUL-2017
    DESCRIPTION: This file will hold all the functions related to second interface
* */
package com.silktree.project.cricscore;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.silktree.utilities.CricScoreUtility;
import com.silktree.utilities.Utility;

import static java.lang.Integer.parseInt;

public class Interface2 extends AppCompatActivity {

    //member variables
    private String battingTeamName;
    private String bowlingTeamName;
    private int totalNoOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface2);

        //Getting the intent passed from interface 1
        Intent intent = getIntent();
        //Extracting all the information sent from interface 1
        battingTeamName = intent.getStringExtra("battingTeamName");
        bowlingTeamName = intent.getStringExtra("bowlingTeamName");
        totalNoOfPlayers = intent.getIntExtra("totalNoOfPlayers", 1);


        //Setting the batting team name
        TextView battingTeamNameTextView =  (TextView) findViewById(R.id.batting_team_name_textview);
        battingTeamNameTextView.setText("TEAM " + battingTeamName);

        //Setting the bowling team name
        TextView bowlingTeamNameTextView = (TextView) findViewById(R.id.bowling_team_name_textview);
        bowlingTeamNameTextView.setText("TEAM " + bowlingTeamName);

        //Array Adapter created to bind the values with the spinner view
        Spinner totalplayersSpinner = (Spinner)findViewById(R.id.no_of_runs_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.no_of_runs_array, android.R.layout.simple_spinner_item);
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
    * This function adds runs to the current score
    * @param current view
    * * */
    public void addRuns(View view){

        //Extracting the current score
        TextView currentScoreTextView = (TextView) findViewById(R.id.score_textview);

        //Extracting the runs to add
        Spinner runSpinnerView = (Spinner) findViewById(R.id.no_of_runs_spinner);

        //getting the current score after adding the runs
        int currentScore = CricScoreUtility.setScore(parseInt(currentScoreTextView.getText().toString()), parseInt(runSpinnerView.getSelectedItem().toString()), CricScoreUtility.ADD_RUNS);

        //setting the current score after adding the runs
        currentScoreTextView.setText(String.valueOf(currentScore));
    }

    /*
    * This function subtracts runs to the current score
    * @param current view
    * * */

    public void subtractRuns(View view){

        //Extracting the current score
        TextView currentScoreTextView = (TextView) findViewById(R.id.score_textview);
        //Extracting the runs to add
        Spinner runsSpinnerView = (Spinner) findViewById(R.id.no_of_runs_spinner);

        //Getting the current score
        int currentScore = Integer.parseInt(currentScoreTextView.getText().toString());
        //Getting the runs to be subtracted
        int runs = Integer.parseInt(runsSpinnerView.getSelectedItem().toString());


        //Checking if the runs to be subtracted cause the score to go below zero
        if((currentScore - runs) >= 0) {
            //getting the current score after subtracting the runs
            int newCurrentScore = CricScoreUtility.setScore(currentScore, runs, CricScoreUtility.SUBTRACT_RUNS);

            //setting the current score after subtracting the runs
            currentScoreTextView.setText(String.valueOf(newCurrentScore));
        }else{
            //Displaying the error message
            Toast.makeText(this, R.string.interface2_subtract_runs_err_msg, Toast.LENGTH_LONG).show();
        }
    }

        /*
    * This function add wickets to the current wicket total
    * @param current view
    * * */

    public void addWicket(View view){

        //Getting the current wicket total
        TextView wicketTextView = (TextView) findViewById(R.id.wicket_textview);
        int currentWicketTotal = Integer.parseInt(wicketTextView.getText().toString());

        //Checking the total no. of wickets
        if(currentWicketTotal < (totalNoOfPlayers)) {
            //Adding to the total no. of wickets
            int newCurrentWicketTotal = CricScoreUtility.setWickets(currentWicketTotal, CricScoreUtility.ADD_WICKETS);
            //Displaying the total no. of wickets
            wicketTextView.setText(String.valueOf(newCurrentWicketTotal));
            //Checking if the last wicket has fallen
            if((newCurrentWicketTotal == totalNoOfPlayers) || (newCurrentWicketTotal == totalNoOfPlayers - 1)){
                //disable buttons all buttons except wicket minus button
                findViewById(R.id.batting_plus_button).setEnabled(false);
                findViewById(R.id.batting_minus_button).setEnabled(false);
                findViewById(R.id.bowling_plus_button).setEnabled(false);
            }
        }
    }

    /*
    * This function subtract wickets from the current wicket total
    * @param current view
    * * */

    public void subtractWickets(View view){

        //Getting the current wicket total
        TextView wicketTextView = (TextView) findViewById(R.id.wicket_textview);
        int currentWicketTotal = Integer.parseInt(wicketTextView.getText().toString());


        //Enabling buttons if entire team is out
        if(!(findViewById(R.id.bowling_plus_button).isEnabled())){
            //enabling buttons all buttons except wicket minus button
            findViewById(R.id.batting_plus_button).setEnabled(true);
            findViewById(R.id.batting_minus_button).setEnabled(true);
            findViewById(R.id.bowling_plus_button).setEnabled(true);
        }


        //Checking no. of wickets
        if(currentWicketTotal > 0){
            //subtracting the no. of wickets
            int newWicketTotal = CricScoreUtility.setWickets(currentWicketTotal, CricScoreUtility.SUBTRACT_WICKETS);
            //displaying the no. of wickets
            wicketTextView.setText(String.valueOf(newWicketTotal));

        }else{
            //Displaying the error message
            Toast.makeText(this, R.string.interface2_subtract_wickets_err_msg, Toast.LENGTH_LONG).show();
        }
    }

    /*
    * This function resets the score
    * @param current view
    * * */
    public void reset(View view){

        //Enabling buttons if entire team is out
        if(!(findViewById(R.id.bowling_plus_button).isEnabled())){
            //enabling buttons all buttons except wicket minus button
            findViewById(R.id.batting_plus_button).setEnabled(true);
            findViewById(R.id.batting_minus_button).setEnabled(true);
            findViewById(R.id.bowling_plus_button).setEnabled(true);
        }

        //Resetting the score
        CricScoreUtility.resetInnings((TextView) findViewById(R.id.score_textview), (TextView) findViewById(R.id.wicket_textview), (Spinner) findViewById(R.id.no_of_runs_spinner));
    }

    /*
    * This method is called when Innings Finished button is clicked
    * @param view - take the current view
    * */

    public void inningsFinished(View view){

        //Creating an intent to acdess interface 3
        final Intent interface3 = new Intent(this, Interface3.class);
        TextView targetTextView = (TextView) findViewById(R.id.score_textview);
        final int target = Integer.parseInt(targetTextView.getText().toString()) + 1;

        //Checking if the bowling plus button is enabled
        //The pop up to be displayed if all the wickets have not fallen
        if(findViewById(R.id.bowling_plus_button).isEnabled()) {

            //Getting the current wicket total
            TextView wicketTextView = (TextView) findViewById(R.id.wicket_textview);
            int currentWicketTotal = Integer.parseInt(wicketTextView.getText().toString());

            //Calculating the remaining wickets
            int remainingWickets = (totalNoOfPlayers - currentWicketTotal) - 1;

            //Creating the popup window
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popup));
            //Getting access to the textview displayed on the popup
            TextView popupText = (TextView) popupView.findViewById(R.id.popup_text);
            //Getting the text to be displayed on the pop up
            String msg = getResources().getQuantityString(R.plurals.popupMessage, remainingWickets, remainingWickets);
            //Setting the text to be displayed
            popupText.setText(msg);

            //Creating a popup window
            final PopupWindow popupWindow = new PopupWindow(popupView, 500, 200,true);
            //Setting display location of the pop up
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            //Extracting the NO button view
            Button noButton = (Button) popupView.findViewById(R.id.no_button);
            //Setting the button listener for the NO button
            noButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    //Closing the popup
                    popupWindow.dismiss();
                }
            });

            //Extracting the YES button view
            Button yesButton = (Button) popupView.findViewById(R.id.yes_button);
            //Setting the button listener for the YES button
            yesButton.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    //data to be sent to interface 3
                    //1. Batting team name
                    //2. Bowling team name
                    //3. Target score + 1
                    interface3.putExtra("batting_team_name", battingTeamName);
                    interface3.putExtra("bowling_team_name", bowlingTeamName);
                    interface3.putExtra("target", target);
                    //moving to interface 3
                    startActivity(interface3);
                    //closing the pop up
                    popupWindow.dismiss();
                }
            });
        }else{
            //moving to interface 3 directly if all the wickets have fallen
            //data to be sent to interface 3
            interface3.putExtra("batting_team_name", battingTeamName);
            interface3.putExtra("bowling_team_name", bowlingTeamName);
            interface3.putExtra("target", target);
            startActivity(interface3);
        }
    }
}
