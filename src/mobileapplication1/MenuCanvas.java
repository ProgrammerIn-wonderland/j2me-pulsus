/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.*;

/**
 * @author alice
 */
public class MenuCanvas extends Canvas implements CommandListener, Runnable {
    Display display;
    Image bgBlur;
    Image bg;
    int xScreenCenter;
    int yScreenCenter;
    Font defaultFont;
    long milioffset = Long.MAX_VALUE;
    boolean introAnimationDone = false;
    int initialWidth;
    int initialHeight;
    boolean gameLoadAnimation = false;
    long stageOffset = 0;
    boolean loadGameNextFrame = false;
    int cursorPos = 0;
    Thread runner;
    /**
     * constructor
     */
    public MenuCanvas(Display display) {
        try {
            xScreenCenter = (getWidth() / 2);
            yScreenCenter = (getHeight() / 2);
            defaultFont = Font.getDefaultFont();
            initialHeight = (((this.getHeight() - 145 )/ 2) );
            initialWidth =  (((this.getWidth() - 145 )/ 2));
            this.display = display;
            this.bgBlur = Image.createImage(getClass().getResourceAsStream("/bg-blur.png"));
            this.bg = Image.createImage(getClass().getResourceAsStream("/bg.png"));
            // Set up this canvas to listen to command events
            setCommandListener(this);
            // Add the Exit command
            addCommand(new Command("DEBUG main game", Command.OK, 0));
            addCommand(new Command("Exit", Command.EXIT, 1));
            milioffset = System.currentTimeMillis() + 1000;
            stageOffset = System.currentTimeMillis() + 1500;
            runner = new Thread(this);
            runner.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Unused utility function I wrote, not deleting because it might be useful somewhere?
    public byte[] readFully(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    void drawDisclaimerText(Graphics g) {
        int preColor = g.getColor();
        int offsetx = 0;
        System.out.println(System.currentTimeMillis() - milioffset);
        if (System.currentTimeMillis() - milioffset > 0) {
            offsetx = (int)((System.currentTimeMillis() - milioffset) >>> 1);
        }
        g.setColor(0xFFFFFF);
        int x = xScreenCenter - (defaultFont.stringWidth("This is an early") >>> 1) + offsetx;
        int y = yScreenCenter - 2 * (defaultFont.getHeight() >>> 1);
        
        if (offsetx > (this.getWidth()))
            introAnimationDone = true;
        
        g.drawString("This is an early", x, y, Graphics.TOP | Graphics.LEFT);
        x = xScreenCenter - (defaultFont.stringWidth("development build") >>> 1) - offsetx;
        y = (yScreenCenter - (defaultFont.getHeight() >>> 1)) + (defaultFont.getHeight() >>> 2);
        g.drawString("development build", x, y, Graphics.TOP | Graphics.LEFT);
        
        g.setColor(preColor);
    }
    public void drawMenu(Graphics g) {
        g.drawImage(bg, 0, 0, 0);
        g.setColor(0x87CEEB);
        g.fillRoundRect(0, -10, getWidth(), 50, 10, 10);
        g.setColor(0x448EE4);
        g.drawRoundRect(2, (getHeight() - 50)/3, getWidth() - 4, (getHeight() - 50)/3, 20, 20);
        g.drawRoundRect(2, 2 * (getHeight() - 50)/3 + 2, getWidth() - 4, (getHeight() - 50)/3, 20, 20);
        g.drawRoundRect(2, (getHeight() - 50) + 4, getWidth() - 4, (getHeight() - 50)/3, 20, 20);
        g.drawString("Select a song", (getWidth() - defaultFont.stringWidth("Select a song")) >>> 1, 10, 0);
        
    }
    /**
     * paint
     */
    public void paint(Graphics g) {
        g.setColor(0x000000); // Set color black
        g.fillRect(0, 0, this.getWidth(), this.getHeight()); // Draw that background 

        if (loadGameNextFrame) {
            try {
                runner.join();
                this.display.setCurrent(new Game(bgBlur));
                return;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        
        if (!introAnimationDone) {
            g.drawImage(bgBlur, 0, 0, 0);
            drawDisclaimerText(g);
            return;
        }
        
        
        if (gameLoadAnimation && System.currentTimeMillis() - stageOffset > 0) {
            g.drawImage(bgBlur, 0, 0, 0);
            drawGrid(g, (System.currentTimeMillis() - stageOffset)/1000f);
            if ((System.currentTimeMillis() - stageOffset)/1000f > 1) {
                loadGameNextFrame = true;
            }
            
        } else {
            drawMenu(g);
        }

        
    }
    void drawGrid(Graphics g, float multiplier) {
        int precolor = g.getColor();
        g.setColor(0xFFFFFF);
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int maxWidth = (initialWidth + (col * 50));
                int minWidth = (this.getWidth() / 2 - 22);
                int maxHeight = (initialHeight + (row * 50));
                int minHeight = (this.getHeight()/ 2 - 22);
//                g.drawRect((int)(multiplier * (maxWidth - minWidth) + minWidth), (int)(multiplier * (maxHeight - minHeight) + minHeight), 45, 45);
                g.drawRoundRect((int)(multiplier * (maxWidth - minWidth) + minWidth), (int)(multiplier * (maxHeight - minHeight) + minHeight), 45, 45, (int)((1/multiplier)-1), (int)((1/multiplier)-1));
            }
        }
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
            gameLoadAnimation = true;
            stageOffset = System.currentTimeMillis();
        }
    }

    public void run() {
        while (!loadGameNextFrame) {
            repaint();
        }
    }
    
}
