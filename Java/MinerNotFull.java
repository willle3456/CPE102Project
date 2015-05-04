class MinerNotFull
    extends Miner
{
    public MinerNotFull(String name, Point position, int animation_rate, int rate, int resource_limit)
    {
        super(name,position,animation_rate,rate,resource_limit);
    }
    
    def miner_to_ore(self, world, ore):
        entity_pt = self.get_position()
        if not ore:
            return ([entity_pt], False)
        ore_pt = ore.get_position()
        if entity_pt.adjacent(ore_pt):
            self.set_resource_count(1 + self.get_resource_count())
            world.remove_entity(ore)
            return ([ore_pt], True)
        else:
            new_pt = self.next_position(world, ore_pt)
            return (world.move_entity(self, new_pt), False)
            
    public Miner tryTransformMinerNotFull(WorldModel world) //getImages ****************************
    {
        if(this.getResourceCount < this.getResourceLimit)
        {
            return this;
        }
        else
        {
            new_entity = new MinerFull(
                this.getName(), this.getPosition(),
                this.getAnimationRate(), this.getRate(),
                this.getImages(), this.getResourceLimit());
        }
        return new_entity;
    }

    /*def create_miner_not_full_action(self, world, i_store):
        def action(current_ticks):
            self.remove_pending_action(action)

            entity_pt = self.get_position()
            ore = world.find_nearest(entity_pt, Ore)
            (tiles, found) = self.miner_to_ore(world, ore)

            new_entity = self
            if found:
                new_entity = self.try_transform_miner(world,
                                                      self.try_transform_miner_not_full)

            new_entity.schedule_action(world,
                            new_entity.create_miner_action(world, i_store),
                            current_ticks + new_entity.get_rate())
            return tiles

        return action

    def create_miner_action(self, world, image_store):
        return self.create_miner_not_full_action(world, image_store)

    def schedule_entity(self, world, i_store):
        self.schedule_miner(world, 0, i_store)*/
}