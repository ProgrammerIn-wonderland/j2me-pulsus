/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import javax.microedition.lcdui.*;


/**
 * @author alice
 */
public class MIDPCanvas extends Canvas implements CommandListener, Runnable {
    final public int TOP_LEFT = 49;
    final public int TOP_MIDDLE = 50;
    final public int TOP_RIGHT = 51;
    final public int MIDDLE_LEFT = 52;
    final public int MIDDLE_MIDDLE = 53;
    final public int MIDDLE_RIGHT = 54;
    final public int BOTTOM_LEFT = 55;
    final public int BOTTOM_MIDDLE = 56;
    final public int BOTTOM_RIGHT = 57;
    Graphics graphics;
    int initialWidth;
    int initialHeight;
    boolean keyStates[][] =  {
        {false, false, false},
        {false, false, false},
        {false, false, false}
    };
    
    // Each element here represents a square on the grid
    // Between 0 - 44 (full square) is the range of values, 44 being the tile is almost ready.
    // This will be painted on every paint and the values wll be managed by the music thread
    // -- Possibly -- Values can go over 44 for overage (user is too slow) up to 100
    int currentGrid [][] =  {
        {44, 0, 0},
        {0, 30, 0},
        {0, 0, 0}
    };
    /**
     * constructor
     */
    public MIDPCanvas() {
        try {
            // Set up this canvas to listen to command events
            setCommandListener(this);
            // Add the Exit command
            addCommand(new Command("Exit", Command.EXIT, 1));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * paint
     */
    public void paint(Graphics g) {
        
        this.graphics = g;
        g.setColor(0);
        
        
//        new Image()
//        g.drawImage(img, UP, UP, RIGHT);
        
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        initialHeight = (((this.getHeight() - 145 )/ 2) );
        initialWidth =  (((this.getWidth() - 145 )/ 2));
        drawGrid(g);
        g.setColor(0xFF0000);
//        System.out.println(keyCode);
//        g.fillRect(initialWidth + 1, initialHeight+1, 44, 44);
//        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        
        g.drawString("Sample Text", 0, 0, Graphics.TOP | Graphics.LEFT);
    }
    void drawSquare(Graphics g, int x, int y, int size, int color) {

        int precolor = g.getColor();
        if (color != -1) {
            g.setColor(color);
        }

        int midpointx = x+23;
        int midpointy = y+23;
        int halfsize = size >> 1; // Effectively divides by two but faster
                
        g.fillRect( (midpointx - halfsize) ,(midpointy - halfsize), size, size);
        if (color != -1) {
            g.setColor(precolor);
        }
    }
    void drawGrid(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0xFFFFFF);
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                g.drawRect(initialWidth + (col * 50), initialHeight + (row * 50), 45, 45);
            }
        }
        g.setColor(0xFF0000);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                drawSquare(g, initialWidth + (col * 50), initialHeight + (row * 50), currentGrid[row][col], -1);
            }
        }
        g.setColor(0x0000FF);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (keyStates[row][col])
                    drawSquare(g, initialWidth + (col * 50), initialHeight + (row * 50), 44, -1);
            }
        }
    
//        g.drawRect(initialWidth + 0, initialHeight + 0, 45, 45);
////        drawSquare(g, initialWidth + 0, initialHeight, 20, 0xFF0000);
//        g.drawRect(initialWidth + 50, initialHeight + 0, 45, 45);
//        g.drawRect(initialWidth + 100, initialHeight + 0, 45, 45);
//        
//        g.drawRect(initialWidth + 0, initialHeight + 50, 45, 45);
//        g.drawRect(initialWidth + 50, initialHeight + 50, 45, 45);
//        g.drawRect(initialWidth + 100, initialHeight + 50, 45, 45);
//        
//        g.drawRect(initialWidth + 0, initialHeight + 100, 45, 45);
//        g.drawRect(initialWidth + 50, initialHeight + 100, 45, 45);
//        g.drawRect(initialWidth + 100, initialHeight + 100, 45, 45);
//        g.setColor(precolor);
    }
    /**
     * Called when a key is pressed.
     */
    protected void keyPressed(int keyCode) {
        // Broken, apparently you cant just pass reference of the graphics object like this, you have to set some variables and then repaint
        int precolor = graphics.getColor();
        System.out.println(graphics);
        this.graphics.setColor(0xFF0000);
        System.out.println(keyCode);
        // You probably think I'm stupid for not just calculating the index by 
        // division but remember that like the majority of phones didn't even support
        // division in hardware. I'm avoiding division like an elementary schooler - Rafflesia
        switch (keyCode) {
            case TOP_LEFT:
                keyStates[0][0] = true;
                break;
            case TOP_MIDDLE:
                keyStates[0][1] = true;
                break;
            case TOP_RIGHT:
                keyStates[0][2] = true;
                break;
            case MIDDLE_LEFT:
                keyStates[1][0] = true;
                break;
            case MIDDLE_MIDDLE:
                keyStates[1][1] = true;
                break;
            case MIDDLE_RIGHT:
                keyStates[1][2] = true;
                break;
            case BOTTOM_LEFT:
                keyStates[2][0] = true;
                break;
            case BOTTOM_MIDDLE:
                keyStates[2][1] = true;
                break;
            case BOTTOM_RIGHT:
                keyStates[2][2] = true;
                break;
        }
        this.repaint();
    }

    /**
     * Called when a key is released.
     */
    protected void keyReleased(int keyCode) {
        switch (keyCode) {
            case TOP_LEFT:
                keyStates[0][0] = false;
                break;
            case TOP_MIDDLE:
                keyStates[0][1] = false;
                break;
            case TOP_RIGHT:
                keyStates[0][2] = false;
                break;
            case MIDDLE_LEFT:
                keyStates[1][0] = false;
                break;
            case MIDDLE_MIDDLE:
                keyStates[1][1] = false;
                break;
            case MIDDLE_RIGHT:
                keyStates[1][2] = false;
                break;
            case BOTTOM_LEFT:
                keyStates[2][0] = false;
                break;
            case BOTTOM_MIDDLE:
                keyStates[2][1] = false;
                break;
            case BOTTOM_RIGHT:
                keyStates[2][2] = false;
                break;
        }
        repaint();
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected void keyRepeated(int keyCode) {
    }

    /**
     * Called when the pointer is dragged.
     */
    protected void pointerDragged(int x, int y) {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     */
    protected void pointerReleased(int x, int y) {
    }

    /**
     * Called when action should be handled
     */
    public void commandAction(Command command, Displayable displayable) {
    }
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            repaint(); // Ask the system to redraw the screen
            try {
                Thread.sleep(100); // Adjust polling rate (100ms)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
