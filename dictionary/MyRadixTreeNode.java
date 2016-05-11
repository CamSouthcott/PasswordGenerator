package com.camsouthcott.passwordgenerator.dictionary;

import java.util.Map;

import com.camsouthcott.passwordgenerator.radixtree.RadixTreeNode;

public class MyRadixTreeNode extends RadixTreeNode{
	
	protected MyRadixTreeNode(RadixTreeNode parent, boolean isWord){
		super(parent,isWord);
	}
	
	private MyRadixTreeNode(RadixTreeNode parent, Map<String,RadixTreeNode> nodes, boolean isWord){
		super(parent,nodes,isWord);
	}
	
	@Override
	protected RadixTreeNode newNode(RadixTreeNode parent, boolean isWord){
		return new MyRadixTreeNode(parent, isWord);
	}
	
	@Override
	protected RadixTreeNode newNode(RadixTreeNode parent, Map<String,RadixTreeNode> nodes, boolean isWord){
		return new MyRadixTreeNode(parent, nodes, isWord);
	}
	
	protected  CharSequenceMatch bestCharSequenceMatch(String sequence){
		
		//returns the word in the radix tree that best represents the char sequence input
		//best is defined by most letters in the sequence in order, lowest length, alphabetical order
		
		
		int matches = 0;
		String bestMatch = null;
		
		for(String key : nodes.keySet()){
			
			//This works as long as every end node in the tree is a word
			int keyMatches = getCharSequenceMatch(sequence,key);
			MyRadixTreeNode node = (MyRadixTreeNode) nodes.get(key);
			
			CharSequenceMatch nodeCharSequenceMatch = node.bestCharSequenceMatch(sequence.substring(keyMatches));
			
			int totalNodeMatches = keyMatches + nodeCharSequenceMatch.getMatches();
			String nodeBestMatch =  key + nodeCharSequenceMatch.getWord();
			
			if(bestMatch == null ||totalNodeMatches > matches || (totalNodeMatches == matches && bestMatch.length() > nodeBestMatch.length())){
				matches = totalNodeMatches;
				bestMatch = nodeBestMatch;
			}
		}
		
		//if this node is a word and a better match could not be found in its nodes, return this node's value
		if(matches == 0 && isWord){
			bestMatch = "";
		}
		
		return new CharSequenceMatch(matches, bestMatch);
		
	}
	
	private int getCharSequenceMatch(String charSequence, String word){
		
		//takes a word and finds the amount of letters that can be found from the char sequence in that order in the word
		//eg. CS = asdf W = azsxdcfv M = 4, CS = asdf W = fdsa M = 1, 
		int matches = 0;
		
		for(int i = 0; matches < charSequence.length() && i < word.length(); i++){
			if(charSequence.charAt(matches) == word.charAt(i)){
				matches++;
			}
		}
		
		return matches;
	}
	
	protected class CharSequenceMatch{
		
		private int matches;
		private String word;
		
		public CharSequenceMatch(int matches, String word) {
			super();
			this.matches = matches;
			this.word = word;
		}
		public int getMatches() {
			return matches;
		}
		public String getWord() {
			return word;
		}
	}

}
