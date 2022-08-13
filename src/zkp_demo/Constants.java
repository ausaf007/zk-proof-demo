package zkp_demo;

import java.math.BigInteger;

import static java.math.BigInteger.TWO;

public class Constants {
    // This is the large Prime number p, in this Schnorr Signature Demo
    // All integers in this demo will lie in a finite field {0, 1, 2, 3, 4, ...., p-1}
    static final BigInteger P = TWO.pow(255).subtract(BigInteger.valueOf(19)); // Prime Number = 2^55 - 19
    static final BigInteger g = BigInteger.valueOf(9); //g is the generator

    //Path to file where peggy_sign.properties is stored.
    //This file enables Peggy to send data to Victor
    static final String pathToSignatureFile = "zkp_demo//peggy_sign.properties";
}