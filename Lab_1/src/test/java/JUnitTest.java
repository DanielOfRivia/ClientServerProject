import org.junit.Test;
import managers.FakeGenerator;

public class JUnitTest {
    @Test
    public void main() throws InterruptedException{
        FakeGenerator generator1 = new FakeGenerator(1);
        FakeGenerator generator2 = new FakeGenerator(2);
        FakeGenerator generator3 = new FakeGenerator(3);
        FakeGenerator generator4 = new FakeGenerator(4);
        generator4.join();
    }
}
