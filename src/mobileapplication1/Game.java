/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.protocol.DataSource;

/**
 * @author alice
 */
public class Game extends Canvas implements CommandListener, Runnable {
    final public int TOP_LEFT = 49;
    final public int TOP_MIDDLE = 50;
    final public int TOP_RIGHT = 51;
    final public int MIDDLE_LEFT = 52;
    final public int MIDDLE_MIDDLE = 53;
    final public int MIDDLE_RIGHT = 54;
    final public int BOTTOM_LEFT = 55;
    final public int BOTTOM_MIDDLE = 56;
    final public int BOTTOM_RIGHT = 57;
    int[] pastelColors = {0xf0cdd5, 0xdfd6e4, 0xd4e7d3, 0xa89bd3, 0xd774cd, 0x7d3ac8, 0x6157ca, 0x8f94cc, 0xf0ced5};
    boolean safeToRender = true;
    boolean safeToWriteCalculations = true;
    Graphics graphics;
    int initialWidth;
    int initialHeight;
    boolean keyStates[][] =  {
        {false, false, false},
        {false, false, false},
        {false, false, false}
    };
    Vector notes = new Vector();
    Player hitSound;
    
    
    // Each element here represents a square on the grid
    // Between 0 - 44 (full square) is the range of values, 44 being the tile is almost ready.
    // This will be painted on every paint and the values wll be managed by the music thread
    // -- Possibly -- Values can go over 44 for overage (user is too slow) up to 100
    int currentGrid [][] =  {
        {44, 0, 0},
        {0, 30, 0},
        {0, 0, 0}
    };
    int activeNote [][] =  {
        {-1, -1, -1},
        {-1, -1, -1},
        {-1, -1, -1}
    };
    int lastClaimedNote [][] =  {
        {-1, -1, -1},
        {-1, -1, -1},
        {-1, -1, -1}
    };
    
    long score = 0;
    
