import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TestWorldModel {

	OCCGrid grid = new OCCGrid(100,100, new Background("bob", new Point(0,0)));
	OCCGrid occupy = new OCCGrid(100,100, new Ore("bobetta", new Point(1,1),10));
	WorldModel world = new WorldModel(100,100,grid,occupy);
	@Test
	public void testGetAndSetBackground() {
		Point pt = new Point(1,1);
		assertTrue(world.getBackground(pt) instanceof Background);
		Entities e = new Ore("a", new Point(0,1), 10);
		world.setBackgrounds(pt, e);
		assertTrue(world.getBackground(new Point(0,1)) instanceof Background);
	}

	@Test
	public void testGetTileOccupant()
	{
		assertTrue(world.getTileOccupant(new Point(0,0)) instanceof Ore);
	}
	@Test
	public void testNextPosition()
	{
		Point n = world.nextPosition(new Point(1,1), new Point(2,2));
		assertTrue(n.getX() == 1.0 && n.getY() == 1.0);
	}
	@Test
	public void testWithinBounds()
	{
		boolean temp = world.withinBounds(new Point (130,92));
		assertTrue(!temp);
	}
	@Test
	public void testIsOccupied()
	{
		boolean temp = world.isOccupied(new Point(50,50));
		assertTrue(temp);
	}
	@Test
	public void testFindNearestEntity()
	{
		Entities temp = world.findNearest(new Point(45,45), new Ore( "bobetta", new Point(1,1),10));
		assertTrue(temp.getName().equals("bobetta") && temp.getPosition().getX() == 1.0 && temp.getPosition().getY() == 1.0);
	}

	@Test
	public void testMoveEntity()
	{
		Point[] temp = world.moveEntity(new Miner("bob", new Point(0,0), 10, 10, 10), new Point(1,1));
		assertTrue(temp[0].getX() == 0.0 && temp[0].getY() == 0.0);
		assertTrue(temp[1].getX() == 1.0 && temp[1].getY() == 1.0);
	}
	@Test
	public void testAddEntity()
	{
		Entities e = new Ore( "bobetta", new Point(10,10),10);
		world.addEntity(e);
		assertTrue(world.getEntities().get(0).getName().equals("bobetta"));
	}
	@Test
	public void testRemoveEntityAt()
	{
		Entities e = new Ore( "bobetta", new Point(10,10),10);
		world.addEntity(e);
		Entities c = new Miner("joe", new Point(32, 23), 10, 10, 10);
		world.addEntity(c);
		world.addEntity(c);
		world.removeEntityAt(new Point(10,10));
		assertTrue(world.getTileOccupant(new Point(10,10)) == null);
	}
	@Test
	public void testRemoveEntity()
	{
		Entities e = new Ore( "bobetta", new Point(1,1),10);
		world.addEntity(e);
		world.removeEntity(new Ore("bobetta", new Point(1,1),10));
		assertTrue(world.getTileOccupant(new Point(1,1)) == null);
	}
	@Test
	public void testFindOpenAround()
	{
		Point temp = world.findOpenAround(new Point(5,5), 1.0);
		assertTrue(temp == null);
		
	}
	@Test
	public void testBlobNextPosition()
	{
		Point temp = world.blobNextPosition(new Point(4,4), new Point(1,1));
		assertTrue(temp.getX() == 3.0 && temp.getY() == 4.0);
	}

}
