package TCP;

import classes.Packet;
import functions.Encryptor;
import meta.Structure;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class StoreServerTCP {
    private static final int SERVER_PORT = 1337;
    private ServerSocket serverSocket;
    private int port;
    private ThreadPoolExecutor connection_pool;
    private ThreadPoolExecutor process_pool;
    private int timeout;
    static ExecutorService service7 = Executors.newFixedThreadPool(6);

    private static class StoreClientHandler extends Thread {
        private final Socket clientSocket;
        private OutputStream out;
        private InputStream in;

        public StoreClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            service7.submit(() -> {
                try {
                    out = clientSocket.getOutputStream();
                    in = clientSocket.getInputStream();

                    while (in.available() == 0) {
                        byte[] b = new byte[Structure.packet_max_length];
                        in.read(b);
                        BlockingQueue<Packet> queue = new LinkedBlockingQueue<>(5);
                        Packet p = new Packet(b);
                        if (p.getbSrc() == 0) {
                            out.write(Encryptor.encrypt(new Packet("bye", 0, 1, 2)));
                            break;
                        }
                        queue.put(new Packet(Encryptor.encrypt(p)));
                    }
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
