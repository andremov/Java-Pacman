/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Main {
    
    public static BufferedImage pacmanSprites;
    
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;

    public static int currentDirection = 1;
    public static int playerFrame = 0;
    public static Map map;

    public static void init() {
	map = new Map(15,10);
//	while (pacmanSprites == null) {
	    try {
		pacmanSprites = ImageIO.read(new File("imgs/pacman.png"));
	    } catch (Exception e) { }
//	}
	
	Window w = new Window();
	(new Thread() {
	    @Override
	    public void run() {
		while(true) {
		    playerFrame = (playerFrame+1)%2;
		    Main.movePlayer();
		    Main.moveEnemy();
		    try {
			Thread.sleep(200);
		    } catch (Exception e) { }
		}
	    }
	}).start();

	System.out.println("started");
    }
    
    public static BufferedImage getPacman() {
	int directionX = 0;
	int directionY = 0;
	if (currentDirection > 1) {
	    directionX = 88*2;
	}
	if (currentDirection%2 == 0) {
	    directionY = 88;
	}
	
	int frameX = playerFrame*88;
	
	return pacmanSprites.getSubimage(frameX+directionX, directionY, 88, 88);
    }

    public static void moveEnemy() {

    }

    public static void movePlayer() {
	map.move(currentDirection, Map.ENTITY_PLAYER);
    }
}
