package com.camsouthcott.passwordgenerator.dictionary;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;

public class Dictionary {

	private MyRadixTree radixTree;
	
	public Dictionary(Context context){
		
		radixTree = new MyRadixTree();
		
		try {
			BufferedReader br = WordSource.getBufferedReader(context);

			String line;

			while((line = br.readLine()) != null){
				String[] wordArray = line.split(" ");

				for(String word: wordArray){
					try{
						radixTree.addWord(word);
					}catch(IllegalArgumentException e){
						//if the word contains invalid input, just skip it
					}
				}
			}
		} catch (IOException e) {

		}
	}
	
	public String matchCharSequence(String sequence){
		return radixTree.matchCharSequence(sequence);
	}
}
