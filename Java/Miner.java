import java.util.List;
import java.util.Function;

public class Miner
    extends Animation
{
    private int rate;
    private int resource_limit;
    private int current_img;
    private int resource_count;
    private List<String> pending_actions;
    
    public Miner(String name, Point position, List<String> imgs, int animation_rate, int rate, int resource_limit)
    {
        super(name,position, imgs, animation_rate);
        this.rate = rate;
        this.resource_limit = resource_limit;
        this.current_img = 0;
        this.resource_count = 0;
        this.pending_actions = new List<String>();
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

    public Entity tryTransformMiner(Worldmodel world, Object transform)
    {
        Entity new_entity = transform(world);
        if (this != new_entity)
        {
            world.clearPendingActions(this);
            world.removeEntityAt(this.getPosition());
            world.addEntity(new_entity);
            new_entity.scheduleAnimation(world);
        }

        return new_entity;
    }

    public void removeEntity(Worldmodel world)
    {
        for(Object action : this.getPendingActions())
        {
            world.unscheduleAction(action);
        }
        world.clearPendingActions(this);
        world.removeEntity(this);
    }

    public void scheduleMiner(Worldmodel world, int ticks, List<String> i_store)
    {
        this.scheduleAction(world, this.createMinerAction(world, i_store), ticks + this.getRate());
        this.scheduleAnimation(world);
    }
}