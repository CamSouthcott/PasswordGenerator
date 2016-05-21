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
import com.camsouthcott.passwordgenerator.random.RandomPicker;

public class MainActivity extends AppCompatActivity {

    private static final String LOWER_CASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final int PASSWORD_LENGTH_MIN = 4;
    private static final int PASSWORD_MAX_LENGTH = 15;

    private TextView passwordTextView, hintTextView;
    private CheckBox uppercaseCheckBox, numbersCheckBox;
    private Spinner passwordLengthSpinner;
    private Dictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        hintTextView = (TextView) findViewById(R.id.hintTextView);
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

    private String getPassword(){
        CharSequence password = passwordTextView.getText();

        if(password != null){
            return password.toString();
        }

        return null;
    }

    public void generatePasswordClick(View view){
        String password = new String(generatePassword());

        passwordTextView.setText(password);

        if(dictionary != null){
            new HintAsyncTask().execute(password);
        }
    }

    public char[] generatePassword() {

        clearTextViews();

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

                String password = getPassword();
                if(password != null){
                    new HintAsyncTask().execute(password);
                }
            }

        }
    }

    private class HintAsyncTask extends AsyncTask<String,Void,Void>{

        Dictionary mDictionary;
        String hint;

        @Override
        protected void onPreExecute() {
            mDictionary = dictionary;
        }

        @Override
        protected Void doInBackground(String... params) {

            String password = params[0];

            if(mDictionary!= null) {
                hint = mDictionary.matchCharSequence(password);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(hint != null) {
                hintTextView.setText(hint);
            }

        }

    }

    public void deletePasswordClick(View view){
        clearTextViews();
    }

    private void clearTextViews(){
        passwordTextView.setText("");
        hintTextView.setText("");
    }
}
