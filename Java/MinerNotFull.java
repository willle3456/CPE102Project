import java.util.List;
import java.util.function.*;
import java.util.LinkedList;
import processing.core.*;

class MinerNotFull
    extends Miner
{
    public MinerNotFull(String name, Point position, List<PImage> imgs, int animation_rate, long rate, int resource_limit)
    {
        super(name,position,imgs,animation_rate,rate,resource_limit);
    }
    
    public TilesBool minerToOre(WorldModel world, Ore ore)
    {
        Point entity_pt = this.getPosition();
        if(!(ore instanceof Ore))
        {
            return TilesBool(new LinkedList<Point>(entity_pt), false);
        }
        Point ore_pt = ore.getPosition();
        if( entity_pt.adjacent(ore_pt))
        {
            this.setResourceCount(1 + this.getResourceCount());
            world.removeEntity(ore);
            return TilesBool(new LinkedList<Point>(ore_pt),true);
        }
        else
        {
            Point new_pt = this.nextPosition(world, ore_pt);
            return TilesBool(new LinkedList<Point>(world.moveEntity(this, new_pt)),false);
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

    public Object createMinerNotFullAction(WorldModel world, List<String> i_store)
    {
        LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);

            Point entity_pt = this.getPosition();
            Ore ore = world.findNearest(entity_pt, Ore);
            TilesBool tiles_found = this.minerToOre(world, ore);

            Miner new_entity = this;
            if(tiles_found.getBool())
            {
                new_entity = this.tryTransformMiner(world, this::tryTransformMinerNotFull);
            }

            new_entity.scheduleAction(world,
                            new_entity.createMinerAction(world, i_store),
                            current_ticks + new_entity.getRate());
        };

        return action;
    }

    public Object createMinerAction(WorldModel world, List<String> image_store)
    {
        return this.createMinerNotFullAction(world, image_store);
    }

    public void scheduleEntity(WorldModel world, List<String> i_store)
    {
        this.scheduleMiner(world, 0, i_store);
    }
}