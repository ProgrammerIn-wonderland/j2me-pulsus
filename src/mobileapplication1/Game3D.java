/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

/**
 * @author alice
 */
public class Game3D extends Game {
    int screenCenterY = (getHeight() >> 1);
    int screenCenterX = (getWidth() >> 1);
    /**
     * constructor
     */
    public Game3D(Image bg) {
        super(bg);
    }
    /**
     * paint
     */
    public void paint(Graphics g) {
        g.setColor(0);
        
        g.drawImage(bg, 0, 0, 0);

        drawCurrentOutlineNotes(g);
        drawCurrentNotes(g);
        drawDecayNotes(g);
        drawPressedKeys(g);
        drawGrid(g);
        
        g.setColor(0xFF0000);

//        long currentTime = ((player.getMediaTime() * 0x418937L) >>> 32);
        long currentTime = ((System.currentTimeMillis() - millioffset));
        g.drawString("Score: " + Long.toString(score), 0, 0, Graphics.TOP | Graphics.LEFT);
    }
    void drawSquare(Graphics g, int x, int y, int size, int color) {
        // Size is between 0-44 so we will use that to calculate its pos from center
        
        int precolor = g.getColor();
        if (color != -1) {
            g.setColor(color);
        }

        int centerX = x+23;
        int centerY = y+23;
        // Multiply and then divide by 1024 to avoid floating point math
        // 44 * 23 is almost 1024 so then we can just divide by 1024 by bitshifting right 10 bits
        int realx = (int) (((size * 23) * (centerX - screenCenterX + 1)) >> 10) + (screenCenterX); 
        int realy = (int) (((size * 23) * (centerY - screenCenterY + 1)) >> 10) + (screenCenterY);
        
        int halfsize = size >> 1; // Effectively divides by two but faster
                
        g.fillRect( (realx - halfsize) ,(realy - halfsize), size, size);
        if (color != -1) {
            g.setColor(precolor);
        }
    }
    void drawCurrentOutlineNotes(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0xFF0000);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (currentGrid[row][col] > 3) {
                    drawSquare(g, initialWidth + (col * 50), initialHeight + (row * 50), currentGrid[row][col]-3, (0xFF7F7F));
                }
            }
        }
        g.setColor(precolor);
    }

}
