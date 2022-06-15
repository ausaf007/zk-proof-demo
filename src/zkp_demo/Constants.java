package zkp_demo;

import java.math.BigInteger;

public class Constants {

    // This is the large Prime number p, in this Schnorr Demo
    // All integers in this demo will lie in a finite field {0, 1, 2, 3, 4, ...., p-1}
    static final BigInteger P = BigInteger.valueOf(1291799);
    static final BigInteger g = BigInteger.valueOf(17); //g is the generator

    //Path to file where peggy_sign.properties is stored.
    //This file enables Peggy to send data to Victor
    static final String pathToSignatureFile = "zkp_demo//peggy_sign.properties";
}
