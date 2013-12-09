
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.Timer;

public class logPanel extends JPanel implements ActionListener {

    private String line;
    private JTextArea logText;
    private JScrollPane scrollPane;
    private Timer timer;
    private int count = 0;
    private long oldtimeStamp;
    private Font font1;

    public logPanel() {

        font1 = new Font("Tahoma", Font.PLAIN,  12);
        timer = new Timer(3000,this);
        logText = new JTextArea(21, 17);
        logText.setEditable(false);
        scrollPane = new JScrollPane(logText);
        scrollPane.setBorder(null);
        add(scrollPane);
        logText.setLineWrap(true);
        logText.setWrapStyleWord(true);
        setBackground(Color.WHITE);
        timer.start();
    }

    public void actionPerformed(ActionEvent e){

        if (count ==0){
            readFile(true);
        }else{
            
            if (oldtimeStamp != this.checktimeStamp()) {
                oldtimeStamp = checktimeStamp();
                readFile(true);
            }
        }
    }

    public long checktimeStamp(){
        File file = new File("log.txt");
        return file.lastModified();
    }

    public void readFile(boolean checkfile) {

        logText.setText("");
        if (checkfile == true) {
            try {
                FileInputStream fstream = new FileInputStream("log.txt");
                // Get the object of DataInputStream
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader input = new BufferedReader(new InputStreamReader(in));

                int i = 1;

                while ((line = input.readLine()) != null) {

                    logText.append(i + ") " + line + "\n");
                    logText.setFont(font1);
                    i++;
                }
            } catch (Exception IOException) {
                System.out.println(IOException);
            }
        }
        count++;
        
    }

    
}
