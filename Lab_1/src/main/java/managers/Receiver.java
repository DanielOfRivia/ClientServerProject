package managers;

import classes.Packet;

import java.util.concurrent.*;

public class Receiver implements Runnable, interfaces.IReceiver {
    Packet packet;
    BlockingQueue<Packet> queueRequests, queueResponse;
    static ExecutorService service6 = Executors.newFixedThreadPool(6);

    public Receiver(BlockingQueue<Packet> queue) throws InterruptedException {
        queueResponse = new LinkedBlockingQueue<>(5);
        this.queueRequests = queue;
        this.packet = queue.take();
    }
    @Override
    public void receiveMessage()
    {
        try {
            queueRequests.put(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            service6.submit(this::receiveMessage);
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void shutdown(){
        try{
            service6.shutdown();
            while(!service6.awaitTermination(24L, TimeUnit.HOURS)){
                System.out.println("waiting for termination...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
