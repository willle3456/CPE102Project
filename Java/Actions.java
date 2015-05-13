public class Actions
    extends Entities
{
    public Actions(String name, Point position /* imgs */ )
    {
        super(name, position /* imgs */);
    }
    
    public List<Entities> GetPendingActions()
    {
        if hasattr("pending_actions")
        {
            return this.pending_actions;
        }
        else
        {
            return <>;
        }
    }

    public void removePendingAction(action)
    {
        if hasattr("pending_actions")
        {
            this.pending_actions.remove(action);
        }
    }

    public void addPendingAction(action)
    {
        if hasattr("pending_actions")
        {
            this.pending_actions.add(action);
        }
    }

    public void clearPendingActions()
    {
        if hasattr(self, "pending_actions")
        {
            this.pending_actions = <>;
        }
    }
}