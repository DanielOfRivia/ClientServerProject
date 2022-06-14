
import classes.Message;
import classes.Packet;

import functions.Crypto;
import org.junit.Test;
import org.junit.Assert;

import java.nio.charset.StandardCharsets;

public class JUnitTest {

    @Test
    public void main(){
        String string = "Here is a message to test packet";
        String string_1 = "String to test crypto";

        Message message = new Message(string, 0, 0);

        Packet packet = new Packet(string, 0, 0, 0);
        Packet packet_1 = new Packet("Message to check first packet", 1, 1, 1);
        Packet packet_2 = new Packet("Message to check second packet", 2, 2, 2);

        /** ========== Packet tests ========== **/

        Assert.assertEquals(packet.getMessageLength(), message.getBytes().length);

        /** ========== Packet receiver tests ========== **/

        PacketReceiver packetReceiver_1 = new PacketReceiver(packet_1.getPacket());
        PacketReceiver packetReceiver_2 = new PacketReceiver(packet_2.getPacket());

        Assert.assertTrue(packetReceiver_1.getMessageValue().contains("first packet"));
        Assert.assertTrue(packetReceiver_2.getMessageValue().contains("second packet"));

        Assert.assertFalse(packetReceiver_1.getMessageValue().contains("Here is a message to test packet"));
        Assert.assertFalse(packetReceiver_2.getMessageValue().contains("Here is a message to test packet"));

        Assert.assertEquals(packet_1.getMessageLength(), packetReceiver_1.getMessageLength());
        Assert.assertEquals(packet_2.getMessageLength(), packetReceiver_2.getMessageLength());

        Assert.assertTrue(packetReceiver_1.isCorrect());
        Assert.assertTrue(packetReceiver_2.isCorrect());

        /** ========== Crypto tests ========== **/

        byte[] encrypted = Crypto.encrypt(string_1.getBytes(StandardCharsets.UTF_16BE));
        byte[] decrypted = Crypto.decrypt(encrypted);

        Assert.assertEquals(string_1, new String(decrypted, StandardCharsets.UTF_16BE));
    }
}