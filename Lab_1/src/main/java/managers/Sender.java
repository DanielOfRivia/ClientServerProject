package managers;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import functions.Decryptor;

public class Sender {
    public static void sendMessage(byte[] message){
        System.out.println(new String(Objects.requireNonNull(Decryptor.decrypt(message)), StandardCharsets.UTF_16BE));
    }

}
