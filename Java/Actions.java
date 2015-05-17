import java.util.List;
import java.util.Function;

public class Actions
    extends Entities
{
    public Actions(String name, Point position, List<String> imgs)
    {
        super(name, position, imgs);
    }
    
    public List<Object> GetPendingActions()
    {
        if hasattr("pending_actions")
        {
            return this.pending_actions;
        }
        else
        {
            return new List<Object>();
        }
    }

    public void removePendingAction(Object action)
    {
        if hasattr("pending_actions")
        {
            this.pending_actions.remove(action);
        }
    }

    public void addPendingAction(Object action)
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
            this.pending_actions = new List<Object>();
        }
    }
}