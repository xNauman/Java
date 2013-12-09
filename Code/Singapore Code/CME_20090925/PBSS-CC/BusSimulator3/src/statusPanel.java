
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class statusPanel extends JPanel {

    private JPanel statusBar;
    private JPanel subTeam;
    private JPanel subTeamStatus;
    private JPanel subTeamAndStatus;
    private JLabel subteamname[],status,status1[];
    private Font font1,font2;

    public statusPanel(){
        setLayout(new BorderLayout());

        statusBar = new JPanel();
        subTeam = new JPanel();
        subTeamStatus = new JPanel();
        subTeamAndStatus = new JPanel();
        font1 = new Font("Helvetica", Font.PLAIN,  12);
        font2 = new Font("Helvetica", Font.BOLD,  12);
        Border thickBorder = new LineBorder(Color.DARK_GRAY);

        add(statusBar,BorderLayout.NORTH);
        add(subTeamAndStatus, BorderLayout.CENTER);

        subTeam.setLayout(new GridLayout(6,1));
        subTeamStatus.setLayout(new GridLayout(6,1));
        subTeamAndStatus.setLayout(new GridLayout(1,2));
        subTeamAndStatus.add(subTeam);
        subTeamAndStatus.add(subTeamStatus);
        subteamname = new JLabel[6];

        for(int i=0; i<6; i++){
            subteamname[i] = new JLabel();
            subteamname[i].setFont(font2);
            subTeam.add(subteamname[i]);
            
        }
        status = new JLabel(" Status");
        status.setFont(font1);
        subteamname[0].setText(" City Map Editor");
        subteamname[1].setText(" Navigator");
        subteamname[2].setText(" Scheduler");
        subteamname[3].setText(" Database");
        subteamname[4].setText(" Tracker");
        subteamname[5].setText(" Setup");
        
        status1 = new JLabel[6];
        for(int i=0; i<6; i++){
            status1[i] = new JLabel();
            status1[i].setText("DOWN");
            status1[i].setFont(font1);
            status1[i].setForeground(Color.red);
            subTeamStatus.add(status1[i]);
        }

        statusBar.add(status);
        setBorder(thickBorder);
       
        Color c = new Color(146, 207, 213);
        statusBar.setBackground(c);
        subTeam.setBackground(Color.white);
    }
}
