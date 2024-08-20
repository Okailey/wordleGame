package graphicsExample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Drawing extends JComponent {
    private static final int WALL_TOP = 200;
    private static final int WALL_LEFT = 150;
    private static final int WALL_HEIGHT = 100;
    private static final int WALL_WIDTH = 100;
    private static final Color WALL_COLOR = new Color(210, 226, 204);
    
    private static final int ROOF_TOP = WALL_TOP - 50;
    private static final int ROOF_MIDDLE = WALL_LEFT + WALL_WIDTH / 2;
    
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        System.out.println ("Painting drawing");
        g.setColor(WALL_COLOR);
        g.fillRect(WALL_LEFT, WALL_TOP, WALL_WIDTH, WALL_HEIGHT);
        
        Polygon p = new Polygon();
        p.addPoint(WALL_LEFT, WALL_TOP);
        p.addPoint(WALL_LEFT + WALL_WIDTH, WALL_TOP);
        p.addPoint(ROOF_MIDDLE, ROOF_TOP);
        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(p);    

    }
}
