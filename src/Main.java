
import sun.management.resources.agent;
import sun.security.provider.certpath.AdjacencyList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Ruben on 21/01/2014.
 */


public class Main {

    static JFrame mainFrame;
    static JPanel panel1;
    static JPanel panel2;
    static JPanel panel3;
    static String[] names = {"WIRE", "RESISTOR", "AMMETER", "VOLTMETER", "BULB", "VARIABLE RESISTOR", "CELL", "LDR", "LED", "CAPACITOR", "SWITCH", "SPDT"};
    static String[] shortName = {"WRE", "RST", "AMT", "VLT", "BLB", "VAR", "CEL", "LDR", "LED", "CAP", "SWT", "SPD"};
    static Boolean componentSelected = false;
    public static String lastButtonPressed = null;
    public static int rotation = 1;
    static Canvas canvas1 = null;




    public static void main(String[] args)throws IOException {
        init();

    }
    public static void init() throws IOException {
        mainFrame = new JFrame("Circuit Simulation, Yo");
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(null);

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        mainFrame.add(panel1);
        mainFrame.add(panel2);
        mainFrame.add(panel3);
        panel1.setBackground(Color.WHITE);
        panel1.setSize(mainFrame.getWidth(), mainFrame.getHeight());
        panel1.setLocation(0, 0);
        panel2.setBackground(Color.WHITE);
        panel2.setSize(mainFrame.getWidth(), mainFrame.getHeight() - 20);
        panel2.setLocation(0, 0);
        panel3.setBackground(Color.WHITE);
        panel3.setSize(mainFrame.getWidth(), mainFrame.getHeight());
        panel3.setLocation(0, 0);
        panel1.setVisible(true);
        panel2.setVisible(false);
        panel3.setVisible(false);
        JButton push1 =  new JButton("Create New Project");
        push1.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        push1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        panel1.setVisible(false);
                        panel2.setVisible(true);

                    }
                }
        );
        panel1.add(push1);
        push1.setFocusPainted(false);
        push1.setContentAreaFilled(false);
        push1.setBorderPainted(false);
        push1.setRolloverEnabled(false);
        push1.setVisible(true);
        JButton push2 =  new JButton("Open Saved Project");
        push2.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        push2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        panel1.setVisible(false);
                        panel3.setVisible(true);
                    }
                }
        );
        panel1.add(push2);
        push2.setFocusPainted(false);
        push2.setContentAreaFilled(false);
        push2.setBorderPainted(false);
        push2.setRolloverEnabled(false);
        JButton push3 =  new JButton("Back");
        push3.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        panel1.setVisible(true);
                        panel3.setVisible(false);
                    }
                }
        );
        panel3.add(push3);
        push3.setFocusPainted(false);
        push3.setContentAreaFilled(false);
        push3.setBorderPainted(false);
        push3.setRolloverEnabled(false);

        JPanel CompCanvas = new JPanel();
        JPanel spanel3 = new JPanel();
        spanel3.setBackground(Color.white);
        CompCanvas.setBackground(Color.white);
        CompCanvas.setVisible(true);
        canvas1 = new Canvas();

        panel2.setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setEnabled(false);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setRightComponent(new JScrollPane(canvas1));
        splitPane.setLeftComponent(splitPane2);
        splitPane2.setTopComponent(new JScrollPane(CompCanvas));
        splitPane2.setBottomComponent(spanel3);
        splitPane.setResizeWeight(0.1);
        splitPane2.setResizeWeight(0.9);
        panel2.add(splitPane, BorderLayout.CENTER);

        CompCanvas.setLayout(new BoxLayout(CompCanvas, BoxLayout.Y_AXIS));
        for (int s=0; s<names.length; s++){
            final int s2 = s;
            final String name = names[s];
            JButton x = new JButton(name);
            CompCanvas.add(x);
            x.setFocusPainted(false);
            x.setContentAreaFilled(false);
            x.setBorderPainted(false);
            x.setRolloverEnabled(false);
            x.setFont(new Font("Verdana", Font.PLAIN, 20));
            x.setMaximumSize(new Dimension(900, 100));
            x.setMinimumSize(new Dimension(200, 100));
            x.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            Main.lastButtonPressed = shortName[s2];
                            String component = lastButtonPressed;

                            if (canvas1.mouseComponent != null) {
                                canvas1.remove(canvas1.mouseComponent);
                                canvas1.repaint();
                            }

                            if (component.equals("WRE")) {
                            //    canvas1.mouseComponent = new Wire(Main.rotation, 0,0,0,0);
                          }
                              else if (component.equals("RST")) {
                                canvas1.mouseComponent = new Resistor(Main.rotation, 0, 0);
                            } else if (component.equals("AMT")) {
                                canvas1.mouseComponent = new Ammeter(Main.rotation, 0,0);
                            } else if (component.equals("VLT")) {
                                canvas1.mouseComponent = new Voltmeter(Main.rotation, 0,0);
                            } else if (component.equals("BLB")) {
                                canvas1.mouseComponent = new Bulb(Main.rotation, 0,0);
                            } else if (component.equals("VAR")) {
                                canvas1.mouseComponent = new VariableResistor(Main.rotation, 0,0);
                            } else if (component.equals("CEL")) {
                                canvas1.mouseComponent = new Cell(Main.rotation, 0,0);
                            } else if (component.equals("LDR")) {
                                canvas1.mouseComponent = new LightDependentResistor(Main.rotation, 0,0);
                            } else if (component.equals("LED")) {
                                canvas1.mouseComponent = new LightEmittingDiode(Main.rotation, 0,0);
                            } else if (component.equals("CAP")) {
                                canvas1.mouseComponent = new Capacitor(Main.rotation, 0,0);
                            } else if (component.equals("SWT")) {
                                canvas1.mouseComponent = new Switch(Main.rotation, 0,0);
                            } else if (component.equals("SPD")) {
                                canvas1.mouseComponent = new SinglePoleDoubleThrow(Main.rotation, 0,0);
                            }
                            canvas1.mouseComponent.setColor(new Color(160, 160, 160));
                            canvas1.add(canvas1.mouseComponent);
                            
                            
//                            File file = new File("/Users/Ruben/Desktop/data.txt");
//                            Writer writer = null;
//                            try {PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
//                                out.println(shortName[s2]);
//                                out.close();
//                                componentSelected = true;
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            finally {
//                                if (writer != null) try { writer.close(); } catch (IOException ignore) {}
//                            }
                        }
                    }
            );
        }


        AdjacencyLists AdjList1 = new AdjacencyLists(9);

    }
}