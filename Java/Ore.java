public class Ore
    extends Actions
{
    private int rate = 5000;
    
    public Ore(String name, Point position, int rate)
    {
        super(name, position);
        this.rate = rate;
    }
    
    public int getRate()
    {
        return this.rate;
    }

        
    public void scheduleAction(Worldmodel world, action, int time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
       
    public void scheduleOre(Worldmodel world, int ticks, List<String> i_store)
    {
       this.scheduleAction(world,
          this.createOreTransformAction(world, i_store),
          ticks + this.getRate());
    }
          
    public ? createOreTransformAction(Worldmodel world, List<String> i_store)
    {
       public List<Point> action(int current_ticks)
       {
          this.removePendingAction(action);
          Oreblob blob = world.createBlob(this.getName() + " -- blob",
             this.getPosition(),
             this.getRate() // BLOB_RATE_SCALE,
             current_ticks, i_store);

          world.removeEntity(this);
          world.addEntity(blob);

          return new List<Point>(this.getPosition());
       }
       return action;
    }
       
    public void scheduleEntity(Worldmodel world, List<String> i_store)
    {
        this.scheduleOre(world, 0, i_store);
    }        
}