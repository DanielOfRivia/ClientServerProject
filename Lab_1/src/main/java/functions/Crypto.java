package functions;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Crypto {
    private static final SecretKey secretKey;
    private static final Cipher cipher;

    static {
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /** ========== Encrypt message ========== **/

    public static byte[] encrypt(byte[] data) {
        return crypt(data, "encrypt");
    }

    /** ========== Decrypt message ========== **/

    public static byte[] decrypt(byte[] data) {
        return crypt(data, "decrypt");
    }

    /** ========== Common function ========== **/

    public static byte[] crypt(byte[] data, String operation) {
        try {

            if(Objects.equals(operation, "encrypt")) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, cipher.getParameters());
            }

            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}