    Player player;
    long millioffset = 0;
    Image bg;
    /**
     * constructor
     */
    public Game(Image bg) {
        try {
            this.bg = bg;
            // Set up this canvas to listen to command events
            setCommandListener(this);
            
            initialHeight = (((this.getHeight() - 145 )/ 2) );
            initialWidth =  (((this.getWidth() - 145 )/ 2));
            
            InputStream is = getClass().getResourceAsStream("/hit.wav");
            byte[] hitSample = new byte[25580];
//            System.out.println(is.read(hitSample));
            hitSound = Manager.createPlayer(is, "audio/mpeg"); // Use "audio/x-wav" for WAV files
            hitSound.realize();
            hitSound.prefetch();
            // Add the Exit command
            // addCommand(new Command("Exit", Command.EXIT, 1));
            playMusic("/music.mp3");
            millioffset = System.currentTimeMillis();
            Thread runner = new Thread(this);
            runner.start();
            notes = (new NoteLoader()).loadNotes("/default_chart.txt");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void playMusic(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            player = Manager.createPlayer(is, "audio/x-wav"); // Use "audio/x-wav" for WAV files
            player.realize();
            player.prefetch();
            player.start();  // Play the music
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * paint
     */
    public void paint(Graphics g) {
        safeToWriteCalculations = false;
        
        this.graphics = g;
        g.setColor(0);
        
        g.drawImage(bg, 0, 0, 0);

        
        drawCurrentNotes(g);
        drawDecayNotes(g);
        drawPressedKeys(g);
        drawGrid(g);
        
        g.setColor(0xFF0000);

//        long currentTime = ((player.getMediaTime() * 0x418937L) >>> 32);
        long currentTime = ((System.currentTimeMillis() - millioffset));
        g.drawString("Score: " + Long.toString(score), 0, 0, Graphics.TOP | Graphics.LEFT);
        safeToWriteCalculations = true;
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
    void resetGrid() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                currentGrid[row][col] = 0;
                activeNote[row][col] = -1;
            }
        }
    }
    void drawPressedKeys(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0x0000FF);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (keyStates[row][col])
                    drawSquare(g, initialWidth + (col * 50), initialHeight + (row * 50), 44, -1);
            }
        }
        g.setColor(precolor);
    }
    void drawDecayNotes(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0xFF00FF);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (currentGrid[row][col] < 0)
                    drawSquare(g, initialWidth + (col * 50), initialHeight + (row * 50), 44+currentGrid[row][col], -1);
            }
        }
        g.setColor(precolor);
    }
    void drawCurrentNotes(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0xFF0000);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (currentGrid[row][col] > 0) {
                    drawSquare(g, initialWidth + (col * 50), initialHeight + (row * 50), currentGrid[row][col], pastelColors[col]);
                }
            }
        }
        g.setColor(precolor);
    }
    
    void drawGrid(Graphics g) {
        int precolor = g.getColor();
        g.setColor(0xFFFFFF);
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                g.drawRect(initialWidth + (col * 50), initialHeight + (row * 50), 45, 45);
            }
        }

    }
    /**
     * Called when a key is pressed.
     */
    protected void keyPressed(int keyCode) {
        int[] keys = Utils.getKeyPosition(keyCode);
        int x = keys[0];
        int y = keys[1];
        if (x == -1) 
            return;
        
        if (activeNote[y][x] != -1) {
            score += Math.abs(currentGrid[y][x]);
            lastClaimedNote[y][x] = activeNote[y][x];
            activeNote[y][x] = -1;
            
        }
        try {
            System.out.println("State: " + hitSound.getState());
//            InputStream is = getClass().getResourceAsStream("/hit.wav");
//            byte[] hitsound = new byte[is.available()];
//            is.read(hitsound, 0, is.available());
//            
//            hitSound = Manager.createPlayer(new DataSource(is), "audio/x-wav");
//            hitSound.realize();
                
//            hitSound.stop();
//            hitSound.prefetch();
            hitSound.setMediaTime(0);
            
            hitSound.start();
//            hitSound.deallocate();
//            hitSound.realize();
            System.out.println("Hit sound played");
        } catch (Exception ex) {
            System.out.println("Somethings fuckkkkeddd");
            ex.printStackTrace();
        }

        keyStates[y][x] = true;
    }

    /**
     * Called when a key is released.
     */
    protected void keyReleased(int keyCode) {
        int[] keys = Utils.getKeyPosition(keyCode);
        int x = keys[0];
        int y = keys[1];
        if (x == -1) 
            return;
        
        keyStates[y][x] = false;
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

    long lastMilisecond = 0;
    int starteri = 0;
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            repaint(); // Ask the system to redraw the screen
            try {
                
                
                // Uncomment this line if your device supports accurate media time
//                long currentTime = ((player.getMediaTime() * 0x418937L) >>> 32);

                // Comment this section out if your device supports accurate media time
                long currentTime = ((System.currentTimeMillis() - millioffset)); // This is a totally inferior method of measuring time but is required on blackberries
                
                // Fixer for when the manual time measurement gets offcourse
                long computedFixer = (((player.getMediaTime() + 1) * 0x418937L) >>> 32);
                if (currentTime < computedFixer) {
                    millioffset = System.currentTimeMillis() - computedFixer;
                    currentTime = computedFixer;
                }
                
                if ((lastMilisecond - computedFixer == 1000)) {
                    millioffset = System.currentTimeMillis() - computedFixer;
                }
                lastMilisecond = computedFixer;
                safeToRender = false;
                while (!safeToWriteCalculations) {
                    Thread.sleep(1);
                }
                resetGrid();
                // End of comment this section out if your device supports accurate media time 
                for (int i = starteri; i < notes.size(); i++) {
                    int lastoobi = 0;
                    Note note = (Note) notes.elementAt(i);
                    if (note.startTime < currentTime && currentTime < note.safetyTime && lastClaimedNote[note.gridX][note.gridY] < i) {
                        if (currentTime < note.time) {
                            if (currentGrid[note.gridX][note.gridY] <= 0) {
                                currentGrid[note.gridX][note.gridY] = (int) ( ((44 * (currentTime - note.startTime)) / (note.time - note.startTime)));
                                activeNote[note.gridX][note.gridY] = i;
                            } else {
                                System.out.println("Weird fucked up thing with distance of " + ((note.time - note.startTime) - (currentTime - note.startTime)) );
                            }


                        } else {
                            currentGrid[note.gridX][note.gridY] = (int) -( ((44 * (currentTime - note.time)) / (note.safetyTime - note.time)));
                            activeNote[note.gridX][note.gridY] = i;
                        }
                    } else if (note.startTime > currentTime) {
                        break;
                    } else if (note.time < currentTime) {
                        lastoobi = i;
                    } // Work in progress optimization
                    starteri = lastoobi;
                }
                safeToRender = true;
                Thread.sleep(16); // Adjust polling rate (16ms)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
