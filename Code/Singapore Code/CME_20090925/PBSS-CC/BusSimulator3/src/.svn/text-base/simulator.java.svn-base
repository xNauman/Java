
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class simulator extends JPanel implements ActionListener {

    private rightPanel rightpane = new rightPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel tabLabel = new JLabel("Main Panel");
    private JPanel mainTab = new JPanel();
    private JPanel variousWindow = new JPanel();
    private JPanel actionpane = new JPanel();
    private JPanel fullpanel = new JPanel();
    private JPanel simupanel = new JPanel();
    private JButton one = new JButton();
    private JButton browser = new JButton("Browser");
    private JButton start = new JButton("Start");
    private JButton stop = new JButton("Stop");
    private int tabCounter = 0;
    private ImageIcon closeXIcon;
    private Dimension closeButtonSize;
    private BackgroundPanel back = new BackgroundPanel();

    public simulator() {
        mainTab.setOpaque(false);
        closeXIcon = new ImageIcon("images/cross.gif");
        closeButtonSize = new Dimension(
                closeXIcon.getIconWidth() + 2,
                closeXIcon.getIconHeight() + 2);


        setLayout(new BorderLayout());

        browser.addActionListener(this);
        Font font1 = new Font("Helvetica", Font.PLAIN, 12);
        actionpane.setLayout(new BorderLayout());
        JPanel statusBar = new JPanel();
        JPanel function = new JPanel();
        JLabel actionbar = new JLabel("Action");
        actionbar.setFont(font1);
        Color c = new Color(146, 207, 213);
        statusBar.setBackground(c);
        statusBar.add(actionbar);
        function.setLayout(new GridLayout(3, 1));
        function.add(browser);
        function.add(start);
        function.add(stop);

        Border thickBorder = new LineBorder(Color.BLACK);
        actionpane.setBorder(thickBorder);
        actionpane.add(statusBar, BorderLayout.NORTH);
        actionpane.add(function, BorderLayout.CENTER);

        variousWindow.setLayout(new BorderLayout());
        variousWindow.add(rightpane, BorderLayout.CENTER);
        variousWindow.add(actionpane, BorderLayout.SOUTH);
        variousWindow.setMaximumSize(new Dimension(200,1200));
        back.add(one);
        back.setOpaque(true);

        fullpanel.setLayout(new BorderLayout());
        fullpanel.add(variousWindow, BorderLayout.EAST);
        fullpanel.add(back, BorderLayout.CENTER);
        fullpanel.setOpaque(false);
        
        tabbedPane.add(fullpanel);
        add(tabbedPane);
        mainTab.add(tabLabel, BorderLayout.WEST);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, mainTab);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bus Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Add content to the window.
        frame.add(new simulator(), BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200, 900);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browser) {

            System.out.println("haha");
            final Browser_1 browser1 = new Browser_1();

            JPanel tab = new JPanel();
            tab.setOpaque(false);

            JLabel tabLabel = new JLabel("Browser ");

            JButton tabCloseButton = new JButton(closeXIcon);
            tabCloseButton.setPreferredSize(closeButtonSize);
            tabCloseButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int closeTabNumber =
                            tabbedPane.indexOfComponent(browser1);
                    tabbedPane.removeTabAt(closeTabNumber);
                }
            });

            tab.add(tabLabel, BorderLayout.WEST);
            tab.add(tabCloseButton, BorderLayout.EAST);

            tabbedPane.addTab(null, browser1);
            tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        }
    }
}
