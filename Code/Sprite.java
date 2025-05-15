import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Daniel Song
//Program Description: Sprite class for a side-scrolling platform game


public class Sprite
{
   private double x, y, dx, dy;
   private int width, height;
   private boolean left, right, jumping, falling;
   
   private double moveSpeed, stopSpeed, maxSpeed; //values for horizontal movement
   private double jumpSpeed, gravity, maxFallingSpeed; //values for vertical movement
   
   private BufferedImage[] idleSprite, walkingSprites, jumpingSprite, fallingSprite, ghostSprite;
 //  private BufferedImage[] powerup;
   private boolean facingLeft;
   
   private Animation animation;
   
   private TileMap tileMap;
   private boolean topLeft, topRight, bottomRight, bottomLeft; //true if corners are locked

   
   public Sprite(TileMap tileMap)
   {
      this.tileMap=tileMap;
      width = 22;
      height = 22;
      
      //Initial values for horizontal movement
      moveSpeed = 0.5; //acceleration factor
      stopSpeed = 0.5; //deceleration factor
      maxSpeed = 1.2; //top speed
      
      //Initial values for vertical movement
      jumpSpeed = -4.5;
      gravity = 0.1;
      maxFallingSpeed = 12;
      //food.add(new Food(200, 200, 25, 25, Color.red));  
      try
      {
//         powerup = new BufferedImage[1];
//         powerup[0] = ImageIO.read(new File("src/powerup.png"));
         ghostSprite = new BufferedImage[1];
         ghostSprite[0] = ImageIO.read(new File("src/ghost2.gif"));
         idleSprite = new BufferedImage[1];//storing one thing
         idleSprite[0] = ImageIO.read(new File("src/kirbyidle.gif"));
         jumpingSprite = new BufferedImage[1];//storing one thing
         jumpingSprite[0] = ImageIO.read(new File("src/kirbyjump.gif"));
         fallingSprite = new BufferedImage[1];//storing one thing
         fallingSprite[0] = ImageIO.read(new File("src/kirbyfall.gif"));
         
         walkingSprites = new BufferedImage[6]; //sprite sheet
         //Read in from a sprite sheet
         BufferedImage image = ImageIO.read(new File("src/kirbywalk.gif"));
         for(int i = 0; i < walkingSprites.length; i++) {
            walkingSprites[i] = image.getSubimage(
                  i*width + i,
                  0,
                  width,
                  height
                  );
         
         }
      } catch(IOException e) {e.printStackTrace();}
      
      
      //initializing the animation object
      animation = new Animation();
   }
   
