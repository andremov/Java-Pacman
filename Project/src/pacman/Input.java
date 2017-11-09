/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author andresmovilla
 */
public class Input extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_A) {
			Main.currentDirection = Main.DIRECTION_LEFT;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			Main.currentDirection = Main.DIRECTION_UP;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			Main.currentDirection = Main.DIRECTION_DOWN;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			Main.currentDirection = Main.DIRECTION_RIGHT;
		}
		
	}
	
}
