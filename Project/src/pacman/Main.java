/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public static Window w;
    
    public static int remainingFood;
    
    public static long playerScore;

    public static Thread displayThread;
    public static Thread entityThread;
    public static Thread scoreThread;
    
    public static final int TIMER_DISPLAY = 10;
    public static final int TIMER_ENTITY = 400;
    public static final int TIMER_SCORE = 200;
    
    public static ArrayList<MapNode> foodStack;
    
    public static boolean gameEnd;
    public static boolean playerWon;
    
    public static boolean loading;
    
    public static void init() {
	loading = true;
	loadData();
	gameEnd = false;
	playerWon = false;
	
//	buildFoodStack(map);
	for (int i = 0; i < 10; i++) {
	    remainingFood++;
//	    nextFood();
	}
	
	playerScore = 0;
	
	createThreads();
	
	displayThread.start();
	
	map.buildPaths();
	
	loading = false;
	
	entityThread.start();
	scoreThread.start();
    }
    
    public static void buildFoodStack(Map map) {
	ArrayList<MapNode> list = map.allNodes();
	for (int i = list.size()-1; i >= 0; i--) {
	    if (list.get(i).isWall()) {
		list.remove(i);
	    }
	}
	Random r = new Random();
	for (int i = 0; i < list.size()-1; i++) {
	    int numberSwaps = list.size()-i;
	    int swapIndex = r.nextInt(numberSwaps)+i;
	    MapNode temp = list.get(i);
	    list.set(i, list.get(swapIndex));
	    list.set(swapIndex,temp);
	}
	foodStack = list;
    }
    
    public static void nextFood() {
	MapNode next = foodStack.remove(0);
	next.setFood(true);
	foodStack.add(next);
    }
    
    public static void loadData() {
	
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
	
	w = new Window(mapData[0].length, mapData.length);
    }
    
    public static void createThreads() {
	displayThread = w.init();
	
	entityThread = new Thread() {
	    @Override
	    public void run() {
		while(true) {
		    Main.checkEnd();
		    playerFrame = (playerFrame+1)%2;

		    map.moveEnemy();
		    map.movePlayer(currentDirection);
		    
		    try {
			Thread.sleep(TIMER_ENTITY);
		    } catch (Exception e) { }
		}
	    }
	};
	
	scoreThread = new Thread() {
	    @Override
	    public void run() {
		while(true) {
		    playerScore++;
		    try {
			Thread.sleep(TIMER_SCORE);
		    } catch (Exception e) { }
		}
	    }
	};
    }
    
    public static void checkEnd() {
	if (remainingFood == 0) {
	    playerWon = true;
	    endGame();
	} else if (map.checkLost()) {
	    playerWon = false;
	    endGame();
	}
    }
    
    public static void endGame() {
	gameEnd = true;
	scoreThread.stop();
	entityThread.stop();
    }
    
    public static void setDirection (int newDir) {
	currentDirection = newDir;
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
}
