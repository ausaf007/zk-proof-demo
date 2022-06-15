package zkp_demo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoCommons {

    //Returns last 4 decimal digits of SHA-256 Hash of string str
    public static Integer compactHash(String str) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(str.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        BigInteger hash= new BigInteger(hex,16);

        return hash.remainder(BigInteger.valueOf(10000)).intValue();
    }

    //returns a random 4 digit number
    public static Integer random4(){
        return ((int)(Math.random()*9000)+1000);
    }
}
