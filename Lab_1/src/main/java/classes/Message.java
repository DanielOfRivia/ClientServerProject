package classes;

import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Message {
    private final String info;
    private final int cType;
    private final int bUserId;

    public Message(String message, int cType, int bUserId) {
        this.cType = cType;
        this.bUserId = bUserId;
        this.info = message;
    }

    /** ========== Get encrypted array from message string ========== **/

    public byte[] getBytes() {
        byte[] messageBytes = info.getBytes(StandardCharsets.UTF_16BE);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8 + (messageBytes != null ? messageBytes.length : 0)).order(ByteOrder.BIG_ENDIAN);

        byteBuffer.putInt(this.cType);
        byteBuffer.putInt(this.bUserId);
        byteBuffer.put(messageBytes != null ? messageBytes : new byte[0]);

        return byteBuffer.hasArray() ? byteBuffer.array() : null;
    }

    public String getInfo(){
        return info;
    }

    public int getcType(){
        return cType;
    }
    public int getbUserId(){
        return bUserId;
    }
}
