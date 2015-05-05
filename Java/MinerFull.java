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
            
    /*public MinerNotFull tryTransformMinerFull(WorldModel world) 
    {    
        new_entity = new MinerNotFull(
            this.getName(), this.getPosition(),
                this.getAnimationRate(), this.getRate(),
                this.getImages(), this.getResourceLimit());

        return new_entity
    }

    def create_miner_full_action(self, world, i_store):
        def action(current_ticks):
            self.remove_pending_action(action)

            entity_pt = self.get_position()
            smith = world.find_nearest(entity_pt, Blacksmith)
            (tiles, found) = self.miner_to_smith(world,  smith)

            new_entity = self
            if found:
                new_entity = self.try_transform_miner(world, 
                                                      self.try_transform_miner_full)

            new_entity.schedule_action(world, 
                            new_entity.create_miner_action(world,  i_store),
                            current_ticks + new_entity.get_rate())
            return tiles

        return action



    def create_miner_action(self, world, image_store):
        return self.create_miner_full_action(world, image_store)*/
}