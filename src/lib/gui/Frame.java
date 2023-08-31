package lib.gui;

import lib.shapes.Cube;
import lib.util.Point3D;
import lib.util.ShapeQueue;
import lib.util.Gtool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Objects;


public class Frame extends JFrame implements KeyListener {

    protected Gtool gTool;
    private final ShapeQueue queue;
    private final static short framesPerSec = 144;
    private final static short calculationsPerSec = 144;

    private final Dimension translation = new Dimension();
    private final HashMap<Integer, Runnable> keyMapping = new HashMap<>();
    private final JPanel panel;

    public Frame(final int width, final int height, ShapeQueue queue){
        // Initialise like a normal JFrame with this title
        super("Isometric Renderer");

        // Initialise our helper
        this.gTool = new Gtool(width, height);

        this.queue = queue;

        keyMapping.put(KeyEvent.VK_LEFT, () -> {translation.width -= 10;});
        keyMapping.put(KeyEvent.VK_UP, () -> {translation.height -= 10;});
        keyMapping.put(KeyEvent.VK_RIGHT, () -> {translation.width += 10;});
        keyMapping.put(KeyEvent.VK_DOWN, () -> {translation.height += 10;});
        keyMapping.put(KeyEvent.VK_ESCAPE, () -> {System.exit(1);});
        keyMapping.put(KeyEvent.VK_R, () -> {new Frame(this.getWidth(),this.getHeight(), this.queue); this.dispose();});
        // The point here does not matter
        keyMapping.put(KeyEvent.VK_W, () -> queue.rotate(Math.toRadians(10),0,0, Point3D.CENTER));
        keyMapping.put(KeyEvent.VK_S, () -> queue.rotate(Math.toRadians(-10),0,0, Point3D.CENTER));
        keyMapping.put(KeyEvent.VK_A, () -> queue.rotate(0,0,Math.toRadians(-10), Point3D.CENTER));
        keyMapping.put(KeyEvent.VK_D, () -> queue.rotate(0,0,Math.toRadians(10), Point3D.CENTER));
        keyMapping.put(KeyEvent.VK_Q, () -> queue.rotate(0,Math.toRadians(-10),0, Point3D.CENTER));
        keyMapping.put(KeyEvent.VK_E, () -> queue.rotate(0,Math.toRadians(10),0, Point3D.CENTER));



        this.setBounds(2560/2 - width/2, 1440/2 - height/2, width, height);
        this.setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/lib/img/Perlin_noise.jpg"))).getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel(this.getLayout()){
            @Override
            public void paint(Graphics g){
                g.translate(translation.width, translation.height);

                g.clearRect(0,0,getWidth(),getHeight());

                g.setColor(new Color(0,0,0,50));

                // Draw the x- and y-axis.
                g.drawLine(translation.width, gTool.MIDDLE[1] + translation.height, getWidth() + translation.width, gTool.MIDDLE[1] + translation.height);
                g.drawLine(gTool.MIDDLE[0] + translation.width, translation.height, gTool.MIDDLE[0] + translation.width, getHeight() + translation.height);

                // Does not work every time
                // g.setColor(Color.WHITE);
                // queue.paint(g, gTool);

                queue.paint(g, gTool);

            }
        };
        panel.setDoubleBuffered(true);
        this.add(panel);
        panel.setVisible(true);



        this.addKeyListener(this);
        this.setVisible(true);


        // Init repaint and nextcord timers
        Timer renderer = new Timer((int) (1000.0f / framesPerSec), e -> panel.paintImmediately(panel.getBounds()));
        Timer calculator = new Timer((int) (1000.f / calculationsPerSec), e -> queue.newCords());
        renderer.start();
        calculator.start();


    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try{
            this.keyMapping.get(e.getKeyCode()).run();
        } catch (NullPointerException ignore){

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }

}


