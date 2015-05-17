import java.util.List;


public class TilesBool
{
    private List<Point> tiles;
    private boolean bool;
    
    public TilesBool(List<Point> tiles, boolean bool)
    {
        this.tiles = tiles;
        this. bool = bool;
    }
    
    public List<Point> getTiles()
    {
        return this.tiles;
    }
    
    public boolean getBool()
    {
        return this.bool;
    }
}