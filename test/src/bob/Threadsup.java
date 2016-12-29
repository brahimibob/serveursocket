package bob;

public class Threadsup extends Thread {

	@Override
	public void run() {
		while (true) {

			try {
				Thread.sleep(60000);
				Provider.insertmetrics();
			} catch (Exception e) {

			}
		}
	}
}
