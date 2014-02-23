import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Ruben on 04/02/2014.
 */
public class Wire extends Component {
    static int xone;
    static int xtwo;
    static int yone;
    static int ytwo;

    public Wire(int rotation,int x2, int y2, int x1, int y1) {
        super(rotation, 0, 0, Component.width, Main.mainFrame.getHeight() );
        xone = x1;
        xtwo = x2;
        yone = y1;
        ytwo = y2;

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(xone, yone, xtwo, ytwo);
    }
}

