import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.ObservableList;


public class EdgeStub {
	
	EdgeStub() {

	}

	public static void sendRequest(String url, int port) throws Exception {

		Socket s = new Socket(url, port);
		
		EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
		edgeTransferObject.setServerName("oswell");
		edgeTransferObject.setCommand("INIT");
		ArrayList<Streamer> streams = new ArrayList<Streamer>();
		Streamer s1 = new Streamer();
		s1.setNviewers(8);
		s1.setPageurl("oswell.com/lol");
		s1.setStreamkey(1000001);
		streams.add(s1);
		
		Streamer s2 = new Streamer();
		s2.setNviewers(10);
		s2.setPageurl("oswell.com/lol2");
		s2.setStreamkey(1000002);
		streams.add(s2);
		
		edgeTransferObject.setStreamer(streams);
		OutputStream os = s.getOutputStream();
		ObjectOutputStream serverWriter = new ObjectOutputStream(os);
		
		serverWriter.writeObject(edgeTransferObject);

		s.close();
	}
	
	public static void sendRequest1(String url, int port) throws Exception {

		Socket s = new Socket(url, port);
		
		EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
		edgeTransferObject.setServerName("oswell");
		edgeTransferObject.setCommand("UPDATE");
		ArrayList<Streamer> streams = new ArrayList<Streamer>();
		Streamer s1 = new Streamer();
		s1.setNviewers(11);
		s1.setPageurl("oswell.com/lol");
		s1.setStreamkey(1000001);
		streams.add(s1);
		
		Streamer s2 = new Streamer();
		s2.setNviewers(23);
		s2.setPageurl("oswell.com/lol2");
		s2.setStreamkey(1000002);
		streams.add(s2);
		
		edgeTransferObject.setStreamer(streams);
		OutputStream os = s.getOutputStream();
		ObjectOutputStream serverWriter = new ObjectOutputStream(os);
		
		serverWriter.writeObject(edgeTransferObject);

		s.close();
	}
	
	public static void sendRequest2(String url, int port) throws Exception {

		Socket s = new Socket(url, port);
		
		EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
		edgeTransferObject.setServerName("oswell2");
		edgeTransferObject.setCommand("INIT");
		ArrayList<Streamer> streams = new ArrayList<Streamer>();
		Streamer s1 = new Streamer();
		s1.setNviewers(22);
		s1.setPageurl("oswell2.com/lol");
		s1.setStreamkey(1000001);
		streams.add(s1);
		
		Streamer s2 = new Streamer();
		s2.setNviewers(34);
		s2.setPageurl("oswell2.com/lol2");
		s2.setStreamkey(1000002);
		streams.add(s2);
		
		edgeTransferObject.setStreamer(streams);
		OutputStream os = s.getOutputStream();
		ObjectOutputStream serverWriter = new ObjectOutputStream(os);
		
		serverWriter.writeObject(edgeTransferObject);

		s.close();
	}
	
	public static void sendRequest3(String url, int port) throws Exception {

		Socket s = new Socket(url, port);
		
		EdgeServerTransferObject edgeTransferObject = new EdgeServerTransferObject();
		edgeTransferObject.setServerName("oswell2");
		edgeTransferObject.setCommand("UPDATE");
		ArrayList<Streamer> streams = new ArrayList<Streamer>();
		Streamer s1 = new Streamer();
		s1.setNviewers(30);
		s1.setPageurl("oswell2.com/lol");
		s1.setStreamkey(1000001);
		streams.add(s1);
		
		edgeTransferObject.setStreamer(streams);
		OutputStream os = s.getOutputStream();
		ObjectOutputStream serverWriter = new ObjectOutputStream(os);
		
		serverWriter.writeObject(edgeTransferObject);

		s.close();
	}
	
	public static void main(String[] args) {
		try {
			//sendRequest("localhost", 9002);
			sendRequest1("localhost", 9002);
			//sendRequest2("localhost", 9002);
			//sendRequest3("localhost", 9002);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
