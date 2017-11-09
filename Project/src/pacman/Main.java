/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

/**
 *
 * @author andresmovilla
 */
public abstract class Main {
	
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	
	public static int currentDirection = 1;
	
	public static Map map;
	
	public static void init() {
		
		map = new Map();
		
		new Window();
		MainThread t = new MainThread();
		new Thread(t).start();
	}
	
	public static void moveEnemy() {
		
	}
	
	
	public static void movePlayer() {
		
	}
}
