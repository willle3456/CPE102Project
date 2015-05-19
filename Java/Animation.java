import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import java.util.LinkedList;

import processing.core.*;

public class Animation
    extends Actions
{
    
    private int animation_rate;
    
    public Animation(String name, Point position, List<PImage> imgs, int animation_rate)
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
        this.scheduleAction(world,
        this.createAnimationAction(world, repeat_count),
        this.getAnimationRate());
    }
        
    public void scheduleAction(WorldModel world, Object action, long time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
    public LongConsumer createAnimationAction(WorldModel world, int repeat_count)
    {
       LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);

          this.nextImage();

          if (repeat_count != 1)
          {
             this.scheduleAction(world,
                this.createAnimationAction(world, Math.max(repeat_count - 1, 0)),
                current_ticks + this.getAnimationRate());
          }
       };
       return action[0];
    }
}