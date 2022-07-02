package functions;

import classes.Message;
import classes.Packet;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Encryptor {

    private static final String secret_word = "LOVECOLA";
    public static Message encrypt(Message message)
    {
        byte [] info_bytes = message.getInfo().getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] secret_word_bytes = secret_word.getBytes(StandardCharsets.UTF_16BE);
            SecretKey secret_key = new SecretKeySpec(secret_word_bytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret_key);
            info_bytes = cipher.doFinal(info_bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(new String(info_bytes, StandardCharsets.UTF_16BE), message.getcType(), message.getbUserId());
    }

    public static byte[] encrypt(Packet packet){
        ByteBuffer byteBuffer = ByteBuffer.allocate(packet.getPacket().length);
        byteBuffer.put(packet.getPacket()[0]).put(packet.getbSrc());
        byteBuffer.putLong(packet.getPacketId());
        byteBuffer.putInt(packet.getwLen());
        byteBuffer.putInt(packet.getCrc16_1());
        byteBuffer.put(encrypt(packet.getMsg()).getBytes());
        byteBuffer.putInt(packet.getCrc16_2());
        return byteBuffer.array();
    }
}
