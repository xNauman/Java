
import javax.swing.*;
import java.awt.*;

public class rightPanel extends JPanel {

    private statusPanel statuspane;
    private logPanelStatus logpane;
    private clockPanel clockpane;
    private JPanel logStatusPane;

    public rightPanel() {
        setLayout(new BorderLayout());
        statuspane = new statusPanel();
        logpane = new logPanelStatus();
        clockpane = new clockPanel();

        logStatusPane = new JPanel();
        logStatusPane.setLayout(new BorderLayout());
        logStatusPane.add(logpane, BorderLayout.CENTER);
        logStatusPane.add(statuspane, BorderLayout.SOUTH);

        add(clockpane,BorderLayout.NORTH);
        add(logStatusPane,BorderLayout.CENTER);
        setOpaque(false);

    }
}