   public void update() // movement, imagery, updates, animation
   {
      //HORIZONTAL MOVEMENT
      if(left) {
         dx -= moveSpeed; // this is acceleration
         if(dx < -maxSpeed) // top speed to the left
            dx = -maxSpeed;
      }
      else if(right) {
         dx += moveSpeed; // this is acceleration
         if(dx > maxSpeed) // top speed to the right
            dx = maxSpeed;
      }
      else {
         if(dx > 0) { //slow to a stop if moving right
            dx -=stopSpeed; // this is deceleration
            if(dx < 0)
               dx = 0;
         }
       else if(dx < 0) { //slow to a stop if moving left
               dx += stopSpeed; // this is deceleration
            if(dx > 0)
               dx = 0;
         
         }
      }     
      //VERTICAL MOVEMENT
      if(jumping) {
         dy = jumpSpeed; // initial jump power
         jumping = false;
         falling = true;
      }
      else if(falling) {
         dy += gravity; //acceleration due to gravity
         if(dy > maxFallingSpeed) //check terminal velocity
            dy = maxFallingSpeed;
      }
      else {
         dy = 0;
         
      }
      
      //check for map collisions
      adjustForTileMapCollisions();
      /***************************************/
      //move the map 
      tileMap.setX((int)(SpriteTester.PREF_W / 2 - x));
      tileMap.setY((int)(SpriteTester.PREF_H / 2 - y));
      
      
      //set the direction sprite is facing
      if(dx<0)
         facingLeft=true;
      else if(dx>0)
         facingLeft=false;
      
      //Set the animation... Update the image based on the sprite's properties
      if(left || right) {
         animation.setFrames(walkingSprites);
         animation.setDelay(100);
      }
      else {
         animation.setFrames(idleSprite);///////////////////////////
       //  animation.setFrames(powerup);
         animation.setDelay(-1);
      }
      
      if(dy < 0) { //if moving up
         animation.setFrames(jumpingSprite);
         animation.setDelay(-1);
      }
      if(dy > 0) { //if moving down
         animation.setFrames(fallingSprite);
         animation.setDelay(-1);
      }
         
      //Update the animation timer so the values can check for a costume change
      animation.update();
      
   }
   public void updateEnemy() // movement, imagery, updates, animation
   {
      //HORIZONTAL MOVEMENT
      if(left) {
         dx -= moveSpeed; // this is acceleration
         if(dx < -maxSpeed) // top speed to the left
            dx = -maxSpeed;
      }
      else if(right) {
         dx += moveSpeed; // this is acceleration
         if(dx > maxSpeed) // top speed to the right
            dx = maxSpeed;
      }
      else {
         if(dx > 0) { //slow to a stop if moving right
            dx -=stopSpeed; // this is deceleration
            if(dx < 0)
               dx = 0;
         }
         else if(dx < 0) { //slow to a stop if moving left
            dx += stopSpeed; // this is deceleration
            if(dx > 0)
               dx = 0;
            
         }
      }
     
      
      
      //check for map collisions
      adjustForTileMapCollisions();
      
      
//      if(topLeft || topRight || bottomLeft || bottomRight)
//         dx = -dx;

  //    enemy collisions with tiles
         if(left && (topLeft || bottomLeft))
         {
            left = false;
            right = true;
         }
         if(right && (topRight || bottomRight))
         {
            left = true;
            right = false;
         }
         
     
      
      
      
      //VERTICAL MOVEMENT
      if(jumping) {
         dy = jumpSpeed; // initial jump power
         jumping = false;
         falling = true;
      }
      else if(falling) {
         dy += gravity; //acceleration due to gravity
         if(dy > maxFallingSpeed) //check terminal velocity
            dy = maxFallingSpeed;
      }
      else {
         dy = 0;
         
      }
      
      /***************************************/
      //move the map 
//      tileMap.setX((int)(SpriteTester.PREF_W / 2 - x));
//      tileMap.setY((int)(SpriteTester.PREF_H / 2 - y));
      
      
      
      //update the location
//      x += dx;        >>>>>>>>>>>>>>>>>>  FAKE COLLISIONS when Kirby needed a ground
//      y += dy;
//      
//      if(y >= GROUND_LEVEL) {
//         falling = false;
//         y = GROUND_LEVEL;
//      }
      
      //set the direction sprite is facing
      if(dx<0)
         facingLeft=true;
      else if(dx>0)
         facingLeft=false;
      
      //Set the animation... Update the image based on the sprite's properties
      if(left || right) {
         animation.setFrames(ghostSprite);
         animation.setDelay(100);
      }
      else {
         animation.setFrames(ghostSprite);///////////////////////////
         //  animation.setFrames(powerup);
         animation.setDelay(-1);
      }
      
      if(dy < 0) { //if moving up
         animation.setFrames(ghostSprite);
         animation.setDelay(-1);
      }
      if(dy > 0) { //if moving down
         animation.setFrames(ghostSprite);
         animation.setDelay(-1);
      }
      
      //Update the animation timer so the values can check for a costume change
      animation.update();
      
   }
   
   
   //this medthod will adjust the x and y values of the sprite based on map collisions
   public void adjustForTileMapCollisions()  //Part 11T 13:00
   {
      int currRow = tileMap.getRowTile((int) y); //kirby knows where his center is, his x, y 
      int currCol = tileMap.getColTile((int) x);
      
      //where is kirby trying to go
      double tox = x + dx;
      double toy = y + dy;
      
      //store the location of kirby (altering the values)
      double tempx = x;
      double tempy = y;
      
      //Check and adjust for map collisions if moving up or down
      calculateCornerCollisions(x, toy); //use the currently calculated y-value to see if you can move to a new location
      //check for blocked tiles above if moving up
      if(dy < 0) {
         if(topLeft || topRight) {
            dy = 0;
            tempy = currRow * tileMap.getTileSize() + height / 2;
         }
         else {   
            //if either of the top corners is unblocked, stop moving up and  adjust the y-value to the closest unblocked y-value
            tempy += dy;
         }
      }
      else if(dy > 0) { //if moving down, check for blocked tile below
         if(bottomLeft || bottomRight) {
            dy = 0;
            falling = false; //just collided with the floor
            tempy = (currRow + 1) * tileMap.getTileSize() - height / 2; //landed on top of the tile below you
         }
         else {   //if bottom is unblocked, then move down
            tempy += dy;
         }
      }
      else { //check if you "walk-off" a tile and should fall
         calculateCornerCollisions(x, y + 1);
         if(!bottomLeft && !bottomRight) { //if bottom left and bottom right is unblocked
            falling = true;
         }
         
      }
      /********************************************************/
      //Check and adjust for map collisions if left or right
      calculateCornerCollisions(tox, y); //use the currently calculated x-value to see if you can move to a new location
     
      if(dx < 0) { //if moving left, adjust of either is blocked
         if(topLeft || bottomLeft) {
            dx = 0;
            tempx = (currCol) * tileMap.getTileSize() + width / 2;
         }
         else { //if nothing blocking to the left, move left
            tempx += dx;
         }
      }
         else if(dx > 0) { //if moving right, adjust of either is blocked
         if(topRight || bottomRight) {
            dx = 0;
            tempx = (currCol+1) * tileMap.getTileSize() - width / 2;
         }
         else { //if nothing blocking to the right, move right
            tempx += dx;
         }
      }
   
      
      
      
      //update the sprite location based on the map collision
      x = tempx; //moves left or right temporarily
      y = tempy;
   }
   

