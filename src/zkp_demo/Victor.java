package zkp_demo;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;


import static zkp_demo.Constants.*;
import static zkp_demo.CryptoCommons.compactHash;

public class Victor {
    public static void main(String[] args) {

        System.out.println("Hello from Victor");

        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.print("(Victor) ZKP>>");
            String str= sc.nextLine();

            str=str.trim().toLowerCase();

            if(str.equals("quit") || str.equals("q")){
                break;
            } else if(str.equals("help") || str.equals("man")){
                //TBD Manual
            } else if(str.equals("verify")){

                //loading Signature file from disk
                Properties prop = new Properties();
                try (FileInputStream fis = new FileInputStream(pathToSignatureFile)) {
                    prop.load(fis);
                }catch (IOException ex){
                    System.out.println("Signature from Peggy not found. Please try again.");
                    ex.printStackTrace();
                    continue;

                }
                int S = Integer.parseInt(prop.getProperty("S"));
                int c = Integer.parseInt(prop.getProperty("c"));

                BigInteger R = BigInteger.valueOf(Integer.parseInt(prop.getProperty("R")));
                BigInteger Y = BigInteger.valueOf(Integer.parseInt(prop.getProperty("Y")));

                String Signature=prop.getProperty("Sign");


                // LHS = (Y^c) * R
                BigInteger LHS = Y.pow(c).multiply(R).mod(P);
                System.out.println("LHS="+LHS);

                // RHS = g^s mod p
                System.out.print("Calculating RHS...");
                BigInteger RHS = g.modPow(BigInteger.valueOf(S),P);
                System.out.println("\nRHS="+RHS);

                if(LHS.equals(RHS) && (c == compactHash(R+Signature)) ){
                    System.out.println("Verification Successful. Peggy is legit.");
                }else{
                    System.out.println("Verification failed. Peggy is an IMPOSTER.");
                }

            }else{
                System.out.println("Command not found. Try running 'help' to learn more.");
            }

        }

    }
}
