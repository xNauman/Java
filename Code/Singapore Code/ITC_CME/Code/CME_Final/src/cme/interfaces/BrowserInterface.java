package cme.interfaces;

import cme.gui.CityMapEditor;

/**
 * Interface class for BrowserControl
 * @author Wong Zhen Cong
 */
public interface BrowserInterface {
    public void captureGoogleMap(CityMapEditor Cme);
    public boolean checkRequirements();
}