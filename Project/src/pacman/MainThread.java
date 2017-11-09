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
public class MainThread implements Runnable {

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
	
}
