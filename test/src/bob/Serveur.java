package bob;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur {

	public static void main(String args[]) throws IOException {
	
   ServerSocket	serverSocket	=new ServerSocket(21425, 10);
		
   ExecutorService pool=Executors.newFixedThreadPool(2);
   
	boolean estActif = true;
	while(estActif){
	Handler handler = new Handler(serverSocket.accept()); 
	pool.execute(handler);}
	}
}
 