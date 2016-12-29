package bob;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Traitement extends Thread {

	private DataInputStream in;
	private ObjectOutputStream out;
	private Socket connection;
	ServerSocket providerSocket;
	private String message;
	

	public Traitement(DataInputStream inn, ObjectOutputStream o) {

		in = inn;
		out = o;
	}

	public Traitement(ServerSocket PproviderSocket, Socket Pconnection) {

		providerSocket = PproviderSocket;
		connection = Pconnection;
	}

	@Override
	public void run() {

		try {

			if (Provider.getConnectionnumber() <101) Provider.setConnectionnumber(Provider.getConnectionnumber() + 1);
			Provider.insertmetrics();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			message = br.readLine();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (Provider.getConnectionnumber() >0)Provider.setConnectionnumber(Provider.getConnectionnumber() - 1);
				Provider.insertmetrics();
				connection.close();

			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

}
