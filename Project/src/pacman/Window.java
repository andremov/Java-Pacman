/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import javax.swing.JFrame;

/**
 *
 * @author andresmovilla
 */
public class Window extends JFrame {

	public Window() {
		setLayout(null);
		setSize(700, 600);
		setTitle("Pacman");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Display d = new Display(this);
		add(d);

//		addKeyListener(new KeyHandler());

		setVisible(true);
		
		new Thread(d).run();
	}
	
	
	
}
