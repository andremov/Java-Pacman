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

	Display d;
	
	public Window() {
		setLayout(null);
		setSize(700, 600);
		setTitle("Pac-man");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		d = new Display(this);
		add(d);

		addKeyListener(new Input());

		setVisible(true);
	}
	
	
	
}
