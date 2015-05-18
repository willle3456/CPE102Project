import java.util.List;
import java.util.function.*;
import java.util.LinkedList;
import processing.core.*;

public class Blacksmith
    extends Actions
{
    private int resource_limit;
    private long rate;
    private int resource_distance;
    private int resource_count;
    private int current_img;
    private List<Object> pending_actions;

    public Blacksmith(String name, Point position, List<PImage> imgs, int resource_limit, long rate, int resource_distance)
    {
        super(name, position,imgs);
        this.resource_limit = resource_limit;
        this.rate = rate;
        this.resource_distance = resource_distance;
        this.resource_count= 0;
        this.current_img = 0;
        this.pending_actions = new LinkedList<Object>();
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

    public int getResourceDistance()
    {
        return this.resource_distance;
    }
    
    /*public void scheduleEntity(WorldModel world, List<String> i_store)
    {
        this.schedule_(world, 0, i_store);
    }   */
}