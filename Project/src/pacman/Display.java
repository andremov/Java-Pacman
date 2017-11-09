/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author andresmovilla
 */
public class Display extends Canvas implements Runnable {

	public Display(JFrame parent) {
		setSize(parent.getWidth()-2, parent.getHeight()-24);
		setLocation(1, 1);
		setFocusable(false);
		setBackground(Color.black);
		setVisible(true);
	}
	
	@Override
	public void run() {
		createBufferStrategy(2);
		while (true) {
			Graphics g = getBufferStrategy().getDrawGraphics();
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(Main.map.mapImage, 0, 0, null);
			try {
				Thread.sleep(100);
			} catch (Exception e) { }
		}
	}
	
}
