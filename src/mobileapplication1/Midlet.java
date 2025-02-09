/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import javax.microedition.midlet.*;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/**
 * @author alice
 */
public class Midlet extends MIDlet {
    private Command back;
    private Display display;

    public void startApp() {
        back = new Command("Back", Command.BACK, 0);
        MIDPCanvas canvas = new MIDPCanvas();
//        game.start();
//        game.addCommand(back);
//        game.setCommandListener(new CommandListener(){
//        public void commandAction(Command c, Displayable s) {
//        game.stop();
//        notifyDestroyed();
//        }
//        });
        display = Display.getDisplay(this);
        display.setCurrent(canvas);  
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
