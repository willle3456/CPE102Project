import static org.junit.Assert.*;

import org.junit.Test;


public class TestEntities {

	@Test
	public void testSign() {
		int[] nums = {Entities.sign(-6.7), Entities.sign(0.0), Entities.sign(5.6)};
		assertTrue(nums[0]==-1);
		assertTrue(nums[1]==0);
		assertTrue(nums[2]==1);
		
	}
	
	@Test
	public void testEntityString() {
		String s = "Miner bob 0.0 0.0";
		String temp = Entities.entitySting(new MinerNotFull("bob", new Point(0,0),10,10,1));
		assertTrue(temp.equals(s));
	}
	
	@Test
	public void testGetAndSetPosition(){
		Entities e = new Vein("bob", new Point(0,0), 10, 5);
		assertTrue((e.getPosition().getX() == 0) && (e.getPosition().getY() == 0));
		e.setPosition(new Point (1,2));
		assertTrue((e.getPosition().getX() == 1) && (e.getPosition().getY() == 2));
	}
	
	@Test
	public void testName() {
		Entities e = new Ore("bobinho", new Point(0,0), 12);
		assertTrue(e.getName().equals("bobinho"));
	}
	

}
