package cme.dataengine;

import java.util.Vector;
import javax.swing.DefaultListModel;
import cme.interfaces.DataEngineInterface;
import javax.swing.JOptionPane;

/**
 * DataEngine class for listing maps stored in the database and local repository
 * @author Poon Wen Jie
 */
public class DataEngine implements DataEngineInterface {

    /**
     * Performs a refresh on the database map list "dbListModel"
     * @param dbListModel ListModel holding the list of maps in the database
     * @return returns true if database is online, false if database offline
     */
    public boolean populateDbMapList(DefaultListModel dbListModel) {
        dbListModel.removeAllElements();
        if (dbListModel.getSize() == 0) {
            String[] dbList = RMI_Client.getMapList();
            if(dbList == null) {
                JOptionPane.showMessageDialog(null, "Unable to connect to Database");
                return false;
            }
            else {
                for (int i = 0; i < dbList.length; i++) {
                    dbListModel.addElement(dbList[i]);
                }
            }
        }
        return true;
    }

    /**
     * Performs a refresh on the local map list "localListModel"
     * @param localListModel ListModel holding the list of maps in the local repository
     */
    public void populateLocalMapList(DefaultListModel localListModel) {
        localListModel.removeAllElements();
        if (localListModel.getSize() == 0) {
            Vector maps = cme.dataengine.FlatFileEngine.returnMapList();
            for (int i = 0; i < maps.size(); i++) {
                localListModel.addElement((String) maps.get(i));
            }
        }
    }
}
