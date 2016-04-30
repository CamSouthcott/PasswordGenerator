package com.camsouthcott.passwordgenerator.dictionary;

import android.content.Context;


import com.camsouthcott.passwordgenerator.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class WordSource {

    private static final int[] wordFiles = {R.raw.nouns,R.raw.verbs,R.raw.adjectives,R.raw.adverbs,R.raw.prepositions,R.raw.conjnctions};
    private static final Word.WordType[] wordTypeOrder = {Word.WordType.noun, Word.WordType.verb, Word.WordType.adjective, Word.WordType.adverb, Word.WordType.preposition, Word.WordType.conjunction};

    public List<List<List<Word>>> getWords(Context context){

        List<List<List<Word>>> wordsData = new ArrayList<List<List<Word>>>(26);

        int numWordTypes = Word.WordType.values().length;

        for(int letterRow = 0; letterRow < 26; letterRow++){
            wordsData.add(new ArrayList<List<Word>>(numWordTypes));

            for(int wordTypeRow = 0; wordTypeRow < numWordTypes; wordTypeRow++){
                wordsData.get(letterRow).add(new ArrayList<Word>());
            }
        }


        for(int i = 0; i < wordFiles.length; i++){

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(wordFiles[i])));

                String line;
                while((line = reader.readLine()) != null){
                    String[] newWords = line.split(" ");

                    for(String word : newWords){

                        try{
                            wordsData.get(Dictionary.getIndex(word.charAt(0))).get(wordTypeOrder[i].ordinal()).add(new Word(word,wordTypeOrder[i]));

                        }catch(IllegalArgumentException e){
                            //Don't crash the program if one of the words doesnt start with a valid char
                            //Just skip it
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return wordsData;
    }
}
