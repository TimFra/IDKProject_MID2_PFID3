package com.drawstuff.mah.drawstuff;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Christian 2015-05-20.
 */
public class FirebaseSelectedWord {



    ArrayList<String> words = new ArrayList<String>();

    words.add("cat");
    words.add("dog");
    words.add("rabbit");
    words.add("spider");
    words.add("fish");
    words.add("house");
    words.add("flower");
    words.add("tree");
    words.add("car");
    words.add("bus");
    words.add("candy");
    words.add("cookie");
    words.add("pizza");
    words.add("tomatoe");
    words.add("apple");
    words.add("pants");
    words.add("jacket");
    words.add("socks");
    words.add("skirt");
    words.add("necklace");
    words.add("smartphone");
    words.add("tv");
    words.add("computer");
    words.add("laptop");
    words.add("harddrive");



    public void setRandomWord(){

        Random r = new Random();

        int x = r.nextInt(words.size() - 0 + 1);

        String selectedWord = words<x>.toString();

////// Push "selectedWord" to firebase


    }

}