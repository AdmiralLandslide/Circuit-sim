
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
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
    static public ArrayList<Component> components = new ArrayList<Component>();
//    private ArrayList<Coordinate> coordList = new ArrayList<Coordinate>();
    CoordList coordList;
    public Component mouseComponent = null;
    boolean clicked = false;
    static int x2;
    static int y2;
    public static int coordListSize;
    static String line = "hi";
    AdjacencyLists Adj1 = new AdjacencyLists(coordList.size());

    public Canvas() {
        super();
        JButton push4 = new JButton("ro-potate");
        push4.setSize(70, 20);
        push4.setLocation(0, 0);
        add(push4);
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
        push5.setSize(70, 20);
        push5.setLocation(0, 20);
        add(push5);
        push5.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Main.lastButtonPressed = null;
                        if (mouseComponent != null) remove(mouseComponent);
                        mouseComponent = null;
                        repaint();
                        clicked = false;
                        x2 = 0;
                        y2 = 0;
                    }
                })
        );

        JButton push6 = new JButton("pun");
        push6.setSize(70, 20);
        push6.setLocation(0, 40);
        add(push6);
        push6.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        listData();

                    }
                })
        );

        JButton push7 = new JButton("rave");
        push7.setSize(70, 20);
        push7.setLocation(0, 60);
        add(push7);
        push7.addActionListener(
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        serialize(components);

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
                        clicked = true;
                        return;
                    }
                    if (clicked == true) {
                        component = new Wire(Main.rotation, x2, y2, x, y);
                        x2 = x;
                        y2 = y;
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

    private void serialize(ArrayList comp) {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("/Users/Ruben/Desktop/serialized.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(comp);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /Users/Ruben/Desktop/serialized.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }



    private void listData() {
        findCoords();
        coordListSize = coordList.size();
        printCoords();
//        System.out.println(coordListSize);
//        System.out.println(components.size());
//        createList();
//        findAllPaths();
//        printList();
//        for (int i=0; i<components.size(); i++) {
//            System.out.println(coordList.get(i));
//        }
    }


    private void findCoords() {
        for(int i=0; i<components.size(); i++) {
//            System.out.println(components.get(i).getX()+ " " + components.get(i).getY());
                if (components.get(i).rotation % 2 == 1 ) {
                    if (!coordList.containsCoord(components.get(i).getX()-100,components.get(i).getY())) {
                        coordList.add(new Coordinate(components.get(i).getX()-100,components.get(i).getY()));
                    }
                    if (!coordList.contains(new Coordinate(components.get(i).getX()+100,components.get(i).getY()))) {
                      coordList.add(new Coordinate(components.get(i).getX()+100,components.get(i).getY()));
                    }
                if (components.get(i).rotation % 2 == 0 ) {
                    if (!coordList.contains(new Coordinate(components.get(i).getX(),components.get(i).getY()-100))) {
                        coordList.add(new Coordinate(components.get(i).getX(),components.get(i).getY()-100));
                    }
                    if (!coordList.contains(new Coordinate(components.get(i).getX(),components.get(i).getY()+100))) {
                        coordList.add(new Coordinate(components.get(i).getX(),components.get(i).getY()+100));
                    }
                }
            }
        }
    }
    private void printCoords() {
        for (int i=0; i<coordListSize; i++) {
            System.out.println(coordList.get(i).getX() + " " + coordList.get(i).getY());

        }
    }

    private void createList() {
        AdjacencyLists Adj1 = new AdjacencyLists(coordListSize);
        for (int i = 0; i<coordList.size(); i++) {
            Adj1.addEdge(coordList.indexOf(new Coordinate((components.get(i).getX()-100), components.get(i).getY())),
                    coordList.indexOf(new Coordinate((components.get(i).getX()+100), components.get(i).getY())));
            Adj1.addEdge(coordList.indexOf(new Coordinate((components.get(i).getX()+100), components.get(i).getY())),
                    coordList.indexOf(new Coordinate((components.get(i).getX()-100), components.get(i).getY())));
        }
    }

//    private void findAllPaths() {
//    }
    private void printList() {
        Adj1.printList();
    }
//    private void readToStr(String file) throws IOException {
//        BufferedReader reader = new BufferedReader( new FileReader (file));
//        String line = "hi";
//        StringBuilder stringBuilder = new StringBuilder();
//        String ls = System.getProperty("line.separator");
//
//        try
//        {
//        while( ( line = reader.readLine() ) != null ) {
//            stringBuilder.append( line );
//            stringBuilder.append(ls);
//        }
//
//
//
//        }catch (IOException e) {
//        e.printStackTrace();
//        }
//        finally {
//        if (reader != null) try { reader.close(); } catch (IOException ignore) {}
//        }
//
//        return stringBuilder.toString();
//    }
}