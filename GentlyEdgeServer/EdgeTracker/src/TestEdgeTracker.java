import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
 *  Functions in EdgeTracker.java are tested in the following order:
 *  - isViewerIncrement(),
 *  - getString(),
 *  - extractData(),
 *  - statsDownloader(),
 *  - trackStatus()
 */

public class TestEdgeTracker {
	private static final String EXTRACTDATA = "extractData";
	private static final String GETSTRING = "getString";
	private static final String STATSDOWNLOADER = "statsDownloader";
	private static final String ISVIEWERINCREMENT = "isViewerIncrement";
	private static final String EDGETRACKER = "EdgeTracker";
	
	@Test
	public void testIsViewerIncrementHasClientNoPublishing(){
		String test = "<client>";
		int nviewer = 0;
		Object result = makeIsViewerIncrementPublic(test, nviewer);
		assertEquals(result, 1);
	}
	
	@Test
	public void testIsViewerIncrementHasClientHasPublishing(){
		String test = "<client>publishing";
		int nviewer = 0;
		Object result = makeIsViewerIncrementPublic(test, nviewer);
		assertEquals(result, 0);
	}
	
	@Test
	public void testIsViewerIncrementNoClientHasPublishing(){
		String test = "publishing";
		int nviewer = 0;
		Object result = makeIsViewerIncrementPublic(test, nviewer);
		assertEquals(result, 0);
	}
	
	@Test
	public void testIsViewerIncrementEmptyString(){
		String test = "";
		int nviewer = 0;
		Object result = makeIsViewerIncrementPublic(test, nviewer);
		assertEquals(result, 0);
	}
	
	@Ignore
	private Object makeIsViewerIncrementPublic(String test, int nviewer) {
		Object result = null;
		try {
			Class edgeTracker = Class.forName(EDGETRACKER);
			Method publicIsViewerIncrement = edgeTracker.getDeclaredMethod(ISVIEWERINCREMENT, new Class[] {String.class, int.class});
			publicIsViewerIncrement.setAccessible(true);
			Object i = edgeTracker.newInstance();
			result = publicIsViewerIncrement.invoke(i, new Object[] {test, nviewer});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e){
			e.printStackTrace();
		} catch (InstantiationException e){
			e.printStackTrace();
		} catch (IllegalArgumentException | InvocationTargetException e){
			e.printStackTrace();
		} 
		return result;
	}
	
	@Test
	public void testGetStringWithPageURL(){
		String test = "<pageurl>http://localhost/</pageurl>";
		String findUsingThis = "<pageurl>";
		Object result = makeGetStringPublic(test, findUsingThis);
		assertEquals(result, "http://localhost/");
	}
	
	@Test
	public void testGetStringWithQ(){
		String test = "<pageurl>http://localhost/</pageurl>";
		String findUsingThis = "q";
		Object result = makeGetStringPublic(test, findUsingThis);
		assertEquals(result, "");
	}
	
	@Test
	public void testGetStringWithBothEmpty(){
		String test = "";
		String findUsingThis = "";
		Object result = makeGetStringPublic(test, findUsingThis);
		assertEquals(result, "");
	}
	
	@Test
	public void testGetStringWithTestEmpty(){
		String test = "";
		String findUsingThis = "q";
		Object result = makeGetStringPublic(test, findUsingThis);
		assertEquals(result, "");
	}
	
	@Test
	public void testGetStringWithFindUsingThisEmpty(){
		String test = "q";
		String findUsingThis = "";
		Object result = makeGetStringPublic(test, findUsingThis);
		assertEquals(result, "");
	}
	
	@Ignore
	private Object makeGetStringPublic(String test, String findUsingThis){
		Object result = null;
		try {
			Class edgeTracker = Class.forName(EDGETRACKER);
			Method publicIsViewerIncrement = edgeTracker.getDeclaredMethod(GETSTRING, new Class[] {String.class, String.class});
			publicIsViewerIncrement.setAccessible(true);
			Object i = edgeTracker.newInstance();
			result = publicIsViewerIncrement.invoke(i, new Object[] {test, findUsingThis});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e){
			e.printStackTrace();
		} catch (InstantiationException e){
			e.printStackTrace();
		} catch (IllegalArgumentException | InvocationTargetException e){
			e.printStackTrace();
		} 
		return result;
	}

	@Test
	public void testExtractDataBrContainsEmptyString(){
		String contains = "";
		BufferedReader br = new BufferedReader(new StringReader(contains));
		Object result = makeExtractDataPublic(br);
		assertEquals(((ArrayList<Streamer>) result).size(), 0);
	}
	
	@Test
	public void testExtractDataBrContainsEndStream(){
		String contains = "</stream>";
		BufferedReader br = new BufferedReader(new StringReader(contains));
		Object result = makeExtractDataPublic(br);
		assertEquals(((ArrayList<Streamer>) result).size(), 1);
	}
	
	@Test
	public void testExtractDataBrContainsStream(){
		String contains = "<stream>\n<name>480</name>\n</stream>";
		BufferedReader br = new BufferedReader(new StringReader(contains));
		Object result = makeExtractDataPublic(br);
		assertEquals(((ArrayList<Streamer>) result).get(0).getStreamkey(), 480);
	}
	
	@Test
	public void testExtractDataBrContainsPageUrl(){
		String contains = "<pageurl>http://localhost/</pageurl>\n</stream>";
		BufferedReader br = new BufferedReader(new StringReader(contains));
		Object result = makeExtractDataPublic(br);
		assertEquals(((ArrayList<Streamer>) result).get(0).getPageurl(), "http://localhost/");
	}
	
	@Ignore
	public void testExtractDataBrNull(){
		String contains = "";
		BufferedReader br = null;
		Object result = makeExtractDataPublic(br);
		assertEquals((ArrayList<Streamer>) result, null);
	}
	
	@Ignore
	private Object makeExtractDataPublic(BufferedReader br){
		Object result = null;
		try {
			Class edgeTracker = Class.forName(EDGETRACKER);
			Method publicExtractData = edgeTracker.getDeclaredMethod(EXTRACTDATA, new Class[] {BufferedReader.class});
			publicExtractData.setAccessible(true);
			Object i = edgeTracker.newInstance();
			result = publicExtractData.invoke(i, new Object[] {br});
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e){
			e.printStackTrace();
		} catch (InstantiationException e){
			e.printStackTrace();
		} catch (IllegalArgumentException | InvocationTargetException e){
			e.printStackTrace();
		} 
		return result;
	}
	
	//PreCondition: Needs to be able to connect to localhost
		@Test
		public void testStatsDownloader(){
			try {
				Class edgeTracker = Class.forName(EDGETRACKER);
				Method publicIsViewerIncrement = edgeTracker.getDeclaredMethod(STATSDOWNLOADER, new Class[] {InputStream.class, BufferedReader.class});
				publicIsViewerIncrement.setAccessible(true);
				Object i = edgeTracker.newInstance();
				Object result = publicIsViewerIncrement.invoke(i, new Object[] {null , null});
				assertEquals(((BufferedReader) result).readLine(), "<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e){
				e.printStackTrace();
			} catch (InstantiationException e){
				e.printStackTrace();
			} catch (IllegalArgumentException | InvocationTargetException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	@Test
	public void testTrackStatus(){
		EdgeTracker edgeTracker = new EdgeTracker();
		assertEquals(edgeTracker.trackStatus().getServerName(), "PLACEHOLDER NAME");
	}
	
}
