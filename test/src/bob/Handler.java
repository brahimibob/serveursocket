package bob;

import java.net.Socket;

public class Handler extends Thread {
	private final Socket socket;
	
	
	public Handler(Socket socket){
	this.socket=socket;
	}
}
