import java.util.List;
import java.util.Function;



public class Quake
    extends Animation
{
    private List<String> pending_actions;
    
    public Quake(String name, Point position, List<String> imgs, int animation_rate)
    {
        super(name, position, imgs, animation_rate);
        this.pending_actions = new List<String>();
    }
    
    public void scheduleQake(Worldmodel world, int ticks)
    {
        this.scheduleAnimation(world, QUAKE_STEPS);
        this.scheduleAction(world, this.createEntityDeathAction(world),
        ticks + QUAKE_DURATION);
    }
       
    public Object createEntityDeathAction(Worldmodel world)
    {
       Function<Integer, List<Point>> action = (int current_ticks) ->
       {
          this.removePendingAction(action);
          Point pt = this.getPosition();
          world.removeEntity(this);
          return new List<Point>(pt);
       };
       return action;
    }
}