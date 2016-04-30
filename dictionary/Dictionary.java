package com.camsouthcott.passwordgenerator.dictionary;

import android.content.Context;

import com.camsouthcott.passwordgenerator.random.RandomPicker;

import java.util.List;

/**
 * Created by Cam Southcott on 4/18/2016.
 */
public class Dictionary {

    private List<List<List<Word>>> words;
    RandomPicker randomPicker;

    public Dictionary(Context context){
        randomPicker = new RandomPicker();
        words = new WordSource().getWords(context);
    }

    public String randWord(char firstLetter, Word.WordType wordType){

        List<Word> choices = words.get(getIndex(firstLetter)).get(wordType.ordinal());

        if(choices.size() > 0){
            Word word = (Word) randomPicker.chooseRand(choices.toArray());
            return word.toString();
        }

        return String.valueOf(firstLetter);
    }

    protected static int getIndex(char c){

        //convert numbers to their equivalent letters
        switch(c){
            case '0':
                c = 'o';
                break;
            case '1':
                c = 'i';
                break;
            case '2':
                c = 'z';
                break;
            case '3':
                c = 'e';
                break;
            case '4':
                c = 'a';
                break;
            case '5':
                c = 's';
                break;
            case '6':
                c = 'g';
                break;
            case '7':
                c = 't';
                break;
            case '8':
                c = 'b';
                break;
            case '9':
                c = 'q';
                break;
            default:
                break;
        }

        //All letters
        // Asci table: http://www.w3schools.com/charsets/ref_html_ascii.asp
        if(c >= 65 && c <=90){
            return c - 65;
        }else if(c >= 97 && c <= 122){
            return c - 97;
        }

        throw new IllegalArgumentException();
    }

}
