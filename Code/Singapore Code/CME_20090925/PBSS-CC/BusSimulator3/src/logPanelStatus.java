
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class logPanelStatus extends JPanel{

    private logPanel logpane;
    private Font font1;
    private JPanel barPanel;
    private JLabel bar;

    public logPanelStatus(){

        logpane = new logPanel();

        font1 = new Font("Helvetica", Font.PLAIN,  12);
        barPanel = new JPanel();
        bar = new JLabel("Log Window");
        barPanel.add(bar);
        setLayout(new BorderLayout());
        Color c = new Color(146, 207, 213);
        barPanel.setBackground(c);
        bar.setFont(font1);

        add(barPanel,BorderLayout.NORTH);
        add(logpane,BorderLayout.CENTER);

        Border thickBorder = new LineBorder(Color.DARK_GRAY);
        setBorder(thickBorder);

        
    }
}
