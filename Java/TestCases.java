import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;

public class TestCases
{
    private static final double DELTA = 0.00001;
    //Entities----------------------------------------------------------------------------------
    @Test
    public void testSign1()
    {
        double x = -10;
        assertEquals(-1, Entities.sign(x), DELTA);
    }
    
    @Test
    public void testSign2()
    {
        double x = 0;
        assertEquals(0, Entities.sign(x), DELTA);
    }
    
    @Test
    public void testSign3()
    {
        double x = 10;
        assertEquals(1, Entities.sign(x), DELTA);
    }
    
    @Test 
    public void testName()
    {
        Blacksmith b = new Blacksmith("bob", new Point(1.0,2.0), 2, 1000, 5);
        assertEquals("bob", b.getName());
    }
    
    @Test 
    public void testgetPositionBS()
    {
        Blacksmith b = new Blacksmith("bob", new Point(1.0,2.0), 2, 1000, 5);
        assertEquals(1.0, b.getPosition().getX(), DELTA);
        assertEquals(2.0, b.getPosition().getY(), DELTA);
    }
    //Blacksmith------------------------------------------------------------------------------------
    @Test
    public void testgetRateBS()
    {
        Blacksmith b = new Blacksmith("bob", new Point(1.0,2.0), 2, 1000, 5);
        assertEquals(1000, b.getRate(), DELTA);
    }
    @Test
    public void testResourceCount()
    {
        Blacksmith b = new Blacksmith("bob", new Point(1.0,2.0), 2, 1000, 5);
        assertEquals(0, b.getResourceCount(), DELTA);
        b.setResourceCount(5);
        assertEquals(5, b.getResourceCount(), DELTA);
    }
    @Test
    public void testResourceLimit()
    {
        Blacksmith b = new Blacksmith("bob", new Point(1.0,2.0), 2, 1000, 5);
        assertEquals(2, b.getResourceLimit(), DELTA);
    }
    @Test
    public void testResourceDist()
    {
        Blacksmith b = new Blacksmith("bob", new Point(1.0,2.0), 2, 1000, 5);
        assertEquals(5, b.getResourceDistance(), DELTA);
    }
    //Vein------------------------------------------------------------------------------------------------------
    @Test
    public void testgetRateVein()
    {
        Vein v = new Vein("vlad", new Point(1.0,2.0), 2, 5);
        assertEquals(2, v.getRate(), DELTA);
    }
    
    @Test
    public void testgetdist()
    {
        Vein v = new Vein("vlad", new Point(1.0,2.0), 2, 5);
        assertEquals(5, v.getResourceDistance(), DELTA);
    }
    //Ore-----------------------------------------------------------------------------------------------------------
    @Test
    public void testgetRateOre()
    {
        Ore o = new Ore("vlad", new Point(1.0,2.0), 5000);
        assertEquals(5000, o.getRate(), DELTA);
    }
    //Animation-------------------------------------------------------------------------------------------------
    @Test
    public void testgetAnimationRate()
    {
        Animation a = new Animation("andy", new Point(1.0,2.0), 1000);
        assertEquals(1000, a.getAnimationRate(), DELTA);
    }
    //OreBlob-----------------------------------------------------------------------------------------------------
    @Test
    public void testGetRateOreblob()
    {
        OreBlob o = new OreBlob("Olly", new Point(1.0, 2.0), 1000, 500);
        assertEquals(500, o.getRate());
    }
    @Test
    public void testBlobToVein()
    {
        OreBlob blob = new OreBlob("molly", new Point(1.0,2.0), 1000, 5);
        OCCGrid background = new OCCGrid(2,2, new Background("backy", new Point(0,0)));
        OCCGrid occupancy = new OCCGrid(2,2, new Obstacle("ob", new Point(1.0,1.0)));
        WorldModel world = new WorldModel(2, 2, background, occupancy);
        Vein v = new Vein("vlad", new Point(2.0,2.0), 2, 5);
        assertTrue(blob.blobToVein(world, v));
    }
    //Miner-------------------------------------------------------------------------------------------------------
    @Test
    public void testgetRateMiner()
    {
        Miner m = new Miner("molly", new Point(1.0,2.0), 1000, 5, 2);
        assertEquals(5, m.getRate(), DELTA);
    }
    
