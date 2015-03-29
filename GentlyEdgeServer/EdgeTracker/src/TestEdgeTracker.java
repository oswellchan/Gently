import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestEdgeTracker {
	EdgeTracker edgeTracker = new EdgeTracker();
	String testFile = "testStats.xml";
	
	@Test
	public void testStreamkeyOne(){
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(testFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fstream)));
			assertEquals(edgeTracker.extractData(br).get(0).getStreamkey(), 480);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNviewersOne(){
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(testFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fstream)));
			assertEquals(edgeTracker.extractData(br).get(0).getNviewers(), 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStreamkeyTwo(){
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(testFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fstream)));
			assertEquals(edgeTracker.extractData(br).get(1).getStreamkey(), 1080);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNviewersTwo(){
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(testFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fstream)));
			assertEquals(edgeTracker.extractData(br).get(1).getNviewers(), 2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
