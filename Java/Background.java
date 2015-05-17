import java.util.List;
import java.util.Function;

public class Background
    extends Entities
{
    private int current_img;
    
    public Background(String name, Point position, List<String> imgs)
    {
        super(name, position, imgs);
        this.current_img = 0;
    }
    
    public String[] getImages()
    {
        return this.imgs;
    }
    
    public String getImage()
    {
        return this.imgs[this.current_img];
    }

    
    public void nextImage()
    {
        this.current_img = (this.current_img + 1) % self.imgs.size();
    }
}