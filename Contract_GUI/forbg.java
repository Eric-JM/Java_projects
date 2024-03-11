
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class forbg  extends JComponent {
    
    
    private Image bg;
    
     public void paintComponent(Graphics g) {
        java.net.URL impath=getClass().getResource("title.png");
        ImageIcon pict=new ImageIcon(impath);
        bg=pict.getImage();
        g.drawImage(bg,0,0,null);
               
        } 
    
    

}
