/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author andresmovilla
 */
public class Map {
	
	public static final int ENTITY_PLAYER = 0;
	public static final int ENTITY_ENEMY = 1;
	
	public BufferedImage mapImage;
	
	MapNode map;
	MapNode enemy;
	MapNode player;
	
	public void createMap() {
		int width = 10;
		int height = 10;
		MapNode[][] mapMatrix = new MapNode[width][height];
		Random rnd = new Random();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				mapMatrix[i][j] = new MapNode(rnd.nextInt()%2 == 1);
			}
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				try {
					mapMatrix[i][j].setUp(mapMatrix[i-1][j]);
				} catch (Exception e) { }
				try {
					mapMatrix[i][j].setDown(mapMatrix[i+1][j]);
				} catch (Exception e) { }
				try {
					mapMatrix[i][j].setLeft(mapMatrix[i][j-1]);
				} catch (Exception e) { }
				try {
					mapMatrix[i][j].setRight(mapMatrix[i][j+1]);
				} catch (Exception e) { }
			}
		}
		
		
		BufferedImage img = new BufferedImage(15,15,BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				g.drawImage(mapMatrix[i][j].getImage(), j*15, i*15, null);
			}
		}
		
	}
	
	public void move(int moveDirection, int entityID) {
		MapNode entity = player;
		if (entityID == ENTITY_ENEMY) {
			entity = enemy;
		}
		switch(moveDirection) {
			case Main.DIRECTION_UP:
				if (entity.canUp()) {
					entity = entity.getUp();
				} else {
					System.out.println("WRONG MOVE");
				}
				break;
			case Main.DIRECTION_DOWN:
				if (entity.canDown()) {
					entity = entity.getDown();
				} else {
					System.out.println("WRONG MOVE");
				}
				break;
			case Main.DIRECTION_RIGHT:
				if (entity.canRight()) {
					entity = entity.getRight();
				} else {
					System.out.println("WRONG MOVE");
				}
				break;
			case Main.DIRECTION_LEFT:
				if (entity.canLeft()) {
					entity = entity.getLeft();
				} else {
					System.out.println("WRONG MOVE");
				}
				break;
		}
	}
	
}
