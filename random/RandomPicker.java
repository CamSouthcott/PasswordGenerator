package com.camsouthcott.passwordgenerator.random;

public class RandomPicker{

    private RandomSource randomSource;

    public RandomPicker(){
        randomSource = new RandomSource();
    }

    public <E> void randomArray(E[] target, E[] values){

        int stepSize = Integer.MAX_VALUE / values.length;

        for(int i = 0; i < target.length; i++){
            target[i] = chooseRand(values);
        }
    }

    public <E> E chooseRand(E[] values){
        int stepSize = Integer.MAX_VALUE / values.length;
        return values[Math.abs(randomSource.randomInt()/stepSize)];
    }

    //char and other primitives don't work with generics
    public void randCharArray(char[] target, char[] values ){

        int stepSize = Integer.MAX_VALUE / values.length;

        for(int i = 0; i < target.length; i++){
            target[i] = randChar(values);
        }
    }

    public char randChar(char[] values){
        int stepSize = Integer.MAX_VALUE / values.length;
        return values[Math.abs(randomSource.randomInt()/stepSize)];
    }

    public String randString(String[] values){
        int stepSize = Integer.MAX_VALUE / values.length;
        return values[Math.abs(randomSource.randomInt()/stepSize)];
    }
}
