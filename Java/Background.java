import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import processing.core.*;
import java.util.HashMap;

public class Background
    extends Entities
{
    private int current_img;
    
    public Background(String name, Point position, List<PImage> imgs)
    {
        super(name, position, imgs);
        this.current_img = 0;
    }
    
    public List<PImage> getImages()
    {
        return this.imgs;
    }
    
    public PImage getImage()
    {
        return this.imgs.get(this.current_img);
    }

    
    public void nextImage()
    {
        this.current_img = (this.current_img + 1) % this.imgs.size();
    }
}