    @Test
    public void testResourceCountMiner()
    {
        Miner m = new Miner("molly", new Point(1.0,2.0), 1000, 5, 2);
        assertEquals(0, m.getResourceCount());
        m.setResourceCount(100);
        assertEquals(100, m.getResourceCount(), DELTA);
    }
    
    @Test
    public void testgetresourceLimitMiner()
    {
        Miner m = new Miner("molly", new Point(1.0,2.0), 1000, 5, 2);
        assertEquals(2, m.getResourceLimit(), DELTA);
    }
    
    @Test
    public void testNextPositionMiner()
    {
        Miner m = new Miner("molly", new Point(1.0,2.0), 1000, 5, 2);
        OCCGrid background = new OCCGrid(2,2, new Background("backy", new Point(0,0)));
        OCCGrid occupancy = new OCCGrid(2,2, new Obstacle("ob", new Point(1.0,1.0)));
        WorldModel world = new WorldModel(2, 2, background, occupancy);
        Point newpoint = m.nextPosition(world, new Point(2.0, 2.0));
        assertEquals(2.0 , newpoint.getX(), DELTA);
        assertEquals(2.0, newpoint.getY(), DELTA);
    }
    //MinerNotFull-------------------------------------------------------------------------------------------------
    @Test
    public void testMinerToOre1()
    {
        MinerNotFull m = new MinerNotFull("molly", new Point(1.0,2.0), 1000, 5, 2);
        OCCGrid background = new OCCGrid(2,2, new Background("backy", new Point(0,0)));
        OCCGrid occupancy = new OCCGrid(2,2, new Obstacle("ob", new Point(1.0,1.0)));
        WorldModel world = new WorldModel(2, 2, background, occupancy);
        Ore o = new Ore("vlad", new Point(2.0,2.0), 5000);
        assertTrue(m.minerToOre(world, o));
    }
        
    @Test
    public void testMinerToOre2()
    {
        MinerNotFull m = new MinerNotFull("molly", new Point(1.0,2.0), 1000, 5, 2);
        OCCGrid background = new OCCGrid(2,2, new Background("backy", new Point(0,0)));
        OCCGrid occupancy = new OCCGrid(2,2, new Obstacle("ob", new Point(1.0,1.0)));
        WorldModel world = new WorldModel(2, 2, background, occupancy);
        Ore o = new Ore("vlad", new Point(1.0,2.0), 5000);
        assertFalse(m.minerToOre(world, o));
    }
    //MinerFull-----------------------------------------------------------------------------------------------------------------
    @Test
    public void testMinerToSmith1()
    {
        MinerFull m = new MinerFull("molly", new Point(1.0,2.0), 1000, 5, 2);
        OCCGrid background = new OCCGrid(2,2, new Background("backy", new Point(0,0)));
        OCCGrid occupancy = new OCCGrid(2,2, new Obstacle("ob", new Point(1.0,1.0)));
        WorldModel world = new WorldModel(2, 2, background, occupancy);
        Blacksmith b = new Blacksmith("bob", new Point(2.0,2.0), 2, 1000, 5);
        assertTrue(m.minerToSmith(world, b));
    }

   /* public void testMinerToSmith2()
    {
        MinerFull m = new MinerFull("molly", new Point(1.0,2.0), 1000, 5, 2);
        OCCGrid background = new OCCGrid(2,2, new Background("backy", new Point(0,0)));
        OCCGrid occupancy = new OCCGrid(2,2, new Obstacle("ob", new Point(1.0,1.0)));
        WorldModel world = new WorldModel(2, 2, background, occupancy);
        Blacksmith b = new Blacksmith("bob", new Point(2.0,2.0), 2, 1000, 5);
        assertTrue(m.minerToSmith(world, m));
    }*/
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    