import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

//Daniel Song
//Program Description

public class TileMap
{
   private int mapWidth, mapHeight, tileSize;
   private int[][] map;
   private int x, y;
   private int minX, maxX, minY, maxY;
      
   private Tile[][] tiles;
   
   public TileMap(String fileName, int tileSize)
   {
      this.tileSize=tileSize;
      
      try
      {
         BufferedReader br = new BufferedReader(new FileReader(fileName));
         
         mapWidth = Integer.parseInt(br.readLine().trim());
         mapHeight = Integer.parseInt(br.readLine().trim());
         map = new int[mapHeight][mapWidth];
         
         String delimiters = "\\s+";
         //Read in map data line-by-line
         for(int row = 0; row < mapHeight; row++)
         {
            String line = br.readLine();
            String[] tokens = line.split(delimiters);
            for(int col = 0; col < mapWidth; col++) {
               map[row][col] = Integer.parseInt(tokens[col]);
               System.out.print(map[row][col] + " ");
            }
            System.out.println();
         }
         br.close();
         
      } catch( Exception e){e.printStackTrace();}
      //Set Map scrolling ranges
      maxX = 0;
      maxY = 0;
      minX = SpriteTester.PREF_W - mapWidth * tileSize;
      minY = SpriteTester.PREF_H - mapHeight * tileSize;
      
   }
   
   public void loadTiles(String s)
   {
      try {
         BufferedImage tileset = ImageIO.read(new File(s));
         int numTileAcross = (tileset.getWidth() + 1) / (tileSize + 1);
         tiles = new Tile[2][numTileAcross];
         
         BufferedImage subimage;
         
         for(int col = 0; col < numTileAcross; col++) {//top one is not blocked but the one below is blocked
            //load from top row (unblocked tile)
            subimage = tileset.getSubimage(col * tileSize + col, 0, tileSize, tileSize);
            tiles[0][col] = new Tile(subimage, false);
            //load from bottom row (blocked tile)
            subimage = tileset.getSubimage(col * tileSize + col, tileSize + 1, tileSize, tileSize);
            tiles[1][col] = new Tile(subimage, true);
         }
      }
      catch (Exception e) {e.printStackTrace();}
   }
   
   public void update() {
      //if needed later
   }
   
   public void draw(Graphics2D g2) 
   {
    //  g2.setBackground(Color.black);
      for(int row = 0; row < mapHeight; row++) {
         for(int col = 0; col < mapWidth; col++) {
            int tileValue = map[row][col];
            int r = tileValue / tiles[0].length;
            int c = tileValue % tiles[0].length;
            
            g2.drawImage(
                  tiles[r][c].getImage(),
                  x + col * tileSize,
                  y + row * tileSize,
                  null);
            
            int mapValue = map[row][col];
       //     if(mapValue == 2) g2.setColor(Color.CYAN);
//            if(mapValue == 1) g2.setColor(Color.GRAY);
//            if(mapValue == 2) g2.setColor(Color.GREEN);
     //       if(mapValue==3) playerCollision();
          //  g2.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
          //  g2.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
            
            //show gridLines
//            g2.setColor(Color.BLACK);
//            g2.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            
            
         }
      }
         
   }
   
//   public void playerCollision()
//   {
//      if()
//   }

   public int getColTile(int x)
   {
      return x / tileSize;      
   }
   
   public int getRowTile(int y)
   {
      return y / tileSize;      
   }
   
   public int getTile(int row, int col)
   {
      return map[row][col];
   }
   
   //any non-zero value is a blocked tile
   public boolean isBlocked(int row, int col)
   {
      int tileValue = map[row][col];
      int r = tileValue / tiles[0].length;
      int c = tileValue % tiles[0].length;
      return tiles[r][c].isBlocked();
   }
   
   public int getTileSize()
   {
      return tileSize;
   }
   public int getMapWidth()
   {
      return mapWidth;
   }
   public void setMapWidth(int mapWidth)
   {
      this.mapWidth=mapWidth;
   }
   public int getMapHeight()
   {
      return mapHeight;
   }
   public void setMapHeight(int mapHeight)
   {
      this.mapHeight=mapHeight;
   }
   public int getX()
   {
      return x;
   }
   public void setX(int x)
   {
      this.x=x;
      if(x > maxX) this.x = maxX;
      if(x < minX) this.x = minX;
   }
   public int getY()
   {
      return y;
   }
   public void setY(int y)
   {
      this.y=y;
      if(y > maxY) this.y = maxY;
      if(y < minY) this.y = minY;
   }
}
