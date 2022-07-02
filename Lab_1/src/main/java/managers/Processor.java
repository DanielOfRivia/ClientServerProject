package managers;

import classes.Packet;
import functions.Encryptor;

public class Processor {
    public static void process(Packet packet) throws Exception{
        Sender.sendMessage(Encryptor.encrypt(packet));
    }
}
