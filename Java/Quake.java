import java.util.List;
import java.util.function.*;
import java.util.LinkedList;


public class Quake
    extends Animation
{
    private List<Object> pending_actions;
    
    public Quake(String name, Point position, List<String> imgs, int animation_rate)
    {
        super(name, position, imgs, animation_rate);
        this.pending_actions = new LinkedList<Object>();
    }
    
    public void scheduleQake(WorldModel world, int ticks)
    {
        this.scheduleAnimation(world, QUAKE_STEPS);
        this.scheduleAction(world, this.createEntityDeathAction(world),
        ticks + QUAKE_DURATION);
    }
       
    public Object createEntityDeathAction(WorldModel world)
    {
       Function<Integer, List<Point>> action = (current_ticks) ->
       {
          this.removePendingAction(action);
          Point pt = this.getPosition();
          world.removeEntity(this);
          return new List<Point>(pt);
       };
       return action;
    }
}