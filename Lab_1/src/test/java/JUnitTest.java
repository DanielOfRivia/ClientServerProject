
import functions.Crypto;
import org.junit.Test;
import org.junit.Assert;

import java.nio.charset.StandardCharsets;

public class JUnitTest {

    @Test
    public void main(){
        String string = "String to test crypto";

        /** ========== Crypto tests ========== **/

        byte[] encrypted = Crypto.encrypt(string.getBytes(StandardCharsets.UTF_16BE));
        byte[] decrypted = Crypto.decrypt(encrypted);

        Assert.assertEquals(string, new String(decrypted, StandardCharsets.UTF_16BE));
    }
}