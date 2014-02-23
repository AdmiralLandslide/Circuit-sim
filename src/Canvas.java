
import com.sun.tools.javac.util.Paths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

    private Dimension preferredSize = new Dimension(600,600);
    private Line2D[] lines = new Line2D[70];
    private Line2D[] lines2= new Line2D[70];
    private double scale = 1;
    private static Double MAX_SCALE = 10.0;
    private static Double MIN_SCALE = 0.47;
    private ArrayList<Component> components = new ArrayList<Component>();
    public Component mouseComponent = null;
    boolean clicked = false;
    static int x2;
    static int y2;
    static String line = "hi";

    public Canvas() {
        super();
        JButton push4 = new JButton("ro-potate");
        add(push4);
        push4.setMaximumSize(new Dimension(50, 50));
        push4.setMinimumSize(new Dimension(50, 50));
        push4.setBounds(400, 400, 10, 10);
        push4.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Main.rotation++;
                        if (mouseComponent!=null) {
                            mouseComponent.rotation++;
                        }
                        if ((Main.rotation % 5) == 0) {
                            Main.rotation = 1;
                        } else {
                            return;
                        }

                    }
                })
        );
        JButton push5 = new JButton("mooose");
        add(push5);
        push5.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Main.lastButtonPressed = null;
                        remove(mouseComponent);
                        mouseComponent = null;
                        repaint();
                        clicked = false;
                        x2 = 0;
                        y2 = 0;
                    }
                })
        );

        JButton push6 = new JButton("run");
        add(push6);
        push6.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        listData();

                    }
                })
        );

