package tests.graphics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Circle {
	
	main.Circle circle;
	main.Window window;
	

	@Before
	public void setUp() throws Exception {
		circle = new main.Circle();
		
		
	}

	@After
	public void tearDown() throws Exception {
		
		
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	@Test
	public void test() {
		double[] answer1 = new double[] {0.7,
				0.5,
				0.2,
				0.6414213562373096,
				0.6414213562373094,
				0.2,
				0.5,
				0.7,
				0.2,
				0.3585786437626905,
				0.6414213562373096,
				0.2,
				0.3,
				0.5,
				0.2,
				0.35857864376269044,
				0.3585786437626905,
				0.2,
				0.49999999999999994,
				0.3,
				0.2,
				0.6414213562373094,
				0.35857864376269044,
				0.2,
				0.7,
				0.49999999999999994,
				0.2};
		assertArrayEquals(answer1, circle.createCircle(0.5, 0.5, 0.2, 0.2), 0);
		assertNotEquals(answer1[0],circle.createCircle(0.5, 0.5, 0.2, 0.3)[0] , 0);
		assertEquals(answer1[2], circle.createCircle(0.3, 0.6, 0.2, 0.9)[2],0);
		
		
		assertArrayEquals(circle.createCircle(0.5, 0.5, 0.2, 0.2,8), circle.createCircle(0.5, 0.5, 0.2, 0.2), 0);
		assertNotEquals(circle.createCircle(0.5, 0.5, 0.2, 0.2,6).length, circle.createCircle(0.5, 0.5, 0.2, 0.2).length, 0);
		
	}

}
