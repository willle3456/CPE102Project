import java.util.List;
import java.util.Function;

public class Ore
    extends Actions
{
    private int rate = 5000;
    private List<String> pending_actions;
    
    public Ore(String name, Point position, List<String> imgs, int rate)
    {
        super(name, position, imgs);
        this.rate = rate;
        this.pending_actions = new List<String>();
    }
    
    public int getRate()
    {
        return this.rate;
    }

        
    public void scheduleAction(Worldmodel world, Object action, int time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
       
    public void scheduleOre(Worldmodel world, int ticks, List<String> i_store)
    {
       this.scheduleAction(world,
          this.createOreTransformAction(world, i_store),
          ticks + this.getRate());
    }
          
    public Object createOreTransformAction(Worldmodel world, List<String> i_store)
    {
       Function<Integer, List<Point>> action = (int current_ticks) ->
       //public List<Point> action(int current_ticks)
       {
          this.removePendingAction(Object);
          Oreblob blob = world.createBlob(this.getName() + " -- blob",
             this.getPosition(),
             this.getRate(), // BLOB_RATE_SCALE
             current_ticks, i_store);

          world.removeEntity(this);
          world.addEntity(blob);

          return new List<Point>(this.getPosition());
       };
       return action;
    }
       
    public void scheduleEntity(Worldmodel world, List<String> i_store)
    {
        this.scheduleOre(world, 0, i_store);
    }        
}