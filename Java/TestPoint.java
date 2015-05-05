import static org.junit.Assert.*;

import org.junit.Test;


public class TestPoint {

	@Test
	public void testGetAndSetX() {
		Point pt = new Point(0,0);
		assertTrue(pt.getX() == 0);
		pt.setX(2);
		assertTrue(pt.getX() == 2);
	}
	
	@Test
	public void testGetAndSetY() {
		Point pt = new Point(0,0);
		assertTrue(pt.getY() == 0);
		pt.setY(2);
		assertTrue(pt.getY() == 2);
	}
	
	@Test
	public void testDistance() {
		Point pt = new Point(0,0);
		Point pt1 = new Point(1,1);
		
		double temp = pt.distance(pt1);
		
		assertTrue(temp == Math.sqrt(2));
	}
	
	@Test
	public void testAdjcent() {
		Point pt = new Point(0,0);
		Point pt1 = new Point(0,1);
		
		assertTrue(pt.adjacent(pt1));
	}

}
