/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

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
}
