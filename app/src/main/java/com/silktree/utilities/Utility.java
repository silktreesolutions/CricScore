/**
 NAME: Utility.java
 AUTHOR: Joel Julian
 DATE: 06-JUL-2017
 DESCRIPTION: This class will hold all utility functions
 */

package com.silktree.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Utility {

    /*
    * This function closes the app
    * */
    public static void closeApp(){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /*
    * This function helps to determine whether the string matches the regex pattern
    * @param regexPattern - regular expression pattern against which the text has to
    *                       validated against
    * @param stringToMatch - string which has to be macthed against the regular expression
    * @return - returns a true or false based on the match
    * */
    public static boolean RegExMatcher(String regexPattern, String stringToMatch){

        //variable to hold the result
        boolean result = false;

        //creating a pattern of the regex
        Pattern pattern = Pattern.compile(regexPattern);
        //creating a match of the string with the regex pattern
        Matcher matcher = pattern.matcher(stringToMatch);

        //Checking if the pattern and string match
        if(matcher.matches()){
            //setting the result
            result = true;
        }

        //returning result
        return result;
    }
}
