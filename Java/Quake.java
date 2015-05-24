import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import java.util.LinkedList;

import processing.core.*;

import java.util.HashMap;


public class Quake
    extends Animation
{
    private List<Object> pending_actions;
    private final int QUAKE_STEPS = 10;
    private final int QUAKE_DURATION = 1100;
    private final int QUAKE_ANIMATION_RATE = 100;
    
    public Quake(String name, Point position, List<PImage> imgs, int animation_rate)
    {
        super(name, position, imgs, animation_rate);
        this.pending_actions = new ArrayList<Object>();
    }
    
    public void scheduleEntity(WorldModel world, long ticks, HashMap<String, ArrayList<PImage>> iStore)
    {
        this.scheduleAnimation(world, QUAKE_STEPS);
        this.scheduleAction(world, this.createEntityDeathAction(world),
        ticks + QUAKE_DURATION);
    }
       
    public LongConsumer createEntityDeathAction(WorldModel world)
    {
       LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);
          Point pt = this.getPosition();
          world.removeEntity(this);
       };
       return action[0];
    }
    
    public long getRate()
    {
    	return 0;
    }

}