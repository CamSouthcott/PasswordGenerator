package com.camsouthcott.passwordgenerator.random;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Cam Southcott on 4/18/2016.
 */
public class RandomSource {

    //Secure Random provides cryptographically secure pseudo-random numbers
    //documentation for android: http://developer.android.com/reference/java/security/SecureRandom.html
    private SecureRandom secureRandom;

    public RandomSource(){
        secureRandom = new SecureRandom();
    }

    public int randomInt(){

        byte[] randomBytes = new byte[4];

        secureRandom.nextBytes(randomBytes);

        //too lazy to write my own code to convert byte[] to int
        return new BigInteger(randomBytes).intValue();
    }

}
