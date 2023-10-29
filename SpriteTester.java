import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SpriteTester extends JPanel implements KeyListener
{
   private static final long serialVersionUID = 1L;
   //Define the panel dimensions
   public static final int PREF_W = 300;
   public static final int PREF_H = 400;
   //Rendering hints are for smoothing the draw if needed
   private RenderingHints hints = new RenderingHints(
                                      RenderingHints.KEY_ANTIALIASING,
                                      RenderingHints.VALUE_ANTIALIAS_ON);
   private Font font = new Font("Cooper Black", Font.PLAIN, 25);
   //Enemy en1, en2;
   private Sprite player1, player2, en1, en2, en3, en4, en5, en6;
   private TileMap tileMap;
   private static String message;
   private boolean playing;
   private boolean gameOver;
   private int jumpCounter;
   
   //CONSTRUCTOR
   
   public SpriteTester()
   {
      setBackground(Color.WHITE);
      addKeyListener(this);
      setFocusable(true);
      tileMap = new TileMap("src/map1.txt", 32);
      tileMap.loadTiles("src/tileset (2).gif");
      player1 = new Sprite(tileMap);
      en1 = new Sprite(tileMap);
      en2 = new Sprite(tileMap);
      en3 = new Sprite(tileMap);
      en4 = new Sprite(tileMap);
      en5 = new Sprite(tileMap);
      en6 = new Sprite(tileMap);
      
      player1.setX(110);
      player1.setY(339);
      ArrayList<Sprite> enemies = new ArrayList<>();
      enemies.add(en1);
      enemies.add(en2);
      enemies.add(en3);
      enemies.add(en4);
      enemies.add(en5);
      enemies.add(en6);
 
      en1.setX(200);
      en1.setY(339);
      en1.setLeft(true);
      
      en2.setX(600);
      en2.setY(339);
      en2.setLeft(true);

      en3.setX(800);
      en3.setY(339);
      en3.setLeft(true);

      en4.setX(1200);
      en4.setY(200);
      en4.setLeft(true);
      
      en5.setX(1400);
      en5.setY(339);
      en5.setLeft(true);
      
      en6.setX(1000);
      en6.setY(200);
      en6.setLeft(true);
      
      playing = true;
      gameOver = false;
      message = "Press any arrow key to Start!";
    
      //The main game timer
      new Timer(10, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            update();
            repaint();
         }
      }).start();
   }
   
   //The update method that is called by the timer
  
   {//update the sprite location and image
   //   System.out.println("DO TIMED STUFF");
      
      
    
   }
      
   public void update()
   {
      
      if(playing == true && !gameOver) {
         player1.update();   
         en1.updateEnemy();
         en2.updateEnemy();
         en3.updateEnemy();
         en4.updateEnemy();
         en5.updateEnemy();
         en6.updateEnemy();
         jumpCounter++;
              
      }
      
      if(en1.getBounds().intersects(player1.getBounds()) || 
         en2.getBounds().intersects(player1.getBounds()) || 
         en3.getBounds().intersects(player1.getBounds()) || 
         en4.getBounds().intersects(player1.getBounds()) ||
         en5.getBounds().intersects(player1.getBounds()) ||
         en6.getBounds().intersects(player1.getBounds()) 
           ) {
         playing = false;           
         gameOver = true;
         message = "GAME OVER! Press <r> to reset!";
         
      }     
      
         if(jumpCounter == 100) {
            en1.setJumping(true);
            en2.setJumping(true);
            en3.setJumping(true);
            en4.setJumping(true);
            en5.setJumping(true);
            en6.setJumping(true);
            jumpCounter = 0;
         }              
      }    
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
    
      g2.setRenderingHints(hints);
     
      g2.setColor(Color.BLACK);
      g2.fillRect(0,0,getWidth(),getHeight());
      g2.setFont(font);
      FontMetrics fm = g2.getFontMetrics();
      g2.setColor(Color.WHITE);
      int width = fm.stringWidth("Developing a TileMap Class");
      // g2.drawString("Developing a TileMap Class", getWidth()/2 - width / 2, 30);
      //draw the map
      tileMap.draw(g2);
      //draw the player
      player1.draw(g2);
      en1.draw(g2);
      en2.draw(g2);
      en3.draw(g2);
      en4.draw(g2);
      en5.draw(g2);
      en6.draw(g2);
      Font font = new Font ("Courier New", 1, 12);
      g2.setFont(font);
      g2.drawString(message, getWidth()/2 - width / 2 + 80,80);

   }

   //KEYLISTENER METHODS
   
   public void resetGame() {
      
      player1.setX(110);
      player1.setY(339);
      en1.setX(200);
      en1.setY(339);     
      en2.setX(600);
      en2.setY(339);
      en3.setX(800);
      en3.setY(339);
      en4.setX(1200);
      en4.setY(200);
      en5.setX(1400);
      en5.setY(339);
      en6.setX(1000);
      en6.setY(200);

      message = "NEW Game";
      playing = true;
      gameOver = false;
}
   
   @Override
   public void keyPressed(KeyEvent e)
   {
      int key = e.getKeyCode();
      
      if(key == KeyEvent.VK_LEFT) {
         player1.setLeft(true);
      }
      if(key == KeyEvent.VK_RIGHT) {
         player1.setRight(true);
      }
      if(key == KeyEvent.VK_UP) {
         player1.setJumping(true);
      }
      
      if(player1.keyDetected(key)) {
         playing = true;
         message = "";
      }
      
      if(e.getKeyCode() == 82 && gameOver == true) {
         resetGame();
        
      }
      
      
   }

   @Override
   public void keyReleased(KeyEvent e)
   {
      int key = e.getKeyCode();
      
      if(key == KeyEvent.VK_LEFT) {
         player1.setLeft(false);
      }
      if(key == KeyEvent.VK_RIGHT) {
         player1.setRight(false);
      }
      if(key == KeyEvent.VK_UP) {
         player1.setJumping(true);
      }
      
      //      if(key == KeyEvent.VK_F) {                 testing
//         player.facingLeft=!player.facingLeft;
//      }
   }

   @Override
   public void keyTyped(KeyEvent e){}
   
//********** METHODS FOR CREATING THE JPANEL AND JFRAME **********
   
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }

   private static void createAndShowGUI() {
      JOptionPane.showMessageDialog(null, 
            "Welcome to Kirby's Ghost Trap Game!", 
            "Kirby!", 
            JOptionPane.INFORMATION_MESSAGE
            );
      JOptionPane.showInputDialog(null, 
            "Please enter your name:", 
            "", 
            JOptionPane.INFORMATION_MESSAGE,
            null,
            null, "Enter name");
      JFrame frame = new JFrame("Developing a Sprite Class");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //Add the game panel to the frame
      frame.getContentPane().add(new SpriteTester());
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGUI();
         }
      });
   }
}
