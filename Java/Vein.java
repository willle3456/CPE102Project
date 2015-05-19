import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import processing.core.*;
import java.util.HashMap;

public class Vein
    extends Actions
{
    private long rate;
    private int resource_distance = 1;
    private List<Object> pending_actions;
    
    public Vein(String name, Point position, ArrayList<PImage> images, long rate, int resource_distance)
    {
        super(name,position,images);
        this.rate = rate;
        this.resource_distance = resource_distance;
        this.pending_actions = new ArrayList<Object>();

    }
    
    public Vein(String name, Point position, ArrayList<PImage> images, long rate)
    {
        super(name,position,images);
        this.rate = rate;
        this.resource_distance = 1;
        this.pending_actions = new ArrayList<Object>();
    }
    
    public long getRate()
    {
        return this.rate;
    }
    
    public int getResourceDistance()
    {
        return this.resource_distance;
    }
       
    public void scheduleAction(WorldModel world, Object action, long time)
    {
        this.addPendingAction(action);
        world.scheduleAction(action, time);
    }
        
    public void scheduleVein(WorldModel world, long ticks, HashMap<String, ArrayList<PImage>> i_store)
    {
        this.scheduleAction(world, this.createVeinAction(world,i_store),
        ticks + this.getRate());
    }

    public Object createVeinAction(WorldModel world, HashMap<String, ArrayList<PImage>> i_store)
    {
       LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);
          List<Point> tiles = new ArrayList<Point>();
          
          Point open_pt = world.findOpenAround(this.getPosition(),
             this.getResourceDistance());
          if(open_pt != null)
          {
            Ore ore = world.createOre(
                "ore - " + this.getName() + " - " + current_ticks,
                open_pt, current_ticks, i_store);
             world.addEntity(ore);
            // tiles.add(open_pt);
          }
          else
          {
             tiles = new ArrayList<Point>();
             //tiles = new ArrayList<Point>();
          }
          this.scheduleAction(world,
             this.createVeinAction(world, i_store),
             current_ticks + this.getRate());
          
       };
       return action[0];
    }

    public void removeEntity(WorldModel world)
    {
       for(Object action : this.getPendingActions())
       {
          world.unscheduleAction(action);
       }
       this.clearPendingActions();
       world.removeEntity(this);
    }
       
    public void scheduleEntity(WorldModel world, HashMap<String, ArrayList<PImage>> i_store)
    {
        this.scheduleVein(world, 0, i_store);
    }
}