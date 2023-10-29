import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy 
{
    
    private double x, y, dx, dy;
    private int width, height;
    
    private boolean left, right;
    private double moveSpeed, stopSpeed, maxSpeed;
    private BufferedImage[] enemySprites;
    private Animation AnimationForEnemy;
    
    public Enemy()
    {
        width = 22;
        height = 22;
        
        moveSpeed = 0.5;
        stopSpeed = 0.5;
        maxSpeed = 2;
        
        try {
            enemySprites = new BufferedImage[1];
            enemySprites[0] = ImageIO.read(new File("src/ghost2.gif"));
        } catch(IOException e) {e.printStackTrace();}
        
        AnimationForEnemy = new Animation();
    }
    
    public void draw(Graphics2D g2)
    {
       g2.drawImage(AnimationForEnemy.getImage(), (int)x, (int)y, width, height, null);
       
    }
    
    public void update()
    {
       AnimationForEnemy.setFrames(enemySprites);
       AnimationForEnemy.setDelay(-1);
       AnimationForEnemy.update();
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
