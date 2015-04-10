import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;


public class InternalMemoryTest {

	/*-------------------------------------------------------------------------------------
	 * Description: getStreamSourcesByID(String userID) takes in userID and returns a String
	 * with all links tagged to the userID, separated by a space. If a null or non-existing
	 * userID is given, "" is returned
	 * Equivalence partitions: 
	 * 	userID: [null], [any other string]
	 * 	_streamerToStreamSourcesMap: empty, userID exists
	 * Inputs to test: (userID exists, "randomString"), (empty, "randomString"), 
	 * (userID exists, null)
	 --------------------------------------------------------------------------------------*/

	//Input: (userID exists, "randomString")
	@Test
	public void testGetStreamSourcesByID_00() {
		try {
			Class c = Class.forName("InternalMemory");
			Method method = c.getDeclaredMethod("getStreamSourcesByID", new Class[] { String.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			ConcurrentHashMap<String, ArrayList<String>> map = new ConcurrentHashMap<String, ArrayList<String>>();
			ArrayList<String> links = new ArrayList<String>();
			links.add("a");
			links.add("b");
			links.add("c");
			map.put("randomString", links);

			Field field = c.getDeclaredField("_streamerToStreamSourcesMap");
			field.setAccessible(true);
			field.set(i, map);

			Object r = method.invoke(i, new Object[] { "randomString" });
			assertEquals(r, "a b c ");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	//Input: (empty, "randomString")
	@Test
	public void testGetStreamSourcesByID_01() {
		try {
			Class c = Class.forName("InternalMemory");
			Method method = c.getDeclaredMethod("getStreamSourcesByID", new Class[] { String.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			ConcurrentHashMap<String, ArrayList<String>> map = new ConcurrentHashMap<String, ArrayList<String>>();

			Field field = c.getDeclaredField("_streamerToStreamSourcesMap");
			field.setAccessible(true);
			field.set(i, map);

			Object r = method.invoke(i, new Object[] { "randomString" });
			assertEquals(r, "NULL");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	
	//Input: (userID exists, null)
	@Test
	public void testGetStreamSourcesByID_02() {
		try {
			Class c = Class.forName("InternalMemory");
			Method method = c.getDeclaredMethod("getStreamSourcesByID", new Class[] { String.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			ConcurrentHashMap<String, ArrayList<String>> map = new ConcurrentHashMap<String, ArrayList<String>>();

			Field field = c.getDeclaredField("_streamerToStreamSourcesMap");
			field.setAccessible(true);
			field.set(i, map);

			Object r = method.invoke(i, new Object[] { null });
			assertEquals(r, "NULL");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	
	

}
