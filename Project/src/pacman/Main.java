/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;

public abstract class Main {
    
    public static BufferedImage pacmanSprites;
    public static BufferedImage ghostSprites;
    
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;

    public static int currentDirection = 1;
    public static int lastGhostMove = 1;
    public static int playerFrame = 0;
    public static Map map;
    
    public static long playerScore;

    public static Thread displayThread;
    public static Thread entityThread;
    public static Thread scoreThread;
    
//    public static int[][] mapData;
    
    public static void init() {
	
	try {
	    pacmanSprites = ImageIO.read(new File("imgs/pacman.png"));
	    ghostSprites = ImageIO.read(new File("imgs/ghost.png"));
	} catch (Exception e) { }
	
	int[][] mapData = new int[1][1];
	try {
	    List<String> info = Files.readAllLines(new File("imgs/mundo.txt").toPath());
	    int depth = info.size();
	    int width = info.get(0).split(" ").length;
	    mapData = new int[depth][width];
	    for (int i = 0; i < info.size(); i++) {
		String[] line = info.get(i).split(" ");
		for (int j = 0; j < line.length; j++) {
		    mapData[i][j] = Integer.parseInt(line[j]);    
		}
	    }
	} catch (Exception e) { }
	
	map = new Map(mapData);
	
	Window w = new Window(mapData[0].length, mapData.length);
	displayThread = w.init();
	
	entityThread = new Thread() {
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
	};
	
	playerScore = 0;
	
	scoreThread = new Thread() {
	    @Override
	    public void run() {
		while(true) {
		    playerScore++;
		    try {
			Thread.sleep(800);
		    } catch (Exception e) { }
		}
	    }
	};
	
	entityThread.start();
	displayThread.start();
	scoreThread.start();
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
    
    public static BufferedImage getGhost() {
	int directionX = 0;
	int directionY = 0;
	if (lastGhostMove > 1) {
	    directionX = 88*2;
	}
	if (lastGhostMove%2 == 0) {
	    directionY = 88;
	}
	
	int frameX = playerFrame*88;
	
	return ghostSprites.getSubimage(frameX+directionX, directionY, 88, 88);
    }

    public static void moveEnemy() {

    }

    public static void movePlayer() {
	map.move(currentDirection, Map.ENTITY_PLAYER);
    }
}
