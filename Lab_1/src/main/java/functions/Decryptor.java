package functions;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Decryptor {
    private static final String secret_word = "LOVECOLA";

    public static byte[] decrypt(byte[] message) {
        byte[] result_message = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] secret_word_bytes = secret_word.getBytes(StandardCharsets.UTF_16BE);
            SecretKey secret_key = new SecretKeySpec(secret_word_bytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secret_key);
            result_message = cipher.doFinal(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result_message;
    }
}