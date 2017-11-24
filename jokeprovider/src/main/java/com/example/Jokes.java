package com.example;

import java.util.Random;

//Libary class to provide jokes for main activity
public class Jokes {
    public String getJoke(){
        //jokes pulled from http://www.short-funny.com/
        //get a random joke and return to the caller
        Random rand = new Random();
        int  number = rand.nextInt(4) + 1;
        if(number==1){
            return "joke1:Can a kangaroo jump higher than a house?\n"+
                    "Of course, a house doesn’t jump at all";
        }else if(number==2){
            return "joke2:Doctor: I'm sorry but you suffer from a terminal illness and have only 10 to live.\n" +
                    "Patient: \"What do you mean, 10? 10 what? Months? Weeks?!\"\n" +
                    "Doctor: Nine";
        }else if(number==3){
            return "joke3:My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.\n" +
                    "Check out this really funny jokes: http://www.short-funny.com/#ixzz4xq4PEQS7";
        }
        else {
            return "joke4:What is the difference between a snowman and a snowwoman?\n" +
                    "Snowballs.";
        }
    }
}
