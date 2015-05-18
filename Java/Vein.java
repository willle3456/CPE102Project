import java.util.List;
import java.util.function.*;
import java.util.LinkedList;
public class Vein
    extends Actions
{
    private long rate;
    private int resource_distance = 1;
    private List<Object> pending_actions;
    
    public Vein(String name, Point position, List<PImage> imgs, long rate, int resource_distance)
    {
        super(name,position,imgs);
        this.rate = rate;
        this.resource_distance = resource_distance;
        this.pending_actions = new LinkedList<Object>();
    }
    
    public long getRate()
    {
        return this.rate;
    }
    
    public int getResourceDistance()
    {
        return this.resource_distance;
    }
       
    public void scheduleAction(WorldModel world, Object action, int time)
    {
        this.addPendingAction(action);
        world.scheduleAction(action, time);
    }
        
    public void scheduleVein(WorldModel world, int ticks, List<String> i_store)
    {
        this.scheduleAction(world, this.createVeinAction(world,i_store),
        ticks + this.getRate());
    }

    public Object createVeinAction(WorldModel world, List<String> i_store)
    {
       LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);
          List<Point> tiles = new LinkedList<Point>();
          
          Point open_pt = world.findOpenAround(this.getPosition(),
             this.getResourceDistance());
          if(open_pt != null)
          {
            Ore ore = world.createOre(
                "ore - " + this.getName() + " - " + current_ticks.toString(),
                open_pt, current_ticks, i_store);
             world.addEntity(ore);
             tiles.add(open_pt);
          }
          else
          {
             tiles = new LinkedList<Point>();
          }
          this.scheduleAction(world,
             this.createVeinAction(world, i_store),
             current_ticks + this.getRate());
          
       };
       return action;
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
       
    public void scheduleEntity(WorldModel world, List<String> i_store)
    {
        this.scheduleVein(world, 0, i_store);
    }
}