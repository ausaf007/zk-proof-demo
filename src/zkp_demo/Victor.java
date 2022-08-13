package zkp_demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;
import java.util.Scanner;

import static zkp_demo.Constants.*;
import static zkp_demo.CryptoCommons.hash;

public class Victor {
    public static void main(String[] args) {
        System.out.println("Hello from Victor");
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("(Victor) ZKP>>");
            String str = sc.nextLine();

            str = str.trim().toLowerCase();

            if (str.equals("quit") || str.equals("q")) {
                break;
            } else if (str.equals("help") || str.equals("man")) {
                //TBD Manual
            } else if (str.equals("verify")) {
                //loading Signature file from disk
                Properties prop = new Properties();
                try (FileInputStream fis = new FileInputStream(pathToSignatureFile)) {
                    prop.load(fis);
                } catch (IOException ex) {
                    System.out.println("Signature from Peggy not found. Please try again.");
                    ex.printStackTrace();
                    continue;
                }
                BigInteger S = new BigInteger(prop.getProperty("S"));
                BigInteger c = new BigInteger(prop.getProperty("c"));
                BigInteger R = new BigInteger(prop.getProperty("R"));
                BigInteger Y = new BigInteger(prop.getProperty("Y"));
                String Signature = prop.getProperty("Sign");

                // LHS = (Y^c) * R
                BigInteger LHS = Y.modPow(c, P).multiply(R).mod(P);
                System.out.println("LHS=" + LHS);

                // RHS = g^s mod p
                BigInteger RHS = g.modPow(S, P);
                System.out.println("RHS=" + RHS);

                if (LHS.equals(RHS) && (c.equals(hash(R + Signature)))) {
                    System.out.println("Verification Successful. Peggy is legit.");
                } else {
                    System.out.println("Verification failed. Peggy is an IMPOSTER.");
                }
            } else {
                System.out.println("Command not found. Try running 'help' to learn more.");
            }
        }
    }
}
