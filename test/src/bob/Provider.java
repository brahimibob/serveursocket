package bob;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

public class Provider {
	static ServerSocket providerSocket;
	private static int MAXIMUM_CONNECTIONS = 99;
	private static volatile int connectionnumber = 0;
	static InfluxDB influxDB;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	static String dbName = "mydb";
	private static final Logger log = Logger.getLogger(Provider.class.getName());

	Provider() {
	}

	public static void main(String args[]) {
		Provider server = new Provider();
		influxDB = InfluxDBFactory.connect("http://localhost:8086", "bob", "bob");
		try {
			Threadsup tsup = new Threadsup();
			tsup.start();
			
			while (true) {
				if (connectionnumber < MAXIMUM_CONNECTIONS) {
					if (providerSocket == null || providerSocket.isClosed())
						providerSocket = new ServerSocket(21421, 10);
					Socket connection = providerSocket.accept();
					Traitement t = new Traitement(providerSocket, connection);
					t.start();
				} else {
					if (providerSocket != null)
						providerSocket.close();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static synchronized void insertmetrics() {
		try {
			BatchPoints batchPoints = BatchPoints.database(dbName).tag("async", "true").retentionPolicy("autogen")
					.consistency(ConsistencyLevel.ALL).build();
			Point point1 = Point.measurement("simulateurserveur").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
					.addField("serveurftp", "serveurftp").addField("value", Float.valueOf(getConnectionnumber()))
					.build();
			batchPoints.point(point1);
			influxDB.write(batchPoints);
			log.debug(point1.toString());
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}

	public synchronized static int getConnectionnumber() {
		return connectionnumber;
	}

	public synchronized static void setConnectionnumber(int connectionnumber) {
		Provider.connectionnumber = connectionnumber;
	}

}
