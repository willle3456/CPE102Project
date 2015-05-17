import java.util.List;
import java.util.LinkedList;
import java.util.function.*;
public class MinerFull
    extends Miner
{
    public MinerFull(String name, Point position, List<String> imgs, int animation_rate, int rate, int resource_limit)
    {
        super(name,position,imgs,animation_rate,rate,resource_limit);
    }
   
    
    public TilesBool minerToSmith(WorldModel world, Blacksmith smith)
    {
        Point entity_pt = this.getPosition();
        LinkedList<Point> tiles = new LinkedList<Point>();
        
        if(!(smith instanceof Blacksmith))
        {
            tiles.add(entity_pt);
            return new TilesBool(tiles, false);
        }
        Point smith_pt = smith.getPosition();
        if( entity_pt.adjacent(smith_pt))
        {
            smith.setResourceCount(
                smith.getResourceCount() +
                this.getResourceCount());
            this.setResourceCount(0);
            tiles.clear();
            return new TilesBool(tiles,true);
        }
        else
        {
            Point new_pt = this.nextPosition(world, smith_pt);
            tiles.add(world.moveEntity(this, new_pt))
            return new TilesBool(tiles ,false);
        }
    }
            
    public MinerNotFull tryTransformMinerFull(WorldModel world) 
    {    
        MinerNotFull new_entity = new MinerNotFull(
            this.getName(), this.getPosition(),this.getImages(),
                this.getAnimationRate(), this.getRate(),
                 this.getResourceLimit());

        return new_entity;
    }

    public Object createMinerFullAction(WorldModel world, List<String> i_store)
    {
        Function<Integer, List<Point>> action = (current_ticks) ->
        //public List<Point> action(int current_ticks)
        {
            this.removePendingAction(action);

            Point entity_pt = this.getPosition();
            Blacksmith smith = world.findNearest(entity_pt, Blacksmith);
            TilesBool tiles_found = this.minerToSmith(world, smith);

            Miner new_entity = this;
            if(tiles_found.getBool())
            {
                new_entity = this.tryTransformMiner(world, 
                                                      this::tryTransformMinerFull);

            new_entity.scheduleAction(world, 
                            new_entity.createMinerAction(world,  i_store),
                            current_ticks + new_entity.getRate());
            }
            return tiles_found.getTiles();
        };

        return action;
    }



    public Object createMinerAction(WorldModel world, List<String> image_store)
    {
        return this.createMinerFullAction(world, image_store);
    }
}