public class Animation
    extends Actions
{
    
    private int animation_rate;
    
    public Animation(String name, Point position, int animation_rate)
    {
        super(name,position);
        this.animation_rate = animation_rate;
    }
    
    public int getAnimationRate()
    {
        return this.animation_rate;
    }
    
    public void scheduleAnimation(Worldmodel world, int repeat_count=0)
    {
        this.schedule_action(world,
        this.createAnimationAction(world, repeat_count),
        this.getAnimationRate());
    }
        
    public void scheduleAction(Worldmodel world, action, int time)
    {
       this.addPendingAction(action);
       world.scheduleAction(action, time);
    }
       
    public ? createAnimationAction(Worldmodel world, int repeat_count)
    {
       public List<Integer> action(int current_ticks)
       {
          this.removePendingAction(action);

          this.nextImage();

          if (repeat_count != 1)
          {
             this.scheduleAction(world,
                this.createAnimationAction(world, Math.max(repeat_count - 1, 0)),
                current_ticks + this.getAnimationRate());
          }

          return [this.getPosition()]
       }
       return action
    }
}