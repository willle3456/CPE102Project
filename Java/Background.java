public class Background
    extends Entities
{
    private String[] imgs;
    private int current_img;
    
    public Background(String name, Point position, String[] imgs)
    {
        super(name, position);
        this.imgs = imgs;
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