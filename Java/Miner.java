import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import java.util.LinkedList;

import processing.core.*;

import java.util.HashMap;

public abstract class Miner
    extends Animation
{
    private long rate;
    private int resource_limit;
    private int current_img;
    private int resource_count;
    private List<LongConsumer> pending_actions;
    
    abstract LongConsumer createMinerAction(WorldModel world, HashMap<String, ArrayList<PImage>> image_store);
    
    public Miner(String name, Point position, List<PImage> imgs, int animation_rate, long rate, int resource_limit)
    {
        super(name,position, imgs, animation_rate);
        this.rate = rate;
        this.resource_limit = resource_limit;
        this.current_img = 0;
        this.resource_count = 0;
        this.pending_actions = new ArrayList<LongConsumer>();
    }
    
    public long getRate()
    {
        return this.rate;
    }

    public void setResourceCount(int n)
    {
        this.resource_count = n;
    }

    public int getResourceCount()
    {
        return this.resource_count;
    }

    public int getResourceLimit()
    {
        return this.resource_limit;
    }

    public Point nextPosition(WorldModel world, Point dest_pt)
    {
        int horiz = Entities.sign(dest_pt.getX() - this.getPosition().getX());
        Point new_pt = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(new_pt))
        {
            int vert = Entities.sign(dest_pt.getY() - this.getPosition().getY());
            new_pt = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(new_pt))
            {
                new_pt = new Point(this.getPosition().getX(), this.getPosition().getY());
            }
        }

        return new_pt;
    }

    public Miner tryTransformMiner(WorldModel world, Function<WorldModel, Miner> transform)
    {
        Miner new_entity = transform.apply(world);
        if (this != new_entity)
        {
            world.clearPendingActions(this);
            world.removeEntityAt(this.getPosition());
            world.addEntity(new_entity);
            new_entity.scheduleAnimation(world,0);
        }

        return new_entity;
    }

    public void removeEntity(WorldModel world)
    {
        for(LongConsumer action : this.getPendingActions())
        {
            world.unscheduleAction(action);
        }
        world.clearPendingActions(this);
        world.removeEntity(this);
    }

    public void scheduleEntity(WorldModel world, long ticks, HashMap<String,ArrayList<PImage>> i_store)
    {
        this.scheduleAction(world, this.createMinerAction(world, i_store), ticks + this.getRate());
        this.scheduleAnimation(world,0);
    }
}