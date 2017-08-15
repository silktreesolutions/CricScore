package com.silktree.project.cricscore;

/*
    NAME: Interface3.java
    AUTHOR: Joel Julian
    DATE: 15-AUG-2017
    DESCRIPTION: This file will hold all the functions related to third interface
* */

import android.content.Context;
import android.content.Intent;
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

public class Interface3 extends AppCompatActivity {

    //member variables
    private String battingTeamName;
    private String bowlingTeamName;
    private int target;
    private int totalNoOfPlayers;

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
        totalNoOfPlayers = intent.getIntExtra("total_players", 1);

        //Displaying the information
        //Toast.makeText(this, battingTeamName + " " + bowlingTeamName + " " + target, Toast.LENGTH_LONG).show();


        //Setting the batting team name
        TextView battingTeam = (TextView)findViewById(R.id.batting_team_name_textview);
        battingTeam.setText("TEAM " + battingTeamName);

        //Setting the bowling team name
        TextView bowlingTeam = (TextView) findViewById(R.id.bowling_team_name_textview);
        bowlingTeam.setText("TEAM " + bowlingTeamName);

        //Setting the target
        TextView finalTarget = (TextView) findViewById(R.id.final_target);
        finalTarget.setText("" + target);


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

        //checking of the target has been achieved
        if(currentScore < target) {
            //setting the current score after adding the runs
            currentScoreTextView.setText(String.valueOf(currentScore));
        }else{
            //disabling the buttons as the target has been achieved
            findViewById(R.id.batting_plus_button).setEnabled(false);
            findViewById(R.id.bowling_plus_button).setEnabled(false);
            findViewById(R.id.bowling_minus_button).setEnabled(false);
            //setting the current score after adding the runs
            currentScoreTextView.setText(String.valueOf(currentScore));

            //display the congratulatory message for the batting team
        }
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

        if(!findViewById(R.id.batting_plus_button).isEnabled()){
            findViewById(R.id.batting_plus_button).setEnabled(true);
            findViewById(R.id.bowling_plus_button).setEnabled(true);
            findViewById(R.id.bowling_minus_button).setEnabled(true);
            findViewById(R.id.winning_msg_textview).setVisibility(View.INVISIBLE);
            findViewById(R.id.start_new_match_button).setVisibility(View.INVISIBLE);

            //hide the congratulatory message
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

                //display the congratulatory message to the bowling team
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
            //hide the congratulatory message
            findViewById(R.id.winning_msg_textview).setVisibility(View.INVISIBLE);
            findViewById(R.id.start_new_match_button).setVisibility(View.INVISIBLE);
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
            findViewById(R.id.bowling_minus_button).setEnabled(true);
        }

        //Resetting the score
        CricScoreUtility.resetInnings((TextView) findViewById(R.id.score_textview), (TextView) findViewById(R.id.wicket_textview), (Spinner) findViewById(R.id.no_of_runs_spinner));

        //enabling the show result button
        findViewById(R.id.show_result_button).setEnabled(true);

        //hide the congratulatory message
        findViewById(R.id.winning_msg_textview).setVisibility(View.INVISIBLE);

        //start new match button made invisible
        findViewById(R.id.start_new_match_button).setVisibility(View.INVISIBLE);
    }

    /*
* This function displays the result of the match
* @param current view
* * */
    public void showResult(View view){

        //Getting the current wicket total
        TextView wicketTextView = (TextView) findViewById(R.id.wicket_textview);
        final int currentWicketTotal = Integer.parseInt(wicketTextView.getText().toString());

        TextView currentScoreTextView = (TextView) findViewById(R.id.score_textview);
        final int currentScore = Integer.parseInt(currentScoreTextView.getText().toString());


        //Checking if the bowling plus button is enabled
        //Checking if the batting plus button is enabled
        //The pop up to be displayed if all the wickets have not fallen
        if(findViewById(R.id.bowling_plus_button).isEnabled() && findViewById(R.id.batting_plus_button).isEnabled()) {

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
            //Setting the button listener for the YES button
            yesButton.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){

                    //start new match button made visible
                    findViewById(R.id.start_new_match_button).setVisibility(View.VISIBLE);
                    //display the congratulatory message
                    TextView winningmsgTextView = (TextView) findViewById(R.id.winning_msg_textview);
                    winningmsgTextView.setVisibility(View.VISIBLE);

                    if(target > currentScore){
                        int runs = target - currentScore;
                        //String msg = getResources().getString(R.string.interface3_winning_msg_batting_team, bowlingTeamName, String.valueOf(runs));
                        //String msg = getResources().getString(R.plurals.winning_msg_batting_team, bowlingTeamName, String.valueOf(runs));
                        String msg = getResources().getQuantityString(R.plurals.winning_msg_batting_team, runs, bowlingTeamName, runs);
                        winningmsgTextView.setText(msg);
                    }else{
                        int remainingWickets = totalNoOfPlayers - currentWicketTotal - 1;
                        //String msg = getResources().getString(R.string.interface3_winning_msg_bowling_team, battingTeamName, String.valueOf(remainingWickets));
                        String msg = getResources().getQuantityString(R.plurals.winning_msg_bowling_team, remainingWickets, battingTeamName, remainingWickets);
                        winningmsgTextView.setText(msg);
                    }

                    //closing the pop up
                    popupWindow.dismiss();
                }
            });
        }else{
            //display the congrtulatory message
            findViewById(R.id.winning_msg_textview).setVisibility(View.VISIBLE);
            //start new match button made visible
            findViewById(R.id.start_new_match_button).setVisibility(View.VISIBLE);
            TextView winningmsgTextView = (TextView) findViewById(R.id.winning_msg_textview);
            winningmsgTextView.setVisibility(View.VISIBLE);
            if(target > currentScore){
                int runs = target - currentScore;
                //String msg = getResources().getString(R.string.interface3_winning_msg_batting_team, bowlingTeamName, String.valueOf(runs));
                String msg = getResources().getQuantityString(R.plurals.winning_msg_batting_team, runs, bowlingTeamName, runs);
                winningmsgTextView.setText(msg);
            }else{
                int remainingWickets = totalNoOfPlayers - currentWicketTotal - 1;
                //String msg = getResources().getString(R.string.interface3_winning_msg_bowling_team, battingTeamName, String.valueOf(remainingWickets));
                String msg = getResources().getQuantityString(R.plurals.winning_msg_bowling_team, remainingWickets, battingTeamName, remainingWickets);
                winningmsgTextView.setText(msg);
            }
        }
    }


    /*
* This function takes the user to the home screen
* @param current view
* * */
    public void startNewMatch(View view){
        //taking user to the home screen (i.e. interface1)

        findViewById(R.id.start_new_match_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.winning_msg_textview).setVisibility(View.INVISIBLE);

        Intent interface1 = new Intent(this, Interface1.class);
        interface1.putExtra("batting_team_name", bowlingTeamName);
        interface1.putExtra("bowling_team_name", battingTeamName);
        startActivity(interface1);
    }
}