//       Canvas.Layout(GridBagLayout);
        //generate lines
        for (int i=0; i<lines.length; i++) {
            lines[i] = new Line2D.Float(
                    i*50, 0,
                    i*50, 4000);
            lines2[i] = new Line2D.Float(
                    0, i*50,
                    4000, i*50);
        }

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                int x = (mouseEvent.getX()-100);
                int y = (mouseEvent.getY()-100);
                if (mouseComponent != null) {
                    if ((x % 50 <15) || x % 50 > 35) {
                        x = 50*(Math.round((x+25)/50));
                    }
                    if ((y % 50 <15) || y % 50 > 35) {
                        y = 50*(Math.round((y+25)/50));
                    }
                    mouseComponent.setLocation(x, y);
                    mouseComponent.repaint();
                }
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
//                updatePreferredSize(e.getWheelRotation(), e.getPoint());

            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String lst = Main.lastButtonPressed;
                int x = (50*(Math.round((mouseEvent.getX()+25)/50)));
                int y = (50*(Math.round((mouseEvent.getY()+25)/50)));

                Component component = null;
                if (lst.equals("WRE")) {
                    if (clicked == false) {
                        x2 = x;
                        y2 = y;
                        System.out.println(x2);
                        System.out.println(y2);
                        System.out.println();
                        clicked = true;
                        return;
                    }
                    if (clicked == true) {
                        System.out.println(x);
                        System.out.println(y);
                        System.out.println(x2);
                        System.out.println(y2);
                        System.out.println();
                        component = new Wire(Main.rotation, x2, y2, x, y);
                        x2 = x;
                        y2 = y;
//                        x = 0;
//                        y = 0;
                    }
                } else if (lst.equals("RST")) {
                    component = new Resistor(Main.rotation, x, y);
                } else if (lst.equals("AMT")) {
                    component = new Ammeter(Main.rotation, x,y);
                } else if (lst.equals("VLT")) {
                    component = new Voltmeter(Main.rotation, x,y);
                } else if (lst.equals("BLB")) {
                    component = new Bulb(Main.rotation, x,y);
                } else if (lst.equals("VAR")) {
                    component = new VariableResistor(Main.rotation, x,y);
                } else if (lst.equals("CEL")) {
                    component = new Cell(Main.rotation, x,y);
                } else if (lst.equals("LDR")) {
                    component = new LightDependentResistor(Main.rotation, x,y);
                } else if (lst.equals("LED")) {
                    component = new LightEmittingDiode(Main.rotation, x,y);
                } else if (lst.equals("CAP")) {
                    component = new Capacitor(Main.rotation, x,y);
                } else if (lst.equals("SWT")) {
                    component = new Switch(Main.rotation, x,y);
                } else if (lst.equals("SPD")) {
                    component = new SinglePoleDoubleThrow(Main.rotation, x,y);
                }
                components.add(component);
                add(component);

                repaint();

                getcoords(mouseEvent.getX(), mouseEvent.getY());

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        } );
    }
    private void updatePreferredSize(int n, Point p) {
        if (n == 0) return;
        double d = (double) n * 1.08;
        d = (n > 0) ? d : 1 / -d;

        int w = (int) (getWidth() * d);
        int h = (int) (getHeight() * d);
        double newScale = scale * d;
        if (newScale > MAX_SCALE || newScale < MIN_SCALE) {
            return;
        }
        scale = newScale;
        preferredSize.setSize(w, h);

        int offX = (int)(p.x * d) - p.x;
        int offY = (int)(p.y * d) - p.y;
        setLocation(getLocation().x-offX,getLocation().y-offY);

        getParent().doLayout();
    }
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    private Line2D l = new Line2D.Float();
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(220,220,220));
        double w = scale;
        double h = scale;
        for (Line2D line : lines) {
            l.setLine(line.getX1() * w, line.getY1() * w,
                    line.getX2() * h, line.getY2() * h);
            ((Graphics2D)g).draw(l);
        }
        for (Line2D line : lines2) {
            l.setLine(line.getX1() * w, line.getY1() * w,
                    line.getX2() * h, line.getY2() * h);
            ((Graphics2D)g).draw(l);
        }
    }

    public void getcoords(float xCoord, float yCoord) {
        String X1 = "";
        String Y1 = "";
        String X2 = "";
        String Y2 = "";
        String X3 = "";
        String Y3 = "";
        if (Main.lastButtonPressed.equals( "WRE" )) {
            X1 = Integer.toString((int) (Math.round((Wire.xone) / (scale * (1 / 1.08)))/50));
            Y1 = Integer.toString((int) (Math.round((Wire.yone) / (scale * (1 / 1.08)))/50));
            X2 = Integer.toString((int) (Math.round((Wire.xtwo) / (scale * (1 / 1.08)))/50));
            Y2 = Integer.toString((int) (Math.round((Wire.ytwo) / (scale * (1 / 1.08)))/50));
        }
        if  (Main.lastButtonPressed.equals("SPD")) {
            if (Main.rotation == 1) {
                X1 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y1 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y2 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50)-1);
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y3 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50)+1);
            }
            if (Main.rotation == 2) {
                X1 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08)))/50));
                Y1 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 1)));
                Y2 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 1)));
                Y3 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
            }
            if (Main.rotation == 3) {
                X1 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y1 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y2 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50)-1);
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y3 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50)+1);

            }
            if (Main.rotation == 4) {
                X1 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08)))/50));
                Y1 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 1)));
                Y2 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                X3 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 1)));
                Y3 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
            }
        }
        else {
            if (Main.rotation % 2 == 1) {
                X1 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                Y1 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50));
                X2 = Integer.toString((int) (Math.round((((xCoord) / (scale * (1 / 1.08))) / 50) + 2)));
                Y2 = Integer.toString((int) (Math.round((yCoord) / (scale * (1 / 1.08)))/50));
            }
            if (Main.rotation % 2 == 0) {
                X1 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08)))/50));
                Y1 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) - 2)));
                X2 = Integer.toString((int) (Math.round((xCoord) / (scale * (1 / 1.08)))/50));
                Y2 = Integer.toString((int) (Math.round((((yCoord) / (scale * (1 / 1.08))) / 50) + 2)));
            }
        }
        if (X1.length() < 2) { X1 = "0" + X1;}
        if (Y1.length() < 2) { Y1 = "0" + Y1;}
        if (X2.length() < 2) { X2 = "0" + X2;}
        if (Y2.length() < 2) { Y2 = "0" + Y2;}
        if (X3.length() < 2) { X3 = "0" + X3;}
        if (Y3.length() < 2) { Y3 = "0" + Y3;}


        File file = new File("/Users/Ruben/Desktop/data.txt");
        Writer writer = null;
        try{ PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            if (Main.lastButtonPressed == "SPD") {
                out.println(X1 + " " + Y1 + " " + Main.lastButtonPressed + " " + X2 + " " + Y2 + " " + X3 + " " + Y3 );
                out.close();
            }
            else {
                out.println(X1 + " " + Y1 + " " + Main.lastButtonPressed + " " + X2 + " " + Y2 );
                out.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) try { writer.close(); } catch (IOException ignore) {}
        }
    }

    private void listData() {
        String file = new String("/Users/Ruben/Desktop/data.txt");
        try {
            readToStr(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
        findCoords();

    }

    private void readToStr(String file) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader (file));
//        String line = "hi";
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try
        {
        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
            System.out.println(line);
        }



        }catch (IOException e) {
        e.printStackTrace();
        }
        finally {
        if (reader != null) try { reader.close(); } catch (IOException ignore) {}
        }

//        return stringBuilder.toString();
    }

    private void findCoords() {
        String string = "";
        for(int i=1; i<3; i++){
            string = (line.substring(1*i-1, i+1) + line.substring(3*i, 3*i+2));
            System.out.println(string);
        }


    }


}