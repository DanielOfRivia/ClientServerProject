package classes;

import functions.CRC16;
import meta.Structure;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Packet {
    private static final byte bMagic = 0x13;
    private static long bPktId;

    private byte[] bytes;

    private int messageLength;

    public Packet(String messageString, int bSrcIncome, int cType, int bUserId) {
        try {
            if (bSrcIncome > 255) throw new IllegalArgumentException("App id out of bounds exception");

            Message message = new Message(messageString, cType, bUserId);

            messageLength = message.getBytes().length;

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteBuffer byteBuffer;

            byteBuffer = ByteBuffer.allocate(2);
            byteArrayOutputStream.write(byteBuffer.put(bMagic).put((byte) bSrcIncome).array());

            byteBuffer = ByteBuffer.allocate(8);
            byteArrayOutputStream.write(byteBuffer.putLong(bPktId).array());

            byteBuffer = ByteBuffer.allocate(4);
            byteArrayOutputStream.write(byteBuffer.putInt(messageLength).array());

            byteBuffer = ByteBuffer.allocate(4);
            byteArrayOutputStream.write(byteBuffer.putInt(CRC16.fromArrayOfBytes(Arrays.copyOfRange(byteArrayOutputStream.toByteArray(), 0, 14))).array());

            byteBuffer = ByteBuffer.allocate(messageLength);
            byteArrayOutputStream.write(byteBuffer.put(message.getBytes()).array());

            byteBuffer = ByteBuffer.allocate(4);
            byteArrayOutputStream.write(byteBuffer.putInt(CRC16.fromArrayOfBytes(Arrays.copyOfRange(byteArrayOutputStream.toByteArray(), Structure.cType_offset, Structure.cType_offset + messageLength))).array());

            bytes = byteArrayOutputStream.toByteArray();
            bPktId++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getPacketId() {
        return bPktId;
    }

    public byte[] getPacket() {
        return bytes;
    }

    public int getMessageLength() {
        return messageLength;
    }
}