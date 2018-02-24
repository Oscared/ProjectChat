
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.lang.Math.*;

public class Crypto {

    private SecretKeySpec AESKey;
    byte[] AESKeyContent;
    private Cipher AESCipher;
    private int CaesarKey;
    private String crypto;
    static String alphabet = "abcdefghijklmnopqrstuvwxyzåäö";
    String alphabet2 = alphabet.toUpperCase();


    public Crypto(String typeOfCrypt) {
        crypto = typeOfCrypt;
        generateKey(crypto);
    }
    
public String deCrypt(String encrypted) {
        String returnText = "";
        if (crypto.equals("AES")) {
            byte [] AESData = encrypted.getBytes();
            try{
            AESCipher.init(Cipher.DECRYPT_MODE, AESKey);
            AESCipher.doFinal(AESData);
            returnText =  AESData.toString();
            } catch(Exception e){
                e.getMessage();
            }
        } else if (crypto.equals("Caesar")) {
            for (int i = 0; i < encrypted.length(); i++) {
                char word = encrypted.charAt(i);
                int index = alphabet.indexOf(encrypted.toLowerCase().charAt(i));
                System.out.println(index);
                if (word == alphabet.charAt(index)) {
                    index = index - CaesarKey;
                    if (index <= 28 && index >= 0) {
                        word = alphabet.charAt(index);
                    } else if (index >= 28) {
                        word = alphabet.charAt(index - 29);
                    } else {
                        word = alphabet.charAt(29 + index);
                    }

                } else {
                    index = index - CaesarKey;
                    if (index <= 28 && index >= 0) {
                        word = alphabet2.charAt(index);
                    } else if (index >= 28) {
                        word = alphabet2.charAt(index - 29);
                    } else {
                        word = alphabet2.charAt(29 + index);
                    }

                }
                returnText = returnText + word;
            }
        }
        return returnText;
    }
    //Christian Lecturenotes
    public String enCrypt(String text) {
        if (crypto.equals("AES")) {
            try{
            AESCipher = Cipher.getInstance("AES");
            AESCipher.init(Cipher.ENCRYPT_MODE, AESKey);
            byte[] AESData = AESCipher.doFinal(text.getBytes());
            text =  AESData.toString();
            }
            catch(Exception e){
                e.getMessage();
            }
        } else if (crypto.equals("Caesar")) {
            String returnText = "";
            text.length();
            for (int i = 0; i < text.length(); i++) {
                char word = text.charAt(i);
                int index = alphabet.indexOf(text.toLowerCase().charAt(i));
                if (word == alphabet.charAt(index)) {
                    index = index + CaesarKey;
                    if (index <= 28 && index >= 0) {
                        word = alphabet.charAt(index);
                    } else if (index >= 28) {
                        word = alphabet.charAt(index - 29);
                    } else {
                        word = alphabet.charAt(29 + index);
                    }

                } else {
                    index = index + CaesarKey;
                    if (index <= 28 && index >= 0) {
                        word = alphabet2.charAt(index);
                    } else if (index >= 28) {
                        word = alphabet2.charAt(index - 29);
                    } else {
                        word = alphabet2.charAt(29 + index);
                    }

                }
                returnText = returnText + word;
            }
            text = returnText;
        }
        return text;
    }

// Help from Christians lecture
    public void generateKey(String crypto) {
        if (crypto.equals("AES")) {
            try {
                KeyGenerator AESgen = KeyGenerator.getInstance("AES");
                AESgen.init(128);
                AESKey = (SecretKeySpec) AESgen.generateKey();
                AESKeyContent = AESKey.getEncoded();
                

            } catch (NoSuchAlgorithmException e) {
                e.getMessage();
            }
        } else if (crypto.equals("Caesar")) {
            CaesarKey = 10;//*(int)Math.random();
        }
    }
   // public void setKey
}
