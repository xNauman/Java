package cme.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.JPanel;
import org.jdesktop.jdic.browser.WebBrowser;
import cme.control.BrowserControl;
import cme.interfaces.BrowserInterface;

/**
 * Browser for displaying the Google Map
 * @author Wong Zhen Cong
 */
public class Browser extends JPanel implements ActionListener {

    WebBrowser webBrowser;
    CityMapEditor cme;
    BrowserInterface browserControl = new BrowserControl();

    /**
     *
     * @param cme Main Program
     */
    public Browser(CityMapEditor cme) {
        this.cme = cme;
        if(browserControl.checkRequirements()) {
            initBrowser();
            cme.browserEnable = true;
        }
    }

    private void initBrowser() {
        try {
            webBrowser = new WebBrowser((new File("gmapCustom.html").toURI()).toURL());
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(1024, 825));
            add(webBrowser, BorderLayout.CENTER);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cme.btnPanel.googleMapBtn) {
            browserControl.captureGoogleMap(cme);
        }
    }
}