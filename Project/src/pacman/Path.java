/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

public class Path {
    
    int goal;
    int length;
    int direction;
    
    public Path(int goal, int length, int direction) {
	this.goal = goal;
	this.length = length;
	this.direction = direction;
    }
    
}
