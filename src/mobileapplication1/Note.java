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
    int time;  // Time in milliseconds
    int lane;  // Grid tile (1 - 9)
    int startTime; // when the Fuck do we start the note
    
    Note(int startTime, int time, int lane) {
        this.time = time;
        this.lane = lane;
        this.startTime = time - 44000;
    }
    
}