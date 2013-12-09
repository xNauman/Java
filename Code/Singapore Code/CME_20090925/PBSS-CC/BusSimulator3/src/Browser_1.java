
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingConstants;
import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

import org.jdesktop.jdic.browser.*;

/**
 *
 * @author Kevin
 */
public class Browser_1 extends JPanel {

    public static ImageIcon browseRight = new ImageIcon(Browser_1.class.getResource("images/Right.gif"));
    BorderLayout browserLayOut = new BorderLayout();
    JToolBar browserToolBar = new JToolBar();
    JButton stopButton = new JButton("Stop", new ImageIcon(getClass().getResource("images/Stop.png")));
    JButton refreshButton = new JButton("Refresh", new ImageIcon(getClass().getResource("images/Reload.png")));
    JButton forwardButton = new JButton("Forward", new ImageIcon(getClass().getResource("images/Forward.png")));
    JButton backButton = new JButton("Back", new ImageIcon(getClass().getResource("images/Back.png")));
    JPanel addressPanel = new JPanel();
    JLabel addressLabel = new JLabel();
    JTextField addTextField = new JTextField();
    JButton goButton = new JButton();
    JPanel addToolBarPanel = new JPanel();
    BrowserStatusBar statusBar = new BrowserStatusBar();
    JPanel browserPanel = new JPanel();
    WebBrowser webBrowser;

