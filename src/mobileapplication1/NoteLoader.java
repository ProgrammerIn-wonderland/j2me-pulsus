/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

/**
 *
 * @author alice
 */
import java.io.*;
import java.util.*;
import javax.microedition.lcdui.List;

public class NoteLoader {
    // Strings in J2ME suck and are painful. They don't have many utility methods.
    // This exists to make our life marginally easier
    // - Rafflesia
    private static String[] split(String input, char delimiter) {
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
    private static String readLine(InputStreamReader isr) throws IOException {
        StringBuffer fileContentBuffer = new StringBuffer();
        char c = (char) isr.read();
        while ((int)c != -1 && c != '\n') {
            fileContentBuffer.append(c);
            c = (char) isr.read();
        }
        return fileContentBuffer.toString();
    }
    
    // I really don't know what I'm doing here, but I'm sort of hoping we land 
    // on a file format which is just time,lane\ntime,lane\ntime,lane etc without
    // stuff like holding notes.. Some phones dont even support allowing us to
    // read multiple keys at once. - Rafflesia
    public Vector loadNotes(String path) {
        Vector notes = new Vector();
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) return notes;

            InputStreamReader isr = (new InputStreamReader(is));
  
            String line;
            while ((line = readLine(isr)) != null) {
                System.out.println(line);
                String[] parts = split(line, ',');
                int time = Integer.parseInt(parts[0]);
                int startTime = time - 44000;
                int lane = Integer.parseInt(parts[1]);
                notes.addElement(new Note(startTime, time, lane));
            }
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }
}
