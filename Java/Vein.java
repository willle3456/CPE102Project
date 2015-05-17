import java.util.List;
import java.util.Function;

public class Vein
    extends Actions
{
    private int rate;
    private int resource_distance = 1;
    private List<String> pending_actions;
    
    public Vein(String name, Point position, List<String> imgs, int rate, int resource_distance)
    {
        super(name,position,imgs);
        this.rate = rate;
        this.resource_distance = resource_distance;
        this.pending_actions = new List<String>();
    }
    
    public int getRate()
    {
        return this.rate;
    }
    
    public int getResourceDistance()
    {
        return this.resource_distance;
    }
       
    public void scheduleAction(Worldmodel world, Object action, int time)
    {
        this.addPendingAction(action);
        world.scheduleAction(action, time);
    }
        
    public void scheduleVein(Worldmodel world, int ticks, List<String> i_store)
    {
        this.scheduleAction(world, this.createVeinAction(world,i_store),
        ticks + this.getRate());
    }

    public Object createVeinAction(Worldmodel world, List<String> i_store)
    {
       Function<Integer, List<Point>> action = (int current_ticks) ->
       //public List<String> action(int current_ticks)
       {
          this.removePendingAction(Object);

          Point open_pt = world.findOpenAround(this.getPosition(),
             this.getResourceDistance());
          if(open_pt)
          {
            Ore ore = world.createOre(
                "ore - " + this.getName() + " - " + str(current_ticks),
                open_pt, current_ticks, i_store);
             world.addEntity(ore);
             List<Point> tiles = new List<Point>(open_pt);
          }
          else
          {
             List<Point> tiles = new List<Point>();
          }
          this.scheduleAction(world,
             this.createVeinAction(world, i_store),
             current_ticks + this.getRate());
          return tiles;
          
       };
       return action;
    }

    public void removeEntity(Worldmodel world)
    {
       for(Object action : this.getPendingActions())
       {
          world.unscheduleAction(action);
       }
       this.clearPendingActions();
       world.removeEntity(this);
    }
       
    public void scheduleEntity(Worldmodel world, List<String> i_store)
    {
        this.scheduleVein(world, 0, i_store);
    }
}