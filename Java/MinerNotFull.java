import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;

import processing.core.*;

import java.util.HashMap;

public class MinerNotFull
    extends Miner
{
    public MinerNotFull(String name, Point position, List<PImage> images, int animation_rate, long rate, int resource_limit)
    {
        super(name,position,images,animation_rate,rate,resource_limit);
    }
    
    public boolean minerToOre(WorldModel world, Entities ore)
    {
        Point entity_pt = this.getPosition();
       // ArrayList<Point> tiles = new ArrayList<Point>();
        
        if(!(ore instanceof Ore))
        {
            return false;
        	//tiles.add(entity_pt);
            //return new TilesBool(tiles, false);
        }
        Point ore_pt = ore.getPosition();
        if( entity_pt.adjacent(ore_pt))
        {
            this.setResourceCount(1 + this.getResourceCount());
            world.removeEntity(ore);
            return true;
           // tiles.clear();
           // tiles.add(ore_pt);
           // return new TilesBool(tiles,true);
        }
        else
        {
            Point new_pt = this.nextPosition(world, ore_pt);
            world.moveEntity(this, new_pt);
            System.out.println(this.getPosition().getX());
            System.out.println(this.getPosition().getY());
            

           // tiles.clear();
            //return new TilesBool(tiles,false);
            return false;
        }
    }
            
    public Miner tryTransformMinerNotFull(WorldModel world) 
    {
        if(this.getResourceCount() < this.getResourceLimit())
        {
            return this;
        }
        else
        {
            MinerFull new_entity = new MinerFull(
                this.getName(), this.getPosition(),this.getImages(),
                this.getAnimationRate(), this.getRate(),
                 this.getResourceLimit());
        
            return new_entity;
        }
    }


    public LongConsumer createMinerNotFullAction(WorldModel world, HashMap<String, ArrayList<PImage>> i_store)
    {
        LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);

            Point entity_pt = this.getPosition();
            Entities ore = world.findNearest(entity_pt, Ore.class);
            boolean tiles_found = this.minerToOre(world, ore);

            Miner new_entity = this;
            if(tiles_found)
            {
                new_entity = this.tryTransformMiner(world, this::tryTransformMinerNotFull);
            }

            new_entity.scheduleAction(world,
                            new_entity.createMinerAction(world, i_store),
                            current_ticks + new_entity.getRate());
        };

        return action[0];
    }


    public LongConsumer createMinerAction(WorldModel world, HashMap<String, ArrayList<PImage>> image_store)
    {
        return this.createMinerNotFullAction(world, image_store);
    }


    
}