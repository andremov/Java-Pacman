/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Window extends JFrame {
	
    public Window() {
	setLayout(null);
	setSize(700, 600);
	setTitle("Pac-man");
	setResizable(false);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	Canvas c = new Canvas();
	c.setSize(getWidth()-2, getHeight()-24);
	c.setLocation(1, 1);
	c.setFocusable(false);
	c.setBackground(Color.black);
	c.setVisible(true);
	add(c);

	addKeyListener(new KeyListener() {
	    
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

	    @Override
	    public void keyTyped(KeyEvent e) { }

	    @Override
	    public void keyReleased(KeyEvent e) { }
	});

	setVisible(true);

	(new Thread() {
	    @Override
	    public void run() {
		createBufferStrategy(2);
		while (true) {

		    Graphics g = getBufferStrategy().getDrawGraphics();
		    g.clearRect(0, 0, getWidth(), getHeight());
		    g.drawImage(Main.map.getImg(), 0, 0, null);
		    getBufferStrategy().show();

		    try {
			Thread.sleep(100);
		    } catch (Exception e) { }
		}
	    }
	}).start();
    }
	
	
	
}
