import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class clockPanel extends JPanel{

    private clock clk;
    private JLabel statusBar;
    private JPanel status;
    private Font font1;

    public clockPanel(){
        
        font1 = new Font("Helvetica", Font.PLAIN,  12);
        clk = new clock(1);
        setLayout(new BorderLayout());
        statusBar = new JLabel("Time");
        Border thickBorder = new LineBorder(Color.black);
        setBorder(thickBorder);

        status = new JPanel();
        status.add(statusBar);
        Color c = new Color(146, 207, 213);
        status.setBackground(c);
        
        statusBar.setFont(font1);
        add(status,BorderLayout.NORTH);
        add(clk,BorderLayout.CENTER);

        setBackground(Color.WHITE);
        
    }

}