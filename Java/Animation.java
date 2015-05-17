import java.util.List;
import java.util.function.*;
import java.util.LinkedList;

public class Animation
    extends Actions
{
    
    private int animation_rate;
    
    public Animation(String name, Point position, List<String> imgs, int animation_rate)
    {
        super(name,position, imgs);
        this.animation_rate = animation_rate;
    }
    
    public int getAnimationRate()
    {
        return this.animation_rate;
    }
    
    public void scheduleAnimation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,
        this.createAnimationAction(world, repeat_count),
        this.getAnimationRate());
    }
        
    public void scheduleAction(WorldModel world, Object action, int time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
    public Object createAnimationAction(WorldModel world, int repeat_count)
    {
       Function<Integer, List<Point>> action = (int current_ticks) ->
       //public List<Point> action(int current_ticks)
       {
          this.removePendingAction(Object);

          this.nextImage();

          if (repeat_count != 1)
          {
             this.scheduleAction(world,
                this.createAnimationAction(world, Math.max(repeat_count - 1, 0)),
                current_ticks + this.getAnimationRate());
          }

          return List<Point>(this.getPosition());
       };
       return action;
    }
}