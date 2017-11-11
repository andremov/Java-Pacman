/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

public abstract class Main {
	
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;

    public static int currentDirection = 1;

    public static Map map;

    public static void init() {
	map = new Map(15,10);
	Window w = new Window();

	(new Thread() {
	    @Override
	    public void run() {
		while(true) {
		    Main.movePlayer();
		    Main.moveEnemy();
		    try {
			Thread.sleep(200);
		    } catch (Exception e) {

		    }
		}
	    }
	}).start();

	System.out.println("started");
    }

    public static void moveEnemy() {

    }

    public static void movePlayer() {
	map.move(currentDirection, Map.ENTITY_PLAYER);
    }
}
