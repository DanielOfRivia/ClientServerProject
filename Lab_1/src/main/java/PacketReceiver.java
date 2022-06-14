
import functions.Crypto;
import meta.Structure;
import functions.CRC16;

import java.util.Arrays;
import java.nio.charset.StandardCharsets;

public class PacketReceiver {

    private final byte[] packetBytes;
    private final byte[] messageBytes;

    public PacketReceiver(byte[] bytes) {
        packetBytes = bytes;

        messageBytes = Crypto.decrypt(Arrays.copyOfRange(bytes, Structure.message_offset + 8, Structure.message_offset + getMessageLength()));
    }

    /** ========== Packet methods (check if packet is correct) ========== **/

    public boolean isCorrectPacketCRC() {
        return isCorrectCRC(Structure.bMagic_offset, Structure.wCrc16_offset);
    }

    /** ========== Message methods (get as a string | get length | check if message is correct) ========== **/

    public String getMessageValue() {
        return new String(Arrays.copyOfRange(messageBytes,0, messageBytes.length), StandardCharsets.UTF_16BE);
    }

    public int getMessageLength() {
        return getCRCValue(Arrays.copyOfRange(packetBytes, Structure.wLen_offset, Structure.wCrc16_offset));
    }

    public boolean isCorrectMessageCRC() {
        return isCorrectCRC(Structure.message_offset, Structure.message_offset + getMessageLength());
    }

    /** ========== Packet Receiver methods (check CRC from packet using offsets | get CRC value from array of bytes | check if both packet and message is correct) ========== **/

    private boolean isCorrectCRC(int crcStart, int crcOffset){
        return (CRC16.fromArrayOfBytes(Arrays.copyOfRange(packetBytes, crcStart, crcOffset)) == getCRCValue(Arrays.copyOfRange(packetBytes, crcOffset, crcOffset + 4)));
    }

    private static int getCRCValue(byte[] bytes) {
        return bytes[3] & 0xFF | (bytes[2] & 0xFF) << 8 | (bytes[1] & 0xFF) << 16 | (bytes[0] & 0xFF) << 24;
    }

    public boolean isCorrect() {
        return isCorrectPacketCRC() && isCorrectMessageCRC();
    }
}