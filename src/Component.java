import javax.swing.*;
import java.awt.*;

/**
 * Created by Ruben on 04/02/2014.
 */
public abstract class Component extends JPanel {

    public int rotation;
    private Color color = Color.black;
    static int width = Main.mainFrame.getWidth();

    public Component(int rotation, int x, int y,int w, int h) {
        super();
        this.setSize(w,h);
        this.setLocation(x,y);
        this.rotation = rotation;
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }
    protected void paintComponent(Graphics g){
        g.setColor(color);

    }

    public void setColor(Color color) {
        this.color = color;
    }
}
