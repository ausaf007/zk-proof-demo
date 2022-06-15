package zkp_demo;

import java.math.BigInteger;



import static zkp_demo.CryptoCommons.compactHash;
import static zkp_demo.CryptoCommons.random4;

import static zkp_demo.Constants.P;
import static zkp_demo.Constants.g;

public class Test {

    //Generates big prime number


    public static void main(String[] args) {

        //Private Key
        int x = 4654;

        // Generating Public Key
        // Y = g^x mod P
        BigInteger Y = g.pow(x).mod(P);

        System.out.println(Y);

        // generate 4-digit random number
        int k = random4();

        System.out.println("k="+k);

        // R = g^k mod P
        BigInteger R = g.pow(k).mod(P);
        System.out.println("R="+R);

        //Hashing R
        String Signature="Peggy";
        int c = compactHash(R+Signature);
        System.out.println("c="+c);


        //S = k + cx;
        int S = k + (c*x);
        System.out.println("S="+S);



        // S, R, Y, C given to prover, verification phase begins

        System.out.println("S="+S);
        System.out.println("R="+R);
        System.out.println("Y="+Y);
        System.out.println("c="+c);

        // LHS = (Y^c) * R
        BigInteger LHS = Y.pow(c).multiply(R).mod(P);
        System.out.println("LHS"+LHS);

        // RHS = g^s mod p
        BigInteger RHS = g.pow(S).mod(P);
        System.out.println("RHS"+RHS);

        if(LHS.equals(RHS) ){
            System.out.println("Test Successful");
        }

    }


}
