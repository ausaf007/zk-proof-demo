package zkp_demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import static java.math.BigInteger.ZERO;
import static zkp_demo.Constants.*;
import static zkp_demo.CryptoCommons.hash;

public class Peggy {

    public static BigInteger Y = ZERO; // This is a public key
    private static BigInteger x = ZERO; // This is the private key

    public static String Signature = null;

    public static void main(String[] args) {
        System.out.println("Hello from Peggy");
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("(Peggy) ZKP>>");
            String str = sc.nextLine();
            str = str.trim().toLowerCase();
            if (str.equals("quit") || str.equals("q")) {
                break;
            } else if (str.equals("generate --pkp")) {
                generate();
                System.out.println("Public Key: " + Y);
                System.out.println("Private Key: " + x);
            } else if (str.equals("help") || str.equals("man")) {
                // TBD Manual
            } else if (str.startsWith("sign")) {
                if (x.equals(ZERO)) {
                    // No private key pair found. Requesting user to generate keys first.
                    System.out.println("Please generate pkp first!");
                    continue;
                }
                try {
                    Signature = str.substring(5);
                } catch (Exception e) {
                    // Signature message missing.
                    System.out.println("Please enter a signature message.");
                    continue;
                }
                System.out.println("Sign: " + Signature);
                sign();
            } else {
                System.out.println("Command not found. Try running 'help' to learn more.");
            }
        }
    }

    public static void generate() {
        // Private Key
        x = new BigInteger(256, new Random());
        // Generating Public Key
        // Y = g^x mod P
        Y = g.modPow(x, P);
    }

    public static void sign() {
        // generate 256-bit random number
        BigInteger k = new BigInteger(256, new Random());
        // R = g^k mod P
        // R is also called nonce, and is unique for every signature
        BigInteger R = g.modPow(k, P);
        System.out.println("R=" + R);
        // Hashing R and Signature together
        BigInteger c = hash(R + Signature);
        System.out.println("c=" + c);
        // S = k + cx;
        BigInteger S = k.add(c.multiply(x));
        System.out.println("S=" + S);
        // Writing S, R, Y, c and Sign to peggy_sign.properties
        // This data is the ZK proof, which will be checked by Victor, to verify Peggy's signature
        Properties prop = new Properties();
        try {
            // set the properties value
            prop.setProperty("S", S + "");
            prop.setProperty("R", R.toString());
            prop.setProperty("Y", Y.toString());
            prop.setProperty("c", c + "");
            prop.setProperty("Sign", Signature);
            // save properties to project root folder
            prop.store(new FileOutputStream(pathToSignatureFile), null);
            System.out.println("Signing Successful!");

        } catch (IOException ex) {
            System.out.println("Unable to write file to disk.");
            ex.printStackTrace();
        }
    }

}