    public Browser_1() {

        setOpaque(false);
        try {
            browserInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) {
    try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {}

    JFrame frame = new JFrame("Bus Simulator Browser");

    Container contentPane = frame.getContentPane();

    contentPane.setLayout(new GridLayout(1, 1));
    contentPane.add(new Browser_1());

    frame.addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
    System.exit(0);
    }
    });

    frame.pack();
    frame.setVisible(true);
    }*/
    private void browserInit() throws Exception {

        String strHomeURL = "http://www.google.com";
        this.setLayout(browserLayOut);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.setPreferredSize(new Dimension(screenSize.width * 9 / 10,
                screenSize.height * 8 / 10));

        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        /*
        addressLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        addressLabel.setToolTipText("");
        addressLabel.setText(" URL: ");

        goButton.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0,
        2, 0, 2),
        new EtchedBorder()));
        goButton.setMaximumSize(new Dimension(60, 25));
        goButton.setMinimumSize(new Dimension(60, 25));
        goButton.setPreferredSize(new Dimension(60, 25));
        goButton.setToolTipText("Load the given URL");
        goButton.setIcon(browseRight);
        goButton.setText("GO");
        goButton.addActionListener(new Browser_goButton_actionAdapter(this));
        addressPanel.setLayout(new BorderLayout());

        addTextField.addActionListener(new Browser_addTextField_actionAdapter(this));
        backButton.setToolTipText("Go back one page");
        backButton.setHorizontalTextPosition(SwingConstants.TRAILING);
        backButton.setEnabled(false);
        backButton.setMaximumSize(new Dimension(75, 27));
        backButton.setPreferredSize(new Dimension(75, 27));
        backButton.addActionListener(new Browser_backButton_actionAdapter(this)); // here
        forwardButton.setToolTipText("Go forward one page");
        forwardButton.setEnabled(false);
        forwardButton.addActionListener(new Browser_forwardButton_actionAdapter(this));
        refreshButton.setToolTipText("Reload current page");
        refreshButton.setEnabled(true);
        refreshButton.setMaximumSize(new Dimension(75, 27));
        refreshButton.setMinimumSize(new Dimension(75, 27));
        refreshButton.setPreferredSize(new Dimension(75, 27));
        refreshButton.addActionListener(new Browser_refreshButton_actionAdapter(this));
        stopButton.setToolTipText("Stop loading this page");
        stopButton.setVerifyInputWhenFocusTarget(true);
        stopButton.setText("Stop");
        stopButton.setEnabled(true);
        stopButton.setMaximumSize(new Dimension(75, 27));
        stopButton.setMinimumSize(new Dimension(75, 27));
        stopButton.setPreferredSize(new Dimension(75, 27));
        stopButton.addActionListener(new Browser_stopButton_actionAdapter(this));
        addressPanel.add(addressLabel, BorderLayout.WEST);
        addressPanel.add(addTextField, BorderLayout.CENTER);
        addressPanel.add(goButton, BorderLayout.EAST);
        addressPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEtchedBorder(),
        BorderFactory.createEmptyBorder(2, 0, 2, 0)));

        browserToolBar.setFloatable(false);
        browserToolBar.add(backButton, null);
        browserToolBar.add(forwardButton, null);
        browserToolBar.addSeparator();
        browserToolBar.add(refreshButton, null);
        browserToolBar.add(stopButton, null);
        browserToolBar.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEtchedBorder(),
        BorderFactory.createEmptyBorder(2, 2, 2, 0)));

        addToolBarPanel.setLayout(new BorderLayout());
        addToolBarPanel.add(addressPanel, BorderLayout.CENTER);
        addToolBarPanel.add(browserToolBar, BorderLayout.WEST);
        addToolBarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));

        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        statusBar.lblDesc.setText("Bus simulator Browser");
         */
        try {
            webBrowser = new WebBrowser(new URL(strHomeURL));
            // Print out debug messages in the command line.
            //webBrowser.setDebug(true);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            return;
        }

        webBrowser.addWebBrowserListener(new WebBrowserListener() {

            public void downloadStarted(WebBrowserEvent event) {
                updateStatusInfo("Loading started.");
            }

            public void downloadCompleted(WebBrowserEvent event) {
                backButton.setEnabled(webBrowser.isBackEnabled());
                forwardButton.setEnabled(webBrowser.isForwardEnabled());

                updateStatusInfo("Loading completed.");

                URL currentUrl = webBrowser.getURL();

                if (currentUrl != null) {
                    addTextField.setText(currentUrl.toString());
                }
            }

            public void downloadProgress(WebBrowserEvent event) {
                // updateStatusInfo("Loading in progress...");
            }

            public void downloadError(WebBrowserEvent event) {
                updateStatusInfo("Loading error.");
            }

            public void documentCompleted(WebBrowserEvent event) {
                updateStatusInfo("Document loading completed.");
            }

            public void titleChange(WebBrowserEvent event) {
                updateStatusInfo("Title of the browser window changed.");
            }

            public void statusTextChange(WebBrowserEvent event) {
                // updateStatusInfo("Status text changed.");
            }

            public void windowClose(WebBrowserEvent event) {
                updateStatusInfo("Closed by script.");
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                        webBrowser,
                        "The webpage you are viewing is trying to close the window.\n Do you want to close this window?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE)) {
                    System.exit(0);
                }
            }
        });

        browserPanel.setLayout(new BorderLayout());
        browserPanel.add(webBrowser, BorderLayout.CENTER);

        this.add(addToolBarPanel, BorderLayout.NORTH);
        this.add(statusBar, BorderLayout.SOUTH);
        this.add(browserPanel, BorderLayout.CENTER);
    }

    void jBackButton_actionPerformed(ActionEvent e) {
        webBrowser.back();
    }

    void backButton_actionPerformed(ActionEvent e) {
        webBrowser.back();
    }

    void forwardButton_actionPerformed(ActionEvent e) {
        webBrowser.forward();
    }

    void goButton_actionPerformed(ActionEvent e) {
        loadURL();
    }

    void addTextField_actionPerformed(ActionEvent e) {
        loadURL();
    }

    void stopButton_actionPerformed(ActionEvent e) {
        webBrowser.stop();
    }

    void refreshButton_actionPerformed(ActionEvent e) {
        webBrowser.refresh();
    }

    void updateStatusInfo(String statusMessage) {
        statusBar.lblStatus.setText(statusMessage);
    }

    void loadURL() {
        String inputValue = addTextField.getText();

        if (inputValue == null) {
            JOptionPane.showMessageDialog(this, "The given URL is NULL:",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            // Check if the text value is a URL string.
            URL curUrl = null;

            try {
                // Check if the input string is a local path by checking if it starts
                // with a driver name(on Windows) or root path(on Unix).
                File[] roots = File.listRoots();

                for (int i = 0; i < roots.length; i++) {
                    if (inputValue.toLowerCase().startsWith(roots[i].toString().toLowerCase())) {
                        File curLocalFile = new File(inputValue);

                        curUrl = curLocalFile.toURL();
                        break;
                    }
                }

                if (curUrl == null) {
                    // Check if the text value is a valid URL.
                    try {
                        curUrl = new URL(inputValue);
                    } catch (MalformedURLException e) {
                        if (inputValue.toLowerCase().startsWith("ftp.")) {
                            curUrl = new URL("ftp://" + inputValue);
                        } else if (inputValue.toLowerCase().startsWith("gopher.")) {
                            curUrl = new URL("gopher://" + inputValue);
                        } else {
                            curUrl = new URL("http://" + inputValue);
                        }
                    }
                }

                webBrowser.setURL(curUrl);

                // Update the address text field, statusbar, and toolbar info.
                updateStatusInfo("Loading " + curUrl.toString() + " ......");

            } catch (MalformedURLException mue) {
                JOptionPane.showMessageDialog(this,
                        "The given URL is not valid:" + inputValue, "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}

class Browser_addTextField_actionAdapter implements java.awt.event.ActionListener {

    Browser_1 adaptee;

    Browser_addTextField_actionAdapter(Browser_1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.addTextField_actionPerformed(e);
    }
}

class Browser_goButton_actionAdapter implements java.awt.event.ActionListener {

    Browser_1 adaptee;

    Browser_goButton_actionAdapter(Browser_1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.goButton_actionPerformed(e);
    }
}

class Browser_backButton_actionAdapter implements java.awt.event.ActionListener {

    Browser_1 adaptee;

    Browser_backButton_actionAdapter(Browser_1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.backButton_actionPerformed(e);
    }
}

class Browser_forwardButton_actionAdapter implements java.awt.event.ActionListener {

    Browser_1 adaptee;

    Browser_forwardButton_actionAdapter(Browser_1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.forwardButton_actionPerformed(e);
    }
}

class Browser_refreshButton_actionAdapter implements java.awt.event.ActionListener {

    Browser_1 adaptee;

    Browser_refreshButton_actionAdapter(Browser_1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.refreshButton_actionPerformed(e);
    }
}

class Browser_stopButton_actionAdapter implements java.awt.event.ActionListener {

    Browser_1 adaptee;

    Browser_stopButton_actionAdapter(Browser_1 adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.stopButton_actionPerformed(e);
    }
}

class BrowserStatusBar extends Box {

    public JLabel lblStatus, lblDesc;

    public BrowserStatusBar() {
        super(BoxLayout.X_AXIS);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        // Add the JLabel displaying the selected object numbers.
        lblStatus = new JLabel("Status:", SwingConstants.LEADING);
        lblStatus.setPreferredSize(new Dimension((int) (0.7 * screenSize.width),
                22));
        lblStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(lblStatus, null);

        // Add the JLabel displaying the selected object size.
        // lblSize = new JLabel("Size:", SwingConstants.LEADING);
        // lblSize.setPreferredSize(new Dimension((int)(0.2*screenSize.width), 22));
        // lblSize.setBorder(BorderFactory.createLoweredBevelBorder());
        // this.add(lblSize, null);

        // Add the JLabel displaying the description.
        lblDesc = new JLabel("Description:", SwingConstants.LEADING);
        lblDesc.setPreferredSize(new Dimension((int) (0.3 * screenSize.width),
                22));
        lblDesc.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(lblDesc, null);
    }
}

