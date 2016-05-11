package com.camsouthcott.passwordgenerator.dictionary;

import android.content.Context;

import com.camsouthcott.passwordgenerator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WordSource {

	public static BufferedReader getBufferedReader(Context context){
		return new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.words)));
	}
}
