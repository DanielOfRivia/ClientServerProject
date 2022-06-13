package classes;

import org.json.JSONObject;
import functions.Crypto;

import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Message {
    private JSONObject jsonObject;
    private final int cType;
    private final int bUserId;

    public Message(String message, int cType, int bUserId) {
        this.cType = cType;
        this.bUserId = bUserId;

        try {
            jsonObject = new JSONObject();
            jsonObject.put("data", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** ========== Get encrypted array from message string ========== **/

    public byte[] getBytes() {
        byte[] messageBytes = Crypto.encrypt(jsonObject.toString().getBytes(StandardCharsets.UTF_16BE));

        ByteBuffer byteBuffer = ByteBuffer.allocate(8 + (messageBytes != null ? messageBytes.length : 0)).order(ByteOrder.BIG_ENDIAN);

        byteBuffer.putInt(this.cType);
        byteBuffer.putInt(this.bUserId);
        byteBuffer.put(messageBytes != null ? messageBytes : new byte[0]);

        return byteBuffer.hasArray() ? byteBuffer.array() : null;
    }
}
