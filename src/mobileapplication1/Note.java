/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

/**
 *
 * @author alice
 */
class Note {
    long time;  // Time in milliseconds
    int lane;  // Grid tile (1 - 9)
    long startTime; // when we start the note
    short gridX;
    short gridY;
    
    Note(long startTime, long time, int lane) {
        this.time = time;
        this.lane = lane;
        this.startTime = startTime;
        System.out.println(lane);
        gridX = (short) ((lane - 1)/3);
        gridY = (short) ((lane - 1)%3);
        
    }
    
}