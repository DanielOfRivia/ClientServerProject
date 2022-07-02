package functions;

import classes.Packet;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

public class Decryptor implements Runnable{
    Packet packet;
    BlockingQueue<Packet> queue;
    private static final String secret_word = "LOVECOLA";

    public static byte[] decrypt(byte[] message) {
        byte[] result_message;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] secret_word_bytes = secret_word.getBytes(StandardCharsets.UTF_16BE);
            SecretKey secret_key = new SecretKeySpec(secret_word_bytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secret_key);
            result_message = cipher.doFinal(message);
            return result_message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void run() {
        try {
            packet = new Packet(new String(decrypt(packet.getMsg().getInfo().getBytes(StandardCharsets.UTF_16BE)), StandardCharsets.UTF_16BE), packet.getbSrc(), packet.getMsg().getcType(), packet.getMsg().getbUserId());
            System.out.println(packet.getPacketId() + ": " + packet.getMsg().getInfo());
            queue.put(packet);
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}