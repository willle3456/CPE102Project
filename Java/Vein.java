public class Vein
    extends Actions
{
    private int rate;
    private int resource_distance = 1;
    
    public Vein(String name, Point position, int rate, int resource_distance)
    {
        super(name,position);
        this.rate = rate;
        this.resource_distance = resource_distance;
    }
    
    public int getRate()
    {
        return this.rate;
    }
    
    public int getResourceDistance()
    {
        return this.resource_distance;
    }
       
    public void scheduleAction(Worldmodel world, action, int time)
    {
        this.addPendingAction(action);
        world.scheduleAction(action, int time);
    }
        
    public void scheduleVein(Worldmodel world, int ticks, List<String> i_store)
    {
        this.scheduleAction(world, this.createVeinAction(world,i_store),
        ticks + this.getRate());
    }

    public ? createVeinAction(Worldmodel world, List<String> i_store)
    {
       public List<String> action(int current_ticks)
       {
          this.removePendingAction(action);

          Point open_pt = world.findOpenAround(this.getPosition(),
             this.getResourceDistance());
          if(open_pt)
          {
            Ore ore = world.createOre(
                "ore - " + this.getName() + " - " + str(current_ticks),
                open_pt, current_ticks, i_store);
             world.addEntity(ore);
             List<Point> tiles = new List<Point>(open_pt);
          }
          else
          {
             List<Point> tiles = new List<Point>();
          }
          this.scheduleAction(world,
             this.createVeinAction(world, i_store),
             current_ticks + this.getRate());
          return tiles;
          
       }
       return action;
    }

    public void removeEntity(Worldmodel world)
    {
       for action in self.get_pending_actions()
       {
          world.unscheduleAction(action);
       }
       this.clearPendingActions();
       world.removeEntity(this);
    }
       
    public void scheduleEntity(Worldmodel world, List<String> i_store)
    {
        this.scheduleVein(world, 0, i_store);
    }
}