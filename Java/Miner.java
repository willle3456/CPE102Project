public class Miner
    extends Animation
{
    private int rate;
    private int resource_limit;
    private int current_img;
    private int resource_count;
    
    public Miner(String name, Point position, int animation_rate, int rate, int resource_limit)
    {
        super(name,position,animation_rate);
        this.rate = rate;
        this.resource_limit = resource_limit;
        this.current_img = 0;
        this.resource_count = 0;
    }
    
    public int getRate()
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

    /*def try_transform_miner(self, world, transform):
        new_entity = transform(world)
        if self != new_entity:
            world.clear_pending_actions(self)
            world.remove_entity_at(self.get_position())
            world.add_entity(new_entity)
            new_entity.schedule_animation(world)

        return new_entity

    def remove_entity(self, world):
        for action in self.get_pending_actions():
            world.unschedule_action(action)
        world.clear_pending_actions(self)
        world.remove_entity(self)

    def schedule_miner(self, world, ticks, i_store):
        self.schedule_action(world, self.create_miner_action(world, i_store), ticks + self.get_rate())
        self.schedule_animation(world)*/
}