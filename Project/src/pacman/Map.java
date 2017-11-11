/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Map {
	
    public static final int ENTITY_PLAYER = 0;
    public static final int ENTITY_ENEMY = 1;

    public BufferedImage mapImage;

    MapNode map;
    MapNode enemy;
    MapNode player;
    
    private int width;
    private int height;


    public Map(int width, int height) {
	createMap();
    }
	
    public void createMap() {

	int[][] matrix = {
	    {0,0,0,0,0,0,0,0,0,0,0,0,0},
	    {0,1,1,1,1,1,1,1,1,1,1,2,0},
	    {0,1,0,0,0,0,0,1,0,0,0,1,0},
	    {0,2,1,1,1,1,1,1,0,1,1,1,0},
	    {0,0,0,0,0,1,0,0,0,1,0,0,0},
	    {0,1,1,1,1,1,1,1,1,1,1,1,1},
	};
	height = matrix.length;
	width = matrix[0].length;
	MapNode[][] mapMatrix = new MapNode[height][width];
	
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		mapMatrix[i][j] = new MapNode(matrix[i][j]==0, matrix[i][j] == 2);
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

	map = mapMatrix[0][0];
	player = map;
    }

    public BufferedImage getImg() {
	BufferedImage img = new BufferedImage(this.width*MapNode.SIZE,this.height*MapNode.SIZE,BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();
	int x = 0;
	int y = 0;
	MapNode cur = map;
	MapNode nextY = map.getDown();
	while (nextY != null) {
	    x = 0;
	    while (cur != null) {
		g.drawImage(cur.getImage(cur == player, cur == enemy), x*MapNode.SIZE, y*MapNode.SIZE, null);
		x++;
		cur = cur.getRight();
	    }
	    cur = nextY;
	    nextY = cur.getDown();
	    y++;
	}
	return img;
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
//		    System.out.println("WRONG MOVE: UP");
		}
		break;
	    case Main.DIRECTION_DOWN:
		if (entity.canDown()) {
		    entity = entity.getDown();
		} else {
//		    System.out.println("WRONG MOVE: DOWN");
		}
		break;
	    case Main.DIRECTION_RIGHT:
		if (entity.canRight()) {
		    entity = entity.getRight();
		} else {
//		    System.out.println("WRONG MOVE: RIGHT");
		}
		break;
	    case Main.DIRECTION_LEFT:
		if (entity.canLeft()) {
		    entity = entity.getLeft();
		} else {
//		    System.out.println("WRONG MOVE: LEFT");
		}
		break;
	}
    }
	
}
