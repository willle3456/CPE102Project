public class Quake
    extends Animation
{
    public Quake(String name, Point position, int animation_rate)
    {
        super(name, position, animation_rate);
    }
    
    public void scheduleQake(Worldmodel world, int ticks)
    {
        this.scheduleAnimation(world, QUAKE_STEPS);
        this.scheduleAction(world, this.createEntityDeathAction(world),
        ticks + QUAKE_DURATION);
    }   
       
    public ? createEntityDeathAction(Worldmodel world)
    {
       public List<Point> action(int current_ticks)
       {
          this.removePendingAction(action);
          Point pt = this.getPosition();
          world.removeEntity(this);
          return List<Point>(pt);
       }
       return action;
    }
}