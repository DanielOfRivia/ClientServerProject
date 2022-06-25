package managers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import functions.Decryptor;

public class Sender {

    public static void sendMessage(byte[] message){
        System.out.println(new String(Arrays.copyOfRange(Decryptor.decrypt(message), 26, 28), StandardCharsets.UTF_16BE));
    }
}