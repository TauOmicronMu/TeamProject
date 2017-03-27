package tests.graphics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Pair {
	
	public main.Pair<String,String> pair;

	@Before
	public void setUp() throws Exception {
		pair = new main.Pair<String,String>("a","b");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPair() {
		main.Pair<Integer, Integer> pair = new main.Pair<Integer,Integer>(5,6);
	}

	@Test
	public void testGetX() {
		assertSame(pair.getX(), "a");
	}

	@Test
	public void testGetY() {
		assertSame(pair.getY(), "b");
		assertNotSame(pair.getY(), "a");
	}

	@Test
	public void testSetX() {
		pair.setX("e");
		assertSame(pair.getX(),"e");
		assertNotSame(pair.getX(),"a");
	}

	@Test
	public void testSetY() {
		pair.setY("g");
		assertSame(pair.getY(),"g");
		assertNotSame(pair.getY(),"b");
	}

}
