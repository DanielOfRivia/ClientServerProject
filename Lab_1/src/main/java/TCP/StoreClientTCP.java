package TCP;

import classes.Packet;
import meta.Structure;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class StoreClientTCP extends Thread{
    private Socket client_socket;
    private OutputStream output;
    private InputStream input;
    private final String ip;
    private final int port;
    private static final int RECONNECT_MAX = 5;
    private static final AtomicInteger NUMBER_DEAD = new AtomicInteger(0);

    public StoreClientTCP(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void startConnection() throws IOException {
        client_socket = new Socket(ip, port);
        output = client_socket.getOutputStream();
        input = client_socket.getInputStream();
    }

    public String sendMessage(Packet p) throws Exception {
        output.write(p.getPacket());
        byte[] res = new byte[Structure.packet_max_length];
        input.read(res);
        Packet packet = new Packet(res);
        return packet.getMsg().getInfo();
    }

    public String reconnect(String ip, Packet packet, int attempt) {
        try {
            final Socket socket = new Socket(ip, port);
            socket.setSoTimeout(3500*attempt);
            return sendMessage(packet);
        } catch (Exception e) {
            if(attempt == RECONNECT_MAX){
                NUMBER_DEAD.incrementAndGet();
                System.out.println("Server is inactive. Attempt "+ NUMBER_DEAD);
                return "";
            }
            else{
                return reconnect(ip,packet,attempt+1);
            }
        }
    }

    public void stopConnection() throws IOException {
        input.close();
        output.close();
        client_socket.close();
    }

    @Override
    public void run() {
        try {
            startConnection();
        } catch (IOException e) {

            System.out.println("Couldn't connect");
        }
    }
}
