import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;


public class MMSWebRequestProcessorTest {

	/*-------------------------------------------------------------------------------------
	 * Description: serviceRequest(BufferedReader br, DataOutputStream outputStream) reads
	 * input from a BufferedReader and searches for all links specified by the input.
	 * Returns the links in a form of stream to the outputStream. Input specified by
	 * BufferedReader has to be in the form of "IP_ADDRESS USERID".
	 * Equivalence partitions: 
	 * 	br: [null], [inputs with valid form], [inputs w/o valid form]
	 * 	outputStream: [null], [not null]
	 * Inputs to test: ("192.0.0.1 randomString", not null), ("192.0.0.1 randomString" null), 
	 * (null, not null), ("randomString", not null), ("192.0.0.1 randomString hello", not null)
	 --------------------------------------------------------------------------------------*/

	//Inputs: ("192.0.0.1 randomString", not null)
	@Test
	public void testServiceRequest_00() {
		try {
			Class c = Class.forName("MMSWebRequestProcessor");
			Method method = c.getDeclaredMethod("serviceRequest", new Class[] { BufferedReader.class, DataOutputStream.class });
			Constructor constructor = c.getDeclaredConstructor(new Class[] { Socket.class });
			method.setAccessible(true);
			Socket s = null;
			Object i = constructor.newInstance(s);

			ByteArrayOutputStream os= new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			CharArrayReader charReader = new CharArrayReader("192.0.0.1 randomString\n".toCharArray());
			BufferedReader br = new BufferedReader(charReader);

			method.invoke(i, new Object[] { br, dos });
			String r = os.toString();
			assertEquals(r, "NULL\n");

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	//Inputs: ("192.0.0.1 randomString" null)
	@Test
	public void testServiceRequest_01() {
		try {
			Class c = Class.forName("MMSWebRequestProcessor");
			Method method = c.getDeclaredMethod("serviceRequest", new Class[] { BufferedReader.class, DataOutputStream.class });
			Constructor constructor = c.getDeclaredConstructor(new Class[] { Socket.class });
			method.setAccessible(true);
			Socket s = null;
			Object i = constructor.newInstance(s);

			ByteArrayOutputStream os= new ByteArrayOutputStream();
			DataOutputStream dos = null;

			CharArrayReader charReader = new CharArrayReader("192.0.0.1 randomString\n".toCharArray());
			BufferedReader br = new BufferedReader(charReader);

			try {
				method.invoke(i, new Object[] { br, dos });
				fail();
			} catch (Exception e) {
				assertEquals(e.getMessage(), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	//Inputs: (null, not null)
	@Test
	public void testServiceRequest_02() {
		try {
			Class c = Class.forName("MMSWebRequestProcessor");
			Method method = c.getDeclaredMethod("serviceRequest", new Class[] { BufferedReader.class, DataOutputStream.class });
			Constructor constructor = c.getDeclaredConstructor(new Class[] { Socket.class });
			method.setAccessible(true);
			Socket s = null;
			Object i = constructor.newInstance(s);

			ByteArrayOutputStream os= new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			CharArrayReader charReader = new CharArrayReader("192.0.0.1 randomString\n".toCharArray());
			BufferedReader br = null;

			try {
				method.invoke(i, new Object[] { br, dos });
				fail();
			} catch (Exception e) {
				assertEquals(e.getMessage(), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	//Inputs: ("randomString", not null)
	@Test
	public void testServiceRequest_03() {
		try {
			Class c = Class.forName("MMSWebRequestProcessor");
			Method method = c.getDeclaredMethod("serviceRequest", new Class[] { BufferedReader.class, DataOutputStream.class });
			Constructor constructor = c.getDeclaredConstructor(new Class[] { Socket.class });
			method.setAccessible(true);
			Socket s = null;
			Object i = constructor.newInstance(s);

			ByteArrayOutputStream os= new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			CharArrayReader charReader = new CharArrayReader("randomString\n".toCharArray());
			BufferedReader br = new BufferedReader(charReader);

			method.invoke(i, new Object[] { br, dos });
			String r = os.toString();

			Field field = c.getDeclaredField("MSG_INVALIDINPUT");
			field.setAccessible(true);
			Object r1 = field.get(field) + "\n";
			assertEquals(r, r1);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	//Inputs: ("192.0.0.1 randomString hello", not null)
	@Test
	public void testServiceRequest_04() {
		try {
			Class c = Class.forName("MMSWebRequestProcessor");
			Method method = c.getDeclaredMethod("serviceRequest", new Class[] { BufferedReader.class, DataOutputStream.class });
			Constructor constructor = c.getDeclaredConstructor(new Class[] { Socket.class });
			method.setAccessible(true);
			Socket s = null;
			Object i = constructor.newInstance(s);

			ByteArrayOutputStream os= new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(os);

			CharArrayReader charReader = new CharArrayReader("192.0.0.1 randomString hello\n".toCharArray());
			BufferedReader br = new BufferedReader(charReader);

			method.invoke(i, new Object[] { br, dos });
			String r = os.toString();

			Field field = c.getDeclaredField("MSG_INVALIDINPUT");
			field.setAccessible(true);
			Object r1 = field.get(field) + "\n";
			assertEquals(r, r1);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}
