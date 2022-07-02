package classes;

import functions.CRC16;
import meta.Structure;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Packet {
    private static Long id_counter = (long)0;
    private static final Byte bMagic = 0x13;
    private Byte bSrc;
    private Long bPktId;
    private Integer wLen;
    private Integer crc16_1;
    private Message msg;
    private Integer crc16_2;
    private byte[] bytes;

    public Packet(String messageString, int bSrcIncome, int cType, int bUserId) throws Exception{
        if (messageString.length() > Structure.info_message_max_length){
            throw new Exception("Message max length exceeded");
        }
        bSrc = (byte) bSrcIncome;

        if (bSrcIncome > 255) throw new IllegalArgumentException("App id out of bounds exception");

        msg = new Message(messageString, cType, bUserId);

        wLen = msg.getBytes().length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteBuffer byteBuffer;

        byteBuffer = ByteBuffer.allocate(2);
        byteArrayOutputStream.write(byteBuffer.put(bMagic).put((byte) bSrcIncome).array());

        byteBuffer = ByteBuffer.allocate(8);
        byteArrayOutputStream.write(byteBuffer.putLong(bPktId).array());

        byteBuffer = ByteBuffer.allocate(4);
        byteArrayOutputStream.write(byteBuffer.putInt(wLen).array());

        byteBuffer = ByteBuffer.allocate(4);
        crc16_1 = CRC16.fromArrayOfBytes(Arrays.copyOfRange(byteArrayOutputStream.toByteArray(), 0, 14));
        byteArrayOutputStream.write(byteBuffer.putInt(crc16_1).array());

        byteBuffer = ByteBuffer.allocate(wLen);
        byteArrayOutputStream.write(byteBuffer.put(msg.getBytes()).array());

        byteBuffer = ByteBuffer.allocate(4);
        crc16_2 = CRC16.fromArrayOfBytes(Arrays.copyOfRange(byteArrayOutputStream.toByteArray(), Structure.cType_offset, Structure.cType_offset + wLen));
        byteArrayOutputStream.write(byteBuffer.putInt(crc16_2).array());

        bytes = byteArrayOutputStream.toByteArray();
        bPktId = id_counter;
        id_counter++;
    }

    public Packet(byte[] bytes) throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length).order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(bytes);
        //check bMagic
//        Byte real_bMagic = byteBuffer.get();
        if (byteBuffer.get() != bMagic){
            throw new Exception("Wrong bMagic");
        }
        bSrc = byteBuffer.get();

        bPktId = byteBuffer.getLong();
        wLen = byteBuffer.getInt();
        crc16_1 = byteBuffer.getInt();
        //check CRC16_1
        ByteBuffer byteBufferCrc1 = ByteBuffer.allocate(Structure.wCrc16_offset).order(ByteOrder.BIG_ENDIAN);
        byteBufferCrc1.put(bMagic).put(bSrc);
        byteBufferCrc1.putLong(bPktId);
        byteBufferCrc1.putInt(wLen);
        if(CRC16.fromArrayOfBytes(byteBufferCrc1.array()) != crc16_1){
            throw new Exception("Wrong first CRC16");
        }
        int cType = byteBuffer.getInt();
        int userID = byteBuffer.getInt();
        byte[] msg_bytes = new byte[wLen-8];
        for (int i = 0; i < wLen-8; i++){
            msg_bytes[i] = byteBuffer.get();
        }
        crc16_2 = byteBuffer.getInt();
        //check CRC16_2
        ByteBuffer byteBufferCrc2 = ByteBuffer.allocate(wLen).order(ByteOrder.BIG_ENDIAN);
        byteBufferCrc2.putInt(cType).putInt(userID);
        byteBufferCrc2.put(msg_bytes);
        if(CRC16.fromArrayOfBytes(byteBufferCrc2.array()) != crc16_2){
            throw new Exception("Wrong second CRC16");
        }
        msg = new Message(new String(msg_bytes, StandardCharsets.UTF_16BE), cType, userID);
        this.bytes = bytes;
        bPktId = id_counter;
        id_counter++;
    }

    public long getPacketId() {
        return bPktId;
    }
    public byte[] getPacket() {
        return bytes;
    }

    public Byte getbSrc(){
        return bSrc;
    }
    public Message getMsg(){
        return msg;
    }

    public int getwLen() {
        return wLen;
    }

    public int getCrc16_1(){
        return crc16_1;
    }

    public int getCrc16_2(){
        return crc16_2;
    }
}
