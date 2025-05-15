import java.awt.image.BufferedImage;

//Daniel Song
//Program Description
//May 17, 2022

public class Animation
{
   private BufferedImage[] frames;
   private int currentFrameIndex;
   
   //Timer values
   private long startTime, delay;
   
   //No need for a constructor
   public Animation() {}
   
   public void setFrames(BufferedImage[] frames)
   {
      this.frames=frames;
      if(currentFrameIndex >= frames.length)
         currentFrameIndex = 0;
   }
   
   //Returns the current image to be used for the sprite
   public BufferedImage getImage()
   {
      return frames[currentFrameIndex];
   }
   
   public void update()
   {
      if(delay == -1) return;
      
      long elapsedTime = (System.nanoTime() - startTime) / 1000000;
      
      if(elapsedTime > delay) {
         currentFrameIndex++;
         if(currentFrameIndex == frames.length)
            currentFrameIndex = 0;
         startTime = System.nanoTime();

      }
         
   }
   
   public void setDelay(long delay)
   {
      this.delay = delay;
   }
}
