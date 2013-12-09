package cme.interfaces;

import javax.swing.DefaultListModel;

/**
 * Interface class for DataEngineControl
 * @author Poon Wen Jie
 */
public interface DataEngineInterface {

    public boolean populateDbMapList(DefaultListModel databaseListModel);
    public void populateLocalMapList(DefaultListModel localListModel);
}
