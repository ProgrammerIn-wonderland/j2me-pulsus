/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author alice
 */
public class Utils {
    final public static int TOP_LEFT = 49;
    final public static int TOP_MIDDLE = 50;
    final public static int TOP_RIGHT = 51;
    final public static int MIDDLE_LEFT = 52;
    final public static int MIDDLE_MIDDLE = 53;
    final public static int MIDDLE_RIGHT = 54;
    final public static int BOTTOM_LEFT = 55;
    final public static int BOTTOM_MIDDLE = 56;
    final public static int BOTTOM_RIGHT = 57;
    
    public static int[] getKeyPosition(int keyCode) {
        int x;
        int y;
        switch (keyCode) {
            case 114:
            case TOP_LEFT:
                x = 0;
                y = 0;
                break;
            case 116:
            case TOP_MIDDLE:
                x = 1;
                y = 0;
                break;
            case 121:
            case TOP_RIGHT:
                x = 2;
                y = 0;
                break;
            case 102:
            case MIDDLE_LEFT:
                x = 0;
                y = 1;
                break;
            case 103:
            case MIDDLE_MIDDLE:
                x = 1;
                y = 1;
                break;
            case 104:
            case MIDDLE_RIGHT:
                x = 2;
                y = 1;
                break;
            case 99:
            case BOTTOM_LEFT:
                x = 0;
                y = 2;
                break;
            case 118:
            case BOTTOM_MIDDLE:
                x = 1;
                y = 2;
                break;
            case 98:
            case BOTTOM_RIGHT:
                x = 2;
                y = 2;
                break;
            default:
                x = -1;
                y = -1;
                break;
        }
        return new int[]{x, y};
    }
    
    // Strings in J2ME suck and are painful. They don't have many utility methods.
    // This exists to make our life marginally easier
    // - Rafflesia
    public static String[] split(String input, char delimiter) {
        Vector stringParts = new Vector();
        
        char[] inputChars = input.toCharArray();
        StringBuffer currentString = new StringBuffer();
        int lastIndex = 0;
        int index = input.indexOf(delimiter);
        
        while (index != -1) {
            stringParts.addElement(input.substring(lastIndex, index));
            lastIndex = index + 1;
            index = input.indexOf(delimiter, lastIndex);
            System.out.println(index);
        }
        stringParts.addElement(input.substring(lastIndex));
        
        String[] returnParts = new String[stringParts.size()];
        for (int i = 0; i < stringParts.size(); i++) {
            returnParts[i] = (String)stringParts.elementAt(i);
        }
        return returnParts;
    }
    
    // Not tested yet -- Remove this comment when you do
    public static String readLine(InputStreamReader isr) throws IOException {
        StringBuffer fileContentBuffer = new StringBuffer();
        char c = (char) isr.read();
        System.out.println("Beginning readthing");
        while ((int)c != -1 && c != '\n' && (int)c != 65535) {
//            System.out.println("Reading: ");
//            System.out.println((int)c);
            fileContentBuffer.append(c);
            c = (char) isr.read();
        }
        return fileContentBuffer.toString();
    }
    public static Image renderPerspective3DBackground(int width, int height) {
        Image background = Image.createImage(width, height);
        Graphics g = background.getGraphics();
        g.setColor(0x1a1832);
        g.fillRect(0, 0, width, height);
        g.setColor(0x494255);
        g.drawRect((((width - 175 )/ 2)), (((height - 175 )/ 2)), 175, 175);
        g.drawRect((((width - 125 )/ 2)), (((height - 125 )/ 2)), 125, 125);
        g.drawRect((((width - 85 )/ 2)), (((height - 85 )/ 2)), 85, 85);
        
        g.drawLine((((width - 155 )/ 2)) - 1000, (((height - 155 )/ 2)) - 1000, (((width - 85 )/ 2)), (((height - 85 )/ 2)));
        g.drawLine((((width - 155 )/ 2)) + 155 + 1000, (((height - 155 )/ 2)) - 1000, (((width - 85 )/ 2)) + 85, (((height - 85 )/ 2)));
        g.drawLine((((width - 155 )/ 2)) - 1000, (((height - 155 )/ 2)) + 155 + 1000, (((width - 85 )/ 2)), (((height - 85 )/ 2)) + 85);
        g.drawLine((((width - 155 )/ 2)) + 155 + 1000, (((height - 155 )/ 2)) + 155 + 1000, (((width - 85 )/ 2)) + 85, (((height - 85 )/ 2)) + 85);
        g.drawLine(width / 2, 0, width / 2, (((height - 85 )/ 2)));
        g.drawLine(width / 2, height, width / 2, (((height - 85 )/ 2)) + 85);
        g.drawLine(0, height / 2, (((width - 85 )/ 2)), (((height - 85 )/ 2)) + 43);
        g.drawLine(width, height / 2, (((width - 85 )/ 2)) + 85, (((height - 85 )/ 2)) + 43);
        
        int[] particlesData = new int[height * width];
        Random rand = new Random();

        int[] pastelColors = {0xFFa79be1, 0xFF8f92eb, 0xFFc99cdd, 0xFFc6d5d1};
        // 50 random small pixels
        for (int i = 0; i < 50; i++) {
            particlesData[rand.nextInt(particlesData.length)] = pastelColors[rand.nextInt(pastelColors.length)];
        }
        
        // 10 random big particles
        for (int i = 0; i < 10; i++) {
            int start = rand.nextInt(particlesData.length);
            int color = pastelColors[rand.nextInt(pastelColors.length)];
            particlesData[start] = color;
            particlesData[start + 1] = color;
            particlesData[start + width] = color;
            particlesData[start + width + 1] = color;
        }
        g.drawRGB(particlesData, 0, width, 0, 0, width, height, true);
        return background;
    }
}
