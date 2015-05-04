public class Background
{
    private String name;
    //private imgs
    
    public Background(String name)
    {
        this.name = name;
    }
    
    /*def get_images(self):
        return self.imgs

    def get_image(self):
        return self.imgs[self.current_img]*/

    public String getName()
    {
        return this.name;
    }
    
    /*def next_image(self):
        self.current_img = (self.current_img + 1) % len(self.imgs)*/
}