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
public class MIDPCanvas extends Canvas implements CommandListener {
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

    void drawGrid(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0xFFFFFF);
        
        g.drawRect(initialWidth + 0, initialHeight + 0, 45, 45);
        g.drawRect(initialWidth + 50, initialHeight + 0, 45, 45);
        g.drawRect(initialWidth + 100, initialHeight + 0, 45, 45);
        
        g.drawRect(initialWidth + 0, initialHeight + 50, 45, 45);
        g.drawRect(initialWidth + 50, initialHeight + 50, 45, 45);
        g.drawRect(initialWidth + 100, initialHeight + 50, 45, 45);
        
        g.drawRect(initialWidth + 0, initialHeight + 100, 45, 45);
        g.drawRect(initialWidth + 50, initialHeight + 100, 45, 45);
        g.drawRect(initialWidth + 100, initialHeight + 100, 45, 45);
        g.setColor(precolor);
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
        this.graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        switch (keyCode) {
            case TOP_LEFT:
                System.out.println("case hit");
                graphics.fillRect(initialWidth + 1, initialHeight+1, 44, 44);
                break;
            case TOP_MIDDLE:
                break;
            case TOP_RIGHT:
                break;
            case MIDDLE_LEFT:
                break;
            case MIDDLE_MIDDLE:
                break;
            case MIDDLE_RIGHT:
                break;
            case BOTTOM_LEFT:
                break;
            case BOTTOM_MIDDLE:
                break;
            case BOTTOM_RIGHT:
                break;
        }
        graphics.setColor(precolor);
    }

    /**
     * Called when a key is released.
     */
    protected void keyReleased(int keyCode) {
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
    
}
