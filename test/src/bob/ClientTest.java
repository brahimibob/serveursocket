package bob;

import java.io.*;
import java.net.*;

public class ClientTest {
    public static void main(String[] args) throws IOException {
    final int PORT_NUMBER = 44827;
    final String HOSTNAME = "localhost";

    //Attempt to connect
    try {
        Socket sock = new Socket(HOSTNAME, PORT_NUMBER);
        System.out.println("connécté");
        PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
        //Output
        out.println("Test");
        out.flush();
        System.out.println("end");
        out.close();
        sock.close();
    } catch(Exception e) {
        e.printStackTrace();
    }
    }
}