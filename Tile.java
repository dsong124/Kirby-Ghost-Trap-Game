import java.awt.image.BufferedImage;

//Daniel Song
//Program Description

public class Tile
{
   private BufferedImage image;
   private boolean blocked;
   
   public Tile(BufferedImage image,boolean blocked)
   {
      super();
      this.image=image;
      this.blocked=blocked;
   }
   
   public BufferedImage getImage()
   {
      return image;
   }
   
   public boolean isBlocked()
   {
      return blocked;
   }
}
