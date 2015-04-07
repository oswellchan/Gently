import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;


public class GUIControllerTest {

	/*-------------------------------------------------------------------------------------
	 * Description: convertToDoubleDigits converts all single digit int to double digits in
	 * String. Any int that has more than 1 digit will not be changed
	 * Equivalence partitions: [MIN_INT ... -1], [0 - 9], [10 - MAX_INT]
	 * Inputs to test: MIN_INT, MIN_INT + 1, -1, 0, 1, 8, 9, 10, 11, MAX_INT - 1, MAX_INT
	 --------------------------------------------------------------------------------------*/

	//Input: MIN_INT
	@Test
	public void testConvertToDoubleDigits_MIN_INT() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { Integer.MIN_VALUE });
			assertEquals(r, "" + Integer.MIN_VALUE);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MIN_INT + 1
	@Test
	public void testConvertToDoubleDigits_MIN_INT_plus1() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { Integer.MIN_VALUE  + 1});
			assertEquals(r, (Integer.MIN_VALUE + 1) + "");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: -1
	@Test
	public void testConvertToDoubleDigits_minus1() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { -1 });
			assertEquals(r, -1 + "");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: 0
	@Test
	public void testConvertToDoubleDigits_0() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { 0 });
			assertEquals(r, "00");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: 1
	@Test
	public void testConvertToDoubleDigits_1() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { 1 });
			assertEquals(r, "01");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: 8
	@Test
	public void testConvertToDoubleDigits_8() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { 8 });
			assertEquals(r, "08");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: 9
	@Test
	public void testConvertToDoubleDigits_9() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { 9 });
			assertEquals(r, "09");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: 10
	@Test
	public void testConvertToDoubleDigits_10() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { 10 });
			assertEquals(r, "10");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: 11
	@Test
	public void testConvertToDoubleDigits_11() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { 11 });
			assertEquals(r, "11");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MAX_INT - 1
	@Test
	public void testConvertToDoubleDigits_MAX_INT_minus1() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { Integer.MAX_VALUE - 1 });
			assertEquals(r, (Integer.MAX_VALUE - 1 ) + "");

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MAX_INT
	@Test
	public void testConvertToDoubleDigits_MAX_INT() {
		try {
			Class c = Class.forName("GUIController");
			Method method = c.getDeclaredMethod("convertToDoubleDigits", new Class[] { int.class });
			method.setAccessible(true);
			Object i = c.newInstance();

			Object r = method.invoke(i, new Object[] { Integer.MAX_VALUE });
			assertEquals(r, Integer.MAX_VALUE  + "");

		} catch (Exception e) {
			fail();
		}

	}

	/*-------------------------------------------------------------------------------------
	 * Description: parsePort converts a valid port number in String into int. Valid port
	 * numbers are between the range as specified by MINSERVERPORT and MAXSERVERPORT. An
	 * invalid port will return a value of -1.
	 * Equivalence partitions: [MINSERVERPORT ... MAXSERVERPORT], [null], [any other string]
	 * Inputs to test: MINSERVERPORT - 1, MINSERVERPORT, MINSERVERPORT + 1, MAXSERVERPORT -1,
	 * MAXSERVERPORT, MAXSERVERPORT + 1, null, "randomString", "*&^%$"
	 --------------------------------------------------------------------------------------*/

	//Input: MINSERVERPORT - 1
	@Test
	public void testConvertToDoubleDigits_MINSERVERPORT_minus1() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);
			Field f = c.getDeclaredField("MINSERVERPORT");
			f.setAccessible(true);

			int value = f.getInt(f) - 1;

			Object r = method.invoke(i, new Object[] { "" + value });
			assertEquals(r, -1);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MINSERVERPORT
	@Test
	public void testConvertToDoubleDigits_MINSERVERPORT() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);
			Field f = c.getDeclaredField("MINSERVERPORT");
			f.setAccessible(true);

			int value = f.getInt(f);

			Object r = method.invoke(i, new Object[] { "" + value });
			assertEquals(r, value);

		} catch (Exception e) {
			fail();
		}

	}


	//Input: MINSERVERPORT + 1
	@Test
	public void testConvertToDoubleDigits_MINSERVERPORT_plus1() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);
			Field f = c.getDeclaredField("MINSERVERPORT");
			f.setAccessible(true);

			int value = f.getInt(f) + 1;

			Object r = method.invoke(i, new Object[] { "" + value });
			assertEquals(r, value);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MAXSERVERPORT - 1
	@Test
	public void testConvertToDoubleDigits_MAXSERVERPORT_minus1() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);
			Field f = c.getDeclaredField("MAXSERVERPORT");
			f.setAccessible(true);

			int value = f.getInt(f) - 1;

			Object r = method.invoke(i, new Object[] { "" + value });
			assertEquals(r, value);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MAXSERVERPORT
	@Test
	public void testConvertToDoubleDigits_MAXSERVERPORT() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);
			Field f = c.getDeclaredField("MAXSERVERPORT");
			f.setAccessible(true);

			int value = f.getInt(f);

			Object r = method.invoke(i, new Object[] { "" + value });
			assertEquals(r, value);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: MAXSERVERPORT + 1
	@Test
	public void testConvertToDoubleDigits_MAXSERVERPORT_plus1() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);
			Field f = c.getDeclaredField("MAXSERVERPORT");
			f.setAccessible(true);

			int value = f.getInt(f) + 1;

			Object r = method.invoke(i, new Object[] { "" + value });
			assertEquals(r, -1);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: null
	@Test
	public void testConvertToDoubleDigits_MAXSERVERPORT_null() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);

			Object r = method.invoke(i, new Object[] { null });
			assertEquals(r, -1);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: "randomString"
	@Test
	public void testConvertToDoubleDigits_MAXSERVERPORT_randomString() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);

			Object r = method.invoke(i, new Object[] { "randomString" });
			assertEquals(r, -1);

		} catch (Exception e) {
			fail();
		}

	}

	//Input: "*&^%$"
	@Test
	public void testConvertToDoubleDigits_MAXSERVERPORT_specialSymbols() {
		try {
			Class c = Class.forName("GUIController");
			Object i = c.newInstance();
			Method method = c.getDeclaredMethod("parsePort", new Class[] { String.class });
			method.setAccessible(true);

			Object r = method.invoke(i, new Object[] { "*&^%$" });
			assertEquals(r, -1);

		} catch (Exception e) {
			fail();
		}

	}

	
}
