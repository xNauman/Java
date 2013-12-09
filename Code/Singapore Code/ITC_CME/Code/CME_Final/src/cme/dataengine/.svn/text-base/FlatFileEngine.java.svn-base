package cme.dataengine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import javax.swing.JOptionPane;
import holder.Map;
import cme.util.Constants;

/**
 * FlatFileEngine for flat file operations. Reads/writes map objects from/to files.
 * @author Poon Wen Jie
 */
public abstract class FlatFileEngine {

    /**
     * Returns a list of maps in the local repository
     * @return Vector list containing map names that are in the "map" folder relative to the execution folder
     */
    public static Vector returnMapList() {
        String[] maps = new String[0];
        Vector mapList = new Vector();
        if (Constants.MAPDIR.isDirectory()) {
            maps = Constants.MAPDIR.list();
        }
        for (int i = 0; i < maps.length; i++) {
            if (maps[i].endsWith(Constants.EXTENSION)) {
                mapList.add(maps[i].substring(0, maps[i].lastIndexOf(Constants.EXTENSION)));
            }
        }
        return mapList;
    }

    /**
     * Saves the map to a file with .cme extension in the "map" folder relative to the execution folder.
     * @param map map to be be saved
     * @param currentlyOpened flag set to true if map is currently open, disables prompt to overwrite on save
     * @return returns true if map is successfully saved to file, false if file save is cancelled or disrupted.
     */
    public static boolean saveMap(Map map, boolean currentlyOpened) {
        if (!Constants.MAPDIR.exists()) {
            Constants.MAPDIR.mkdir();
        }
        File file = new File(Constants.MAPDIR.getAbsolutePath() + Constants.FILESEPARTOR + map.getMapID() + Constants.EXTENSION);
        String filename = map.getMapID();
        if (file.exists() && !currentlyOpened) {
            int n = JOptionPane.showOptionDialog(null, "Map \"" + filename + "\" already exists. Overwrite?", "Confirm file replace", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                writeToFile(map, file);
                return true;
            }
        } else {
            writeToFile(map, file);
            return true;
        }
        return false;
    }

    /**
     * Validates whether a map has been selected from the list
     * @param ID ID of selected map in the ListModel
     * @return returns true if map is selected, false if no map is selected
     */
    public static boolean mapSelected(String ID){
        if (ID == null) {
            JOptionPane.showMessageDialog(null,
                    "No map selected",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            return true;
        }
    }

    /**
     * Renames a map, updates the MapID attribute and filename.
     * @param mapID name of map to be renamed
     */
    public static void renameMap(String mapID) {
        if(!mapSelected(mapID)) return;
        String newMapID = JOptionPane.showInputDialog("Enter new map name:");
        if (newMapID != null && !newMapID.equals("") && !newMapID.contains(" ")) {
            Map map = readMap(newMapID);
            map = readMap(mapID);
            map.setMapID(newMapID);
            boolean saveMapSuccessful = false;
            saveMapSuccessful = saveMap(map, false);
            if (saveMapSuccessful) {
                deleteMap(mapID);
                System.out.println("Map successfully renamed from " + mapID + " to " + newMapID);
            }
        } else {
            if (newMapID != null) {
                if (newMapID.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid ID",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(null,
                            "Spaces not allowed",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Map not renamed",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes the specified map from the local repository
     * @param mapID ID of map to be deleted
     */
    public static void deleteMap(String mapID) {
        if(!mapSelected(mapID)) return;
        File file = new File(Constants.MAPDIR.getAbsolutePath() + Constants.FILESEPARTOR + mapID + Constants.EXTENSION);
        if (file.exists()) {
            file.delete();
        }
    }

    private static void writeToFile(Map map, File file) {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            writer.writeObject(map); //write the map object
            writer.close();
            System.out.println("Map saved locally on: " + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads the map object from the file in the local repository
     * @param mapID ID of map to be retrived from file
     * @return returns map of selected Map ID
     */
    public static Map readMap(String mapID) {
        if(!mapSelected(mapID)) return null;
        Map map = null;
        File file = new File(Constants.MAPDIR.getAbsolutePath() + Constants.FILESEPARTOR + mapID + Constants.EXTENSION);
        if (!file.exists()) {
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            map = (Map) ois.readObject();
            ois.close();
            System.out.println("Map loaded locally from: " + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}
