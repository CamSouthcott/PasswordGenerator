package com.camsouthcott.passwordgenerator.dictionary;

import com.camsouthcott.passwordgenerator.dictionary.MyRadixTreeNode.CharSequenceMatch;
import com.camsouthcott.passwordgenerator.radixtree.RadixTree;

public class MyRadixTree extends RadixTree{

	public MyRadixTree(){
		super(new MyRadixTreeNode(null,false));
	}
	
	public String matchCharSequence(String charSequence){
		
		StringBuilder wordSequence = new StringBuilder();
		
		if(charSequence != null){
			charSequence = formatWord(charSequence.trim());

			if(charSequence != null && !charSequence.isEmpty()){
				int i = 0;

				while(i < charSequence.length()){
					CharSequenceMatch bestSequenceMatch = ((MyRadixTreeNode) head).bestCharSequenceMatch(charSequence.substring(i));

					String bestMatch = bestSequenceMatch.getWord();

					if(bestMatch.isEmpty()){
						bestMatch = charSequence.substring(i,i+1);
						i++;
					}
					
					wordSequence.append(bestMatch);
					
					i += bestSequenceMatch.getMatches();
					
					if(i < charSequence.length()){
						wordSequence.append(" ");
					}
				}
			}
		}
		
		return wordSequence.toString();
	}
}
