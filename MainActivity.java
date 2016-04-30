package com.camsouthcott.passwordgenerator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.camsouthcott.passwordgenerator.dictionary.Dictionary;
import com.camsouthcott.passwordgenerator.dictionary.Word;
import com.camsouthcott.passwordgenerator.random.RandomPicker;

public class MainActivity extends AppCompatActivity {

    private static final Word.WordType[] sentenceStructure = {Word.WordType.adjective, Word.WordType.noun, Word.WordType.verb, Word.WordType.adverb};

    private static final String LOWER_CASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final int PASSWORD_LENGTH_MIN = 4;
    private static final int PASSWORD_MAX_LENGTH = 15;

    private TextView passwordTextView, mnemonicTextView;
    private CheckBox uppercaseCheckBox, numbersCheckBox;
    private Spinner passwordLengthSpinner;
    private Dictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        mnemonicTextView = (TextView) findViewById(R.id.mnemonicTextView);
        uppercaseCheckBox = (CheckBox) findViewById(R.id.uppercaseCheckBox);
        numbersCheckBox = (CheckBox) findViewById(R.id.numbersCheckBox);

        Integer[] passwordLengthValues = new Integer[PASSWORD_MAX_LENGTH - PASSWORD_LENGTH_MIN + 1];

        for(int i = PASSWORD_LENGTH_MIN; i <= PASSWORD_MAX_LENGTH; i++){
            passwordLengthValues[i - PASSWORD_LENGTH_MIN] = i;
        }

        passwordLengthSpinner = (Spinner) findViewById(R.id.passwordLengthSpinner);
        passwordLengthSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, passwordLengthValues));

        new DictionaryLoader().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        generatePassword();
    }

    @Override
    protected void onPause() {
        //Password is only stored as long as the app is active
        clearTextViews();
        super.onPause();
    }

    public void generatePasswordClick(View view){
        char[] password = generatePassword();

        passwordTextView.setText(password, 0, password.length);

        if(dictionary != null){
            mnemonicTextView.setText(mnemonic(password));
        }
    }

    public char[] generatePassword() {

        StringBuilder charsSB = new StringBuilder();

        charsSB.append(LOWER_CASE_CHARS);

        if(uppercaseCheckBox.isChecked()){
            charsSB.append(UPPER_CASE_CHARS);
        }

        if(numbersCheckBox.isChecked()){
            charsSB.append(NUMBERS);
        }

        Integer passwordLength = passwordLengthSpinner.getSelectedItemPosition() + PASSWORD_LENGTH_MIN;

        RandomPicker randomPicker = new RandomPicker();
        char[] password = new char[passwordLength];
        randomPicker.randCharArray(password,charsSB.toString().toCharArray());

        return password;
    }

    private String mnemonic(char[] password){

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < password.length; i++){
            sb.append(dictionary.randWord(password[i],sentenceStructure[i%sentenceStructure.length]));
            sb.append(" ");
        }

        return sb.toString();
    }

    private class DictionaryLoader extends AsyncTask<Void,Void,Void>{

        Dictionary mDictionary;

        @Override
        protected Void doInBackground(Void... params) {

            mDictionary = new Dictionary(getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(mDictionary != null) {
                dictionary = mDictionary;
            }

        }
    }

    public void deletePasswordClick(View view){
        clearTextViews();
    }

    private void clearTextViews(){
        passwordTextView.setText("");
        mnemonicTextView.setText("");
    }
}
