/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapNode {

    public static final int SIZE = 88;
    private final boolean wall;
    
    private ArrayList<Path> paths;
    
    private MapNode[] directions;
    private boolean food;
    
    public int id;
    public int x;
    public int y;

    public MapNode(int i, boolean wall, boolean food, int x, int y) {
	this.wall = wall;
	this.food = food;
	this.id = i;
	this.x = x;
	this.y = y;
	paths = new ArrayList<>();
	directions = new MapNode[4];
    }

    /**
     * @return the wall
     */
    public boolean isWall() {
	return wall;
    }
    
    public MapNode getNext(int direction) {
	return directions[direction];
    }
    
    /**
     * @return the left
     */
    public MapNode getLeft() {
	return directions[Main.DIRECTION_LEFT];
    }

    /**
    * @param left the left to set
    */
   public void setLeft(MapNode left) {
       this.directions[Main.DIRECTION_LEFT] = left;
   }

   /**
    * @return the right
    */
   public MapNode getRight() {
       return directions[Main.DIRECTION_RIGHT];
   }

   /**
    * @param right the right to set
    */
   public void setRight(MapNode right) {
       this.directions[Main.DIRECTION_RIGHT] = right;
   }

    /**
     * @return the up
     */
    public MapNode getUp() {
	return directions[Main.DIRECTION_UP];
    }

    /**
     * @param up the up to set
     */
    public void setUp(MapNode up) {
	this.directions[Main.DIRECTION_UP] = up;
    }

    /**
     * @return the down
     */
    public MapNode getDown() {
	return directions[Main.DIRECTION_DOWN];
    }

    /**
     * @param down the down to set
     */
    public void setDown(MapNode down) {
	this.directions[Main.DIRECTION_DOWN] = down;
    }

    public boolean canDown() {
	if (this.directions[Main.DIRECTION_DOWN] == null)
	    return false;
	
	return !this.directions[Main.DIRECTION_DOWN].isWall();
    }

    public boolean canUp() {
	if (this.directions[Main.DIRECTION_UP] == null)
	    return false;
	
	return !this.directions[Main.DIRECTION_UP].isWall();
    }
	
    public boolean canLeft() {
	if (this.directions[Main.DIRECTION_LEFT] == null)
	    return false;
	
	return !this.directions[Main.DIRECTION_LEFT].isWall();
    }

    public boolean canRight() {
	if (this.directions[Main.DIRECTION_RIGHT] == null)
	    return false;
	
	return !this.directions[Main.DIRECTION_RIGHT].isWall();
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

    /**
     * @param food the food to set
     */
    public void setFood(boolean food) {
	this.food = food;
    }

    public Path getPathTo(int id) {
	for (int j = 0; j < paths.size(); j++) {
	    if (paths.get(j).goal == id) {
		return paths.get(j);
	    }
	}
	return null;
    }
    
    public ArrayList<Path> getPaths() {
	return paths;
    }
    
    public boolean addPath(Path p) {
	boolean canAdd = true;
	for (int j = 0; j < paths.size(); j++) {
	    if (paths.get(j).goal == p.goal) {
		Path c = paths.get(j);
		if (c.length > p.length) {
		    paths.remove(j);
		} else {
		    canAdd = false;
		}
	    }
	}
	if (canAdd) {
	    paths.add(p);
	    return true;
	}
	return false;
    }

}
