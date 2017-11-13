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
    public static int TRUE_SIZE;
    
    public BufferedImage mapImage;

    private MapNode map;
    private MapNode enemy;
    private MapNode player;
    
    private int width;
    private int height;

    public Map(int[][] mapData) {
	TRUE_SIZE = (int)(SCALE*MapNode.SIZE);
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
    
    private void findBasicPathsForVertex(MapNode v) {
	if (v.canDown()) {
	    v.addPath(new Path(v.getDown().id,1,Main.DIRECTION_DOWN));
	}
	if (v.canLeft()) {
	    v.addPath(new Path(v.getLeft().id,1,Main.DIRECTION_LEFT));
	}
	if (v.canRight()) {
	    v.addPath(new Path(v.getRight().id,1,Main.DIRECTION_RIGHT));
	}
	if (v.canUp()) {
	    v.addPath(new Path(v.getUp().id,1,Main.DIRECTION_UP));
	}
    }
    
    public void createMatrix(ArrayList<MapNode> nodes) {
	int N = nodes.size();
	
        for (int i = 0; i < N; i++) {
	    System.out.println("Haciendo "+i+"...");
            boolean[] allDone = new boolean[N];
            for (int k = 0; k < N; k++) {
                allDone[k] = false;
            }
			
            boolean done = false;
            int search = 0;
	    int searchID;
            while (!done) {
                allDone[search] = true;
		searchID = nodes.get(search).id;
                if (search != i) {
                    MapNode place = nodes.get(i);
                    int currentCost = 100;
//                    for (int k = 0; k < place.getPaths().size(); k++) {
                        if (place.getPathTo(searchID) != null) {
                            currentCost = place.getPathTo(searchID).length;
                        }
//                    }
					
                    for (int k = 0; k < place.getPaths().size(); k++) {
			MapNode middle = null;
			int toMidCost = -1;
			int toMidDir = -1;
			for (int j = 0; j < N; j++) {
			    if (nodes.get(j).id == place.getPaths().get(k).goal) {
				middle = nodes.get(j);
				toMidCost = place.getPaths().get(k).length;
				toMidDir = place.getPaths().get(k).direction;
				break;
			    }
			}
			
			for (int l = 0; l < middle.getPaths().size(); l++) {
			    if (middle.getPaths().get(l).goal == searchID) {
				int toGoalCost = middle.getPaths().get(l).length;
				int newCost = toMidCost + toGoalCost;
				if (newCost < currentCost) {
				    place.addPath(new Path(searchID,newCost,toMidDir));
				    allDone[search] = false;
				}
			    }
			}
                    }
                }
                
				
                search++;
                if (search == N) {
                    search = 0;
                    done = true;
                    for (int k = 0; k < N; k++) {
                        if (!allDone[k]) {
                            done = false;
                        }
                    }
					
                    if (!done) {
                        for (int k = 0; k < N; k++) {
                            allDone[k] = false;
                        }
                    }
                }
            }
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
	    findBasicPathsForVertex(nodes.get(i));
	}
	createMatrix(nodes);
    }
	
    public void createMap(int[][] mapData) {

	MapNode[][] mapMatrix = new MapNode[height][width];
	int id = 0;
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {	
		mapMatrix[i][j] = new MapNode(id, mapData[i][j]==1, mapData[i][j] == 2, j, i);
		id++;
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
	player = mapMatrix[7][11];
	enemy = mapMatrix[13][9];
    }

    public BufferedImage getImg() {
	BufferedImage img = new BufferedImage(this.width*MapNode.SIZE,this.height*MapNode.SIZE,BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();
	MapNode cur = map;
	MapNode nextY;
	
	while (cur != null) {
	    nextY = cur.getDown();
	    while (cur != null) {
		if (cur.isFood() && cur == player) {
		    cur.setFood(false);
		    Main.remainingFood--;
//		    Main.nextFood();
		    Main.playerScore += 10;
		}
		g.drawImage(cur.getImage(cur == player, cur == enemy), cur.x*TRUE_SIZE, cur.y*TRUE_SIZE, TRUE_SIZE, TRUE_SIZE, null);
		cur = cur.getRight();
	    }
	    cur = nextY;
	}
	
	return img;
    }
    
    public void moveEnemy() {
	enemy = enemy.getNext(enemy.getPathTo(player.id).direction);
    }
    
    public boolean checkLost() {
	return enemy == player;
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
