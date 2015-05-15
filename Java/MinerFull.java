public class MinerFull
    extends Miner
{
    public MinerFull(String name, Point position, int animation_rate, int rate, int resource_limit)
    {
        super(name,position,animation_rate,rate,resource_limit);
    }
   
    
    public boolean minerToSmith(WorldModel world, Blacksmith smith)
    {
        Point entity_pt = this.getPosition();
        if(!(smith instanceof Blacksmith))
        {
            return (false);
        }
        Point smith_pt = smith.getPosition();
        if( entity_pt.adjacent(smith_pt))
        {
            smith.setResourceCount(
                smith.getResourceCount() +
                this.getResourceCount());
            this.setResourceCount(0);
            return (true);
        }
        else
        {
            Point new_pt = this.nextPosition(world, smith_pt);
            return (false);
        }
    }
            
    public MinerNotFull tryTransformMinerFull(WorldModel world) 
    {    
        MinerNotFull new_entity = new MinerNotFull(
            this.getName(), this.getPosition(),
                this.getAnimationRate(), this.getRate(),
                this.getImages(), this.getResourceLimit());

        return new_entity;
    }

    public ? createMinerFullAction(WorldModel world, List<String> i_store)
    {
        public List<Point> action(int current_ticks)
        {
            this.removePendingAction(action);

            Point entity_pt = this.getPosition();
            Blacksmith smith = world.findNearest(entity_pt, Blacksmith)
            (List<Point> tiles, boolean found) = this.minerToSmith(world,  smith)

            MinerFull new_entity = this
            if(found)
            {
                new_entity = this.tryTransformMiner(world, 
                                                      this.tryTransformMinerFull);

            new_entity.scheduleAction(world, 
                            new_entity.createMinerAction(world,  i_store),
                            current_ticks + new_entity.getRate());
            }
            return tiles;
        }

        return action;



    public ? createMinerAction(WorldModel world, List<String> image_store)
    {
        return this.createMinerFullAction(world, image_store);
    }
}