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
            while (!"".equals(line = Utils.readLine(isr))) {
                System.out.println(line);
                String[] parts = Utils.split(line, ',');
                long startTime = Long.parseLong(parts[0]);
                long time = Long.parseLong(parts[1]);
                int lane = Integer.parseInt(parts[2]);
                notes.addElement(new Note(startTime, time, lane));
            }
            System.out.println("done");
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }
}
