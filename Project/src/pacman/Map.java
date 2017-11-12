/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

    public static final float SCALE = 0.3f;

    public BufferedImage mapImage;

    private MapNode map;
    private MapNode enemy;
    private MapNode player;
    
    private int width;
    private int height;

    public Map(int[][] mapData) {
	height = mapData.length;
	width = mapData[0].length;
	createMap(mapData);
    }
    
    public ArrayList<MapNode> allNodes() {
	MapNode cur = map;
	MapNode nextY;
	ArrayList<MapNode> all = new ArrayList<>();
	
	while (cur != null) {
	    nextY = cur.getDown();
	    while (cur != null) {
		all.add(cur);
		cur = cur.getRight();
	    }
	    cur = nextY;
	}
	return all;
    }
    
    private void findPathsForVertex(ArrayList<MapNode> nodes, int a) {
	
	boolean[] donePaths = new boolean[nodes.size()];

	for (int j = 0; j < nodes.size(); j++)
	    donePaths[j] = false;

	boolean done = false;
	int it = 0;
	int goalID = nodes.get(it).i;
	MapNode start = nodes.get(a);
	
	while (!done) {
//	    System.out.println("  connection to "+nodes.get(it).i);
	    if (start.getPathTo(goalID) == null && goalID != start.i) {
		
		// ONE STEP
		if (start.getDown().i == goalID) {
//		    System.out.println("    done!");
		    start.addPathTo(new Path(goalID,1,Main.DIRECTION_DOWN));
		    donePaths[goalID] = true;
		}
		if (start.getLeft().i == goalID) {
//		    System.out.println("    done!");
		    start.addPathTo(new Path(goalID,1,Main.DIRECTION_LEFT));
		    donePaths[goalID] = true;
		}
		if (start.getRight().i == goalID) {
//		    System.out.println("    done!");
		    start.addPathTo(new Path(goalID,1,Main.DIRECTION_RIGHT));
		    donePaths[goalID] = true;
		}
		if (start.getUp().i == goalID) {
//		    System.out.println("    done!");
		    start.addPathTo(new Path(goalID,1,Main.DIRECTION_UP));
		    donePaths[goalID] = true;
		}
		
		int currentPathIndex = -1;
		int currentLength = nodes.size()+1;
		int direction = -1;
		for (int j = 0; j < nodes.size(); j++) {
		    if (start.getPathTo(j) != null) {
			MapNode midVertex = nodes.get(j);
			if (midVertex.getPathTo(goalID) != null) {
			    int possibleLength = start.getPathTo(j).length + midVertex.getPathTo(goalID).length - 1;
			    if (possibleLength < currentLength) {
				currentPathIndex = j;
				currentLength = possibleLength;
				direction = start.getPathTo(j).direction;
			    }
			}
		    }
		}

		if (currentPathIndex != -1) {
		    start.addPathTo(new Path(goalID,currentLength,direction));
		    donePaths[goalID] = true;
		}
	    } else {
//		System.out.println("    done!");
		donePaths[goalID] = true;
	    }

	    it++;
	    if (it == nodes.size()) {
		it = 0;
		done = true;
		int missing = 0;
		for (int k = 0; k < nodes.size(); k++) {
		    if (!donePaths[k]) {
			done = false;
			missing++;
		    }
		}

		if (!done) {
		    System.out.println("Paths not finished!");
		    System.out.println("Missing: "+missing+"/"+nodes.size());
		    for (int k = 0; k < nodes.size(); k++) {
			donePaths[k] = false;
		    }
		}
	    }
	    goalID = nodes.get(it).i;
	}
    }
    
    public void buildPaths() {
	ArrayList<MapNode> nodes = allNodes();
	for (int i = nodes.size()-1; i >= 0; i--) {
	    if (nodes.get(i).isWall()) {
		nodes.remove(i);
	    }
	}
	for (int i = 0; i < nodes.size(); i++) {
	    System.out.println("Building paths for "+nodes.get(i).i+"...");
	    findPathsForVertex(nodes,i);
	}
    }
	
    public void createMap(int[][] mapData) {

	MapNode[][] mapMatrix = new MapNode[height][width];
	
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		int id = (i*height)+j;
		mapMatrix[i][j] = new MapNode(id, mapData[i][j]==1, mapData[i][j] == 2);
	    }
	}

	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
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
	player = mapMatrix[5][5];
	enemy = mapMatrix[7][10];
    }

    public BufferedImage getImg() {
	BufferedImage img = new BufferedImage(this.width*MapNode.SIZE,this.height*MapNode.SIZE,BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();
	int x = 0;
	int y = 0;
	MapNode cur = map;
	MapNode nextY;
	int trueS = (int)(MapNode.SIZE*SCALE);
	
	while (cur != null) {
	    nextY = cur.getDown();
	    x = 0;
	    while (cur != null) {
		if (cur.isFood() && cur == player) {
		    cur.setFood(false);
		    Main.remainingFood--;
		    Main.checkEnd();
//		    Main.nextFood();
		    Main.playerScore += 10;
		}
		g.drawImage(cur.getImage(cur == player, cur == enemy), x*trueS, y*trueS, trueS, trueS, null);
		g.setFont(new Font("Arial",Font.PLAIN,8));
		x++;
		cur = cur.getRight();
	    }
	    cur = nextY;
	    y++;
	}
	
	return img;
    }
    
    public void moveEnemy() {
	enemy = enemy.getNext(enemy.getPathTo(player.i).direction);
    }
    
    public void movePlayer(int moveDirection) {
	switch(moveDirection) {
	    case Main.DIRECTION_UP:
		if (player.canUp()) {
		    player = player.getUp();
		}
		break;
	    case Main.DIRECTION_DOWN:
		if (player.canDown()) {
		    player = player.getDown();
		}
		break;
	    case Main.DIRECTION_RIGHT:
		if (player.canRight()) {
		    player = player.getRight();
		}
		break;
	    case Main.DIRECTION_LEFT:
		if (player.canLeft()) {
		    player = player.getLeft();
		}
		break;
	}
    }
	
}
