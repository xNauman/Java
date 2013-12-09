
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel
{
  protected Image image;
  public BackgroundPanel()
  {
    try
    {
      image = new ImageIcon("images/bg.jpg").getImage();
    }
    catch (Exception e) { /*handled in paintComponent()*/ }
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (image != null)
      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
  }
}