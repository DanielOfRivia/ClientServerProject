package functions;

import classes.Packet;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Encryptor {

    private static final String secret_word = "LOVECOLA";
    public static byte[] encrypt(Packet packet)
    {
        byte[] res = packet.getPacket();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] secret_word_bytes = secret_word.getBytes(StandardCharsets.UTF_16BE);
            SecretKey secret_key = new SecretKeySpec(secret_word_bytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret_key);
            res = cipher.doFinal(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}