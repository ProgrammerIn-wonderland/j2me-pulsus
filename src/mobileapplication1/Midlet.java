/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.InputStream;
import javax.microedition.midlet.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

/**
 * @author alice
 */
public class Midlet extends MIDlet {
    private Command back;
    private Display display;
    private Player player;
    
    public void startApp() {
        back = new Command("Back", Command.BACK, 0);
        MIDPCanvas canvas = new MIDPCanvas();
        display = Display.getDisplay(this);
        display.setCurrent(canvas);  
    }
    
    public void pauseApp() {
    }

    
    public void destroyApp(boolean unconditional) {
    }
}
