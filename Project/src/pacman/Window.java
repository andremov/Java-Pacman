/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Window extends JFrame {
    
    public Window(int mapWidth, int mapHeight) {
	setLayout(null);
	int width = (int)(mapWidth*MapNode.SIZE*Map.SCALE);
	int height = (int)((mapHeight+5)*MapNode.SIZE*Map.SCALE);
	setSize(width,height);
	setTitle("Pac-man");
	setResizable(false);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	addKeyListener(new KeyListener() {
	    
	    @Override
	    public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_A) {
		    Main.setDirection(Main.DIRECTION_LEFT);
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
		    Main.setDirection(Main.DIRECTION_UP);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
		    Main.setDirection(Main.DIRECTION_DOWN);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
		    Main.setDirection(Main.DIRECTION_RIGHT);
		}

	    }

	    @Override
	    public void keyTyped(KeyEvent e) { }

	    @Override
	    public void keyReleased(KeyEvent e) { }
	});

	setVisible(true);
    }
	
    public Thread init() {

	Canvas c = new Canvas();
	c.setSize(getWidth()-8, getHeight()-31);
	c.setLocation(1, 1);
	c.setFocusable(false);
	c.setBackground(Color.black);
	c.setVisible(true);
	add(c);
	
	Thread t = (new Thread() {
	    @Override
	    public void run() {
		c.createBufferStrategy(2);
		while (true) {

		    Graphics g = c.getBufferStrategy().getDrawGraphics();
		    g.clearRect(0, 0, getWidth(), getHeight());
		    BufferedImage img = Main.map.getImg();
		    g.drawImage(img, 0, 0, null);
		    
		    g.setColor(Color.white);
		    g.setFont(new Font("Arial", Font.BOLD, 30));
		    g.drawString("ALVARO ANAYA           Puntaje: "+Main.playerScore,10,450);
		    if (Main.gameEnd) {
			String a = "Ganó!";
			if (!Main.playerWon) {
			    a = "Perdió :(";
			}
			g.drawString(a,10,480);
		    }
		    c.getBufferStrategy().show();
		    
		    try {
			Thread.sleep(Main.TIMER_DISPLAY);
		    } catch (Exception e) { }
		}
	    }
	});
	
	return t;
    }
	
}