   private void calculateCornerCollisions(double x, double y) //every corner
   {
      //lower left corner of where to go
      //based on my left location, which column I am in
      int leftCol = tileMap.getColTile((int)(x - width / 2));
      int rightCol = tileMap.getColTile((int)(x + width / 2) - 1);
      int topRow = tileMap.getRowTile((int)(y - height / 2));//bottom of its feet is 
      int bottomRow = tileMap.getRowTile((int)(y + height / 2) - 1);//bottom of its feet is //actually one above the one thats right on your feet
      
      topLeft = tileMap.isBlocked(topRow,leftCol);//bottom left corner foot
      topRight = tileMap.isBlocked(topRow,rightCol);//bottom left corner foot
      bottomLeft = tileMap.isBlocked(bottomRow,leftCol);//bottom left corner foot
      bottomRight = tileMap.isBlocked(bottomRow,rightCol);//bottom left corner foot
   }
   
   public void draw(Graphics2D g2)
   {
//      g2.setColor(Color.RED);
//      g2.fillRect((int)x-width/2,(int)y-height/2, width, height);
      
      int tx = tileMap.getX();
      int ty = tileMap.getY();
      g2.drawImage(animation.getImage(),0,0,width,height,null);
      if(facingLeft)
          g2.drawImage(
                     animation.getImage(),
                     (int) (tx + x-width/2), // x is the horizontal center of the sprite
                     (int) (ty + y-height/2),// y is the the vertical center of the sprite
                     width, 
                     height, 
                     null);
         
      else  // sprite is facing to the right
         g2.drawImage(
               animation.getImage(),
               (int) (tx + x-width/2+width), // x is the horizontal center of the sprite
               (int) (ty + y-height/2),// y is the the vertical center of the sprite
               -width,  // negatives flip the image
               height, 
               null);
      
   }
  // public boolean intersects(Food food)
   {     
  //    return walkingSprites.length.intersects(food);
   }
   
//   public boolean intersects(Food food)
//   {     
//      return idleSprite.getRectangle().intersects(food.getRectangle());
//   }
   
   public double getX()
   {
      return x;
   }
   public void setX(double x)
   {
      this.x=x;
   }
   public double getY()
   {
      return y;
   }
   public void setY(double y)
   {
      this.y=y;
   }
   public double getDx()
   {
      return dx;
   }
   public void setDx(double dx)
   {
      this.dx=dx;
   }
   public double getDy()
   {
      return dy;
   }
   public void setDy(double dy)
   {
      this.dy=dy;
   }
   public boolean isLeft()
   {
      return left;
   }
   public void setLeft(boolean left)
   {
      this.left=left;
   }
   public boolean isRight()
   {
      return right;
   }
   public void setRight(boolean right)
   {
      this.right=right;
   }
   public boolean isJumping()
   {
      return jumping;
   }
   public void setJumping(boolean jumping)
   {
      //avoid the double jump
      if(!falling)
         this.jumping=true;
   }

   public boolean intersects(Food food2)
   {
      return true;
   }
   
   public Rectangle2D.Double getBounds()
   {
      return new Rectangle2D.Double(x, y, width, height);
   }

   public boolean keyDetected(int keyCode)
   {
      return false;
   }
   
}
