import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap.*;

import processing.core.*;

abstract public class Actions
    extends Entities
{
    
    private List<LongConsumer> pending_actions;
    
    public Actions(String name, Point position, List<PImage> imgs)
    {
        super(name, position, imgs);
        this.pending_actions = new ArrayList<LongConsumer>();
    }
    
    public List<LongConsumer> getPendingActions()
    {
        if(this instanceof Actions)
        {
            return this.pending_actions;
        }
        else
        {
            return new ArrayList<LongConsumer>();
        }
    }

    public void removePendingAction(LongConsumer action)
    {
        if(this instanceof Actions)
        {
            this.pending_actions.remove(action);
        }
    }

    public void addPendingAction(LongConsumer action)
    {
        if(this instanceof Actions)
        {
            this.pending_actions.add(action);
        }
    }

    public void clearPendingActions()
    {
        if(this instanceof Actions)
        {
            this.pending_actions = new ArrayList<LongConsumer>();
        }
    }

	abstract public void scheduleEntity(WorldModel world,long ticks, HashMap<String, ArrayList<PImage>> iStore);
	abstract public long getRate();
	
}