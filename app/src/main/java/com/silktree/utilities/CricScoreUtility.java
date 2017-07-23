package com.silktree.utilities;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

/*
    NAME: CricScoreUtility.java
    AUTHOR: Joel Julian
    DATE: 17-JUL-2017
    DESCRIPTION: This class will hold all utility functions specific to CricScoreApp
* */
public class CricScoreUtility {

    public static final int ADD_RUNS = 1;
    public static final int SUBTRACT_RUNS = 2;

    public static final int ADD_WICKETS = 1;
    public static final int SUBTRACT_WICKETS = 2;


    /**
     * This function add or subtracts the runs to the current score
     * @param currentScore - is the current score
     * @param runs - is the runs to add/subtract
     * @param scoringOperation - to add or subtract the runs from the current score, -1 = add, -2 = subtract
     * @return the current score after carrying out the operation
     *
     */
    public static int setScore(int currentScore, int runs, int scoringOperation){

        //checking the scoring operation
        if(scoringOperation == 1){
            currentScore += runs;
        }else{
            currentScore -= runs;
        }

        return currentScore;
    }

    /**
     * This function add or subtracts wickets to the total no. of wickets
     * @param currentWicketTotal - current no. of wickets fallen
     * @param wicketOperation - wicket operation to add/subtract a wicket
     * @return the current no. of wickets
     */

    public static int setWickets(int currentWicketTotal, int wicketOperation){

        if(wicketOperation == 1){
            currentWicketTotal += 1;
        }else{
            currentWicketTotal -= 1;
        }

        return currentWicketTotal;
    }

    /*
    * This function resets the values of the score
    * @param runsTextView - The TextView representing the runs scored
    * @param wicketsTextView - The TextView representing the wickets
    * @param noOfRuns - The Spinner representing the no. of runs
    * */
    public static void resetInnings(TextView runsTextView, TextView wicketsTextView, Spinner noOfRuns){

        //Setting the value to zero
        runsTextView.setText(String.valueOf("0"));
        //Setting the value to zero
        wicketsTextView.setText(String.valueOf("0"));
        //Setting the value to zeroth index
        noOfRuns.setSelection(0);
    }

    public static void initiatePopUpWindow(){

    }
}
