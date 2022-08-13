package zkp_demo;

import java.math.BigInteger;
import java.util.Random;

import static zkp_demo.Constants.P;
import static zkp_demo.Constants.g;
import static zkp_demo.CryptoCommons.hash;

public class Test {
    public static void main(String[] args) {
        // Proper unit testing to be added in future
        // This Class Tests out the mathematical logic of Schnorr Signatures
        int x = 4654; // Private Key

        // Generating Public Key
        // Y = g^x mod P
        BigInteger Y = g.pow(x).mod(P);
        System.out.println(Y);

        // generate 16 bit random number
        int k = Integer.parseInt((new BigInteger(16, new Random())).toString());
        System.out.println("k=" + k);

        // R = g^k mod P
        BigInteger R = g.pow(k).mod(P);
        System.out.println("R=" + R);

        // Hashing R
        String Signature = "Peggy";
        BigInteger c = hash(R + Signature);
        c = c.remainder(BigInteger.valueOf((long) Math.pow(10, 4))); // get last 5 digits of c
        System.out.println("c=" + c);

        // S = k + cx;
        int S = k + (c.intValue() * x);
        System.out.println("S=" + S);

        // S, R, Y, C given to prover, verification phase begins
        System.out.println("S=" + S);
        System.out.println("R=" + R);
        System.out.println("Y=" + Y);
        System.out.println("c=" + c);

        // LHS = (Y^c) * R
        BigInteger LHS = Y.pow(c.intValue()).multiply(R).mod(P);
        System.out.println("LHS" + LHS);

        // RHS = g^s mod p
        BigInteger RHS = g.pow(S).mod(P);
        System.out.println("RHS" + RHS);

        if (LHS.equals(RHS)) {
            System.out.println("Test Successful");
        }
    }
}