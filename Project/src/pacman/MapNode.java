/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MapNode {

    public static final int SIZE = 88;
    private final boolean wall;

    private MapNode left;
    private MapNode right;
    private MapNode up;
    private MapNode down;
    private boolean food;

    public MapNode(boolean wall, boolean food) {
	this.wall = wall;
	this.food = food;
    }

    /**
     * @return the wall
     */
    public boolean isWall() {
	return wall;
    }

    /**
     * @return the left
     */
    public MapNode getLeft() {
	return left;
    }

    /**
    * @param left the left to set
    */
   public void setLeft(MapNode left) {
       this.left = left;
   }

   /**
    * @return the right
    */
   public MapNode getRight() {
       return right;
   }

   /**
    * @param right the right to set
    */
   public void setRight(MapNode right) {
       this.right = right;
   }

    /**
     * @return the up
     */
    public MapNode getUp() {
	return up;
    }

    /**
     * @param up the up to set
     */
    public void setUp(MapNode up) {
	this.up = up;
    }

    /**
     * @return the down
     */
    public MapNode getDown() {
	return down;
    }

    /**
     * @param down the down to set
     */
    public void setDown(MapNode down) {
	this.down = down;
    }

    public boolean canDown() {
	return (this.down == null) || (this.down.isWall());
    }

    public boolean canUp() {
	return (this.up == null) || (this.up.isWall());
    }
	
    public boolean canLeft() {
	return (this.left == null) || (this.left.isWall());
    }

    public boolean canRight() {
	return (this.right == null) || (this.right.isWall());
    }

    public BufferedImage getImage(boolean isPlayer, boolean isEnemy) {
	BufferedImage img = new BufferedImage(SIZE,SIZE,BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();

	if (wall) {
	    g.setColor(Color.blue);
	} else {
	    g.setColor(Color.black);
	}
	g.fillRect(0, 0, SIZE, SIZE);
	if (food && isPlayer) {
	    food = false;
	    Main.playerScore += 10;
	}
	if (food) {
	    g.setColor(Color.yellow);
	    int r = SIZE/3;
	    int p = (SIZE/2)-(r/2);
	    g.fillOval(p,p,r,r);
	}
	if (isPlayer) {
	    g.drawImage(Main.getPacman(),0,0,null);
	}
	if (isEnemy) {
	    g.drawImage(Main.getGhost(),0,0,null);
	}

	return img;
    }

    /**
     * @return the food
     */
    public boolean isFood() {
	return food;
    }

}
