class MinerNotFull
    extends Miner
{
    public MinerNotFull(String name, Point position, int animation_rate, int rate, int resource_limit)
    {
        super(name,position,animation_rate,rate,resource_limit);
    }
    
    public boolean minerToOre(WorldModel world, Ore ore)
    {
        Point entity_pt = this.getPosition();
        if(!(ore instanceof Ore))
        {
            return (false);
        }
        Point ore_pt = ore.getPosition();
        if( entity_pt.adjacent(ore_pt))
        {
            this.setResourceCount(1 + this.getResourceCount());
            world.removeEntity(ore);
            return (true);
        }
        else
        {
            Point new_pt = this.nextPosition(world, ore_pt);
            return (false);
        }
    }
            
    public Miner tryTransformMinerNotFull(WorldModel world) 
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

    public ? createMiner_not_full_action(WorldModel world, i_store)
    {
        public List<Point> action(int current_ticks)
        {
            this.removePendingAction(action);

            Point entity_pt = this.getPosition();
            Ore ore = world.findNearest(entity_pt, Ore);
            (List<Point> tiles, boolean found) = this.minerToOre(world, ore);

            Miner new_entity = this;
            if found
            {
                new_entity = this.tryTransformMiner(world, this.tryTransformMinerNotFull);
            }

            new_entity.scheduleAction(world,
                            new_entity.createMinerAction(world, i_store),
                            current_ticks + new_entity.getRate());
            return tiles;
        }

        return action;
    }

    public ? createMinerAction(WorldModel world, List<String> image_store)
    {
        return this.createMinerNotFullAction(world, image_store);
    }

    public void scheduleEntity(WorldModel world, List<String> i_store)
    {
        this.scheduleMiner(world, 0, i_store)
    }
}