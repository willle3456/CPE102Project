import java.util.List;
import java.util.function.*;
import java.util.LinkedList;
public class Ore
    extends Actions
{
    private int rate = 5000;
    private List<Object> pending_actions;
    
    public Ore(String name, Point position, List<String> imgs, int rate)
    {
        super(name, position, imgs);
        this.rate = rate;
        this.pending_actions = new LinkedList<Object>();
    }
    
    public int getRate()
    {
        return this.rate;
    }

        
    public void scheduleAction(WorldModel world, Object action, int time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
       
    public void scheduleOre(WorldModel world, int ticks, List<String> i_store)
    {
       this.scheduleAction(world,
          this.createOreTransformAction(world, i_store),
          ticks + this.getRate());
    }
          
    public Object createOreTransformAction(WorldModel world, List<String> i_store)
    {
       Function<Integer, List<Point>> action = (current_ticks) ->
       //public List<Point> action(int current_ticks)
       {
          this.removePendingAction(action);
          Oreblob blob = world.createBlob(this.getName() + " -- blob",
             this.getPosition(),
             this.getRate(), // BLOB_RATE_SCALE
             current_ticks, i_store);

          world.removeEntity(this);
          world.addEntity(blob);
          
          List<Point> v = new LinkedList<Point>();
          return v.add(this.getPosition());
       };
       return action;
    }
       
    public void scheduleEntity(WorldModel world, List<String> i_store)
    {
        this.scheduleOre(world, 0, i_store);
    }        
}