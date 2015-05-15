public class Blacksmith
    extends Entities
{
    private int resource_limit;
    private int rate;
    private int resource_distance;
    private int resource_count;
    private int current_img;

    public Blacksmith(String name, Point position, int resource_limit, int rate, int resource_distance)
    {
        super(name, position);
        this.resource_limit = resource_limit;
        this.rate = rate;
        this.resource_distance = resource_distance;
        this.resource_count= 0;
        this.current_img = 0;
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

    public int getResourceDistance()
    {
        return this.resource_distance;
    }
    
    public void scheduleEntity(Worldmodel world, List<String> i_store)
    {
        this.schedule_(world, 0, i_store);
    }   
}