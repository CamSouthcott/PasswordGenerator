package com.camsouthcott.passwordgenerator.dictionary;

/**
 * Created by Cam Southcott on 4/18/2016.
 */
public class Word {

    String letters;
    WordType wordType;

    public enum WordType{
        noun, verb, adjective, adverb, preposition, conjunction
    }

    public Word(String letters, WordType wordType){
        this.letters = letters;
        this.wordType = wordType;
    }

    public String toString(){
        return letters;
    }
}
