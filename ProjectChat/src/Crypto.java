
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.lang.Math.*;

public class Crypto {

    private SecretKeySpec AESkey;
    private int CaesarKey;
    private String crypto;
    String alphabet = "abcdefghijklmnopqrstuvwxyzåäö";

    public static void main(String[] args) {
        String text = "ab";
        Crypto nyttKrypto = new Crypto("Caesar");
        System.out.println(nyttKrypto.enCrypt(text));
    }

    public Crypto(String typeOfCrypt) {
        crypto = typeOfCrypt;
        generateKey(crypto);
    }

    public String deCrypt(String encrypted) {
        if (crypto.equals("AES")) {

        } else if (crypto.equals("Caesar")) {
            for (int i = 0; i < encrypted.length(); i++) {
                String returnText = "";
                char word = encrypted.charAt(i);
                int index = alphabet.indexOf(encrypted.toLowerCase().charAt(i));
                index = index - CaesarKey;
                word = alphabet.charAt(index); //Borde funka för små bokstäver nu
                returnText = returnText + word;
                return returnText;

            }

        }
        return "Hej";
    }

    public String enCrypt(String text) {
        if (crypto.equals("AES")) {

        } else if (crypto.equals("Caesar")) {
            String returnText = "";
            // String alphabet = "abcdefghijklmnopqrstuvwxyzåäö";
            String alphabet2 = alphabet.toUpperCase();
            text.length();
            for (int i = 0; i < text.length(); i++) {
                char word = text.charAt(i);
                int index = alphabet.indexOf(text.toLowerCase().charAt(i));
                index = index + CaesarKey;
                word = alphabet.charAt(index); //Borde funka för små bokstäver nu
                returnText = returnText + word;
                return returnText;
            }
            text = returnText;
            return text;
        }
        return "Hej";
    }

// Help from Christians lecture
    public void generateKey(String crypto) {
        byte[] newKey;
        if (crypto.equals("AES")) {
            try {
                KeyGenerator AESgen = KeyGenerator.getInstance("AES");
                AESgen.init(128);
                AESkey = (SecretKeySpec) AESgen.generateKey();
                newKey = AESkey.getEncoded();

            } catch (NoSuchAlgorithmException e) {
                e.getMessage();
            }
        } else if (crypto.equals("Caesar")) {
            CaesarKey = 2;//*(int)Math.random();
        }
    }
}
