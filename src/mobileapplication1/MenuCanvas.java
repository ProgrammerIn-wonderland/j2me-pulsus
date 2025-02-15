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
public class MenuCanvas extends Canvas implements CommandListener {
    Display display;
    /**
     * constructor
     */
    public MenuCanvas(Display display) {
        try {
            this.display = display;
            // Set up this canvas to listen to command events
            setCommandListener(this);
            // Add the Exit command
            addCommand(new Command("DEBUG main game", Command.OK, 0));
            addCommand(new Command("Exit", Command.EXIT, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * paint
     */
    public void paint(Graphics g) {
        g.setColor(0x000000); // Set color black
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // Draw that background 
        
        // Set color red
        g.setColor(0xFF0000);
        g.drawString("Sample Text", 0, 0, Graphics.TOP | Graphics.LEFT);
    }

    /**
     * Called when a key is pressed.
     */
    protected void keyPressed(int keyCode) {
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
        if (command.getLabel().equals("DEBUG main game")) {
            this.display.setCurrent(new Game());
        }
    }
    
}
