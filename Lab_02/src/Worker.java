public class Worker extends Thread{

	private final int id;
	private final Data data;
	
	public Worker(int id, Data d){
		this.id = id;
		data = d;
		this.start();
	}
	
	@Override
	public void run(){
		super.run();
		for (int i = 0; i < 5; i++){
			synchronized (data) {
				try {
					while (id != data.getState()) {
						data.wait();
					}
					if (id == 1) {
						data.Tak();
					} else if (id == 2) {
						data.Toi();
					} else {
						data.Tic();
					}
					data.notify();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
}
