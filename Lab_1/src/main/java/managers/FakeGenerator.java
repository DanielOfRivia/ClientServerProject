package managers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import interfaces.IReceiver;
import classes.Packet;
import functions.Decryptor;
import functions.Encryptor;
import meta.Structure;

public class FakeGenerator extends Thread{
    private final int threadNum;
    private final Random rand;
    public FakeGenerator(int threadNum) {
        this.threadNum = threadNum;
        this.rand = new Random();
        this.start();
    }
    @Override
    public void run(){
        super.run();
        for (int i = 0; i < 10; i++){
            receiveMessage();
        }
    }
    public void receiveMessage(){
        System.out.println("Message received" + ", thread " + threadNum);

        int command = rand.nextInt(6);
        String generatedString = String.valueOf(command);
        System.out.println(generatedString + ", thread " + threadNum);
        try {
            Packet packet = new Packet(generatedString, 7, rand.nextInt(6), rand.nextInt(10000));
            byte[] decr = Decryptor.decrypt(Encryptor.encrypt(packet));
            assert decr != null;
            packet = new Packet(new String(Arrays.copyOfRange(decr, Structure.info_message_offset,
                    decr.length - 4), StandardCharsets.UTF_16BE),
                    byte_to_int(Arrays.copyOfRange(decr, Structure.bSrc_offset, Structure.bPktId_offset)),
                    byte_to_int(Arrays.copyOfRange(decr, Structure.cType_offset, Structure.bUserId_offset)),
                    byte_to_int(Arrays.copyOfRange(decr, Structure.bUserId_offset, decr.length)));
            Processor.process(packet);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static int byte_to_int(byte[] bytes){
        int res = 0;
        for (byte b : bytes) {
            res = (res << 8) + (b & 0xFF);
        }
        return res;
    }
}