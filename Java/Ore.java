import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;

import java.util.LinkedList;

import processing.core.*;
import java.util.HashMap;

public class Ore
    extends Actions
{
    private long rate = 5000;
    private List<LongConsumer> pending_actions;
    
    public Ore(String name, Point position, List<PImage> imgs, long rate)
    {
        super(name, position, imgs);
        this.rate = rate;
        this.pending_actions = new ArrayList<LongConsumer>();
    }
    
    public long getRate()
    {
        return this.rate;
    }

        
    public void scheduleAction(WorldModel world, LongConsumer action, long time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
       
    public void scheduleOre(WorldModel world, long ticks, HashMap<String, ArrayList<PImage>> i_store)
    {
       this.scheduleAction(world,
          this.createOreTransformAction(world, i_store),
          ticks + this.getRate());
    }
          
    public LongConsumer createOreTransformAction(WorldModel world, HashMap<String, ArrayList<PImage>> i_store)
    {
       LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);
          
          OreBlob blob = world.createBlob(this.getName() + " -- blob",
             this.getPosition(),
             this.getRate(), // BLOB_RATE_SCALE
             current_ticks, i_store);

          world.removeEntity(this);
          world.addEntity(blob);
          
          List<Point> v = new ArrayList<Point>();

          //List<Point> v = new LinkedList<Point>();
       };
       return action[0];
    }
       
    public void scheduleEntity(WorldModel world,long ticks, HashMap<String,ArrayList<PImage>> i_store)
    {
        this.scheduleOre(world, 0, i_store);
    }        
}