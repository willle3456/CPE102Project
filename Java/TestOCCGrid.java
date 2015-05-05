import static org.junit.Assert.*;

import org.junit.Test;


public class TestOCCGrid {

	@Test
	public void testGetAndSetCell() {
		Ore o1 = new Ore("bob",new Point(0,5), 10);
		Ore o2 = new Ore("yung jebuzus ",new Point(1,1), 3);
		OCCGrid grid = new OCCGrid(100,100,o1);
		assertTrue(grid.getCell(new Point(2,2)).equals(o1));
		grid.setCell(new Point(1,1), o2);
		assertTrue(grid.getCell(new Point(1,1)).equals(o2));
	}
}
