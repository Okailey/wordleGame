package graphicsExample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public Main() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        
        Drawing drawing = new Drawing();
        drawing.setPreferredSize(new Dimension(400, 400));
        p.add(drawing);

        f.add(p, BorderLayout.CENTER);
        f.setSize(400, 400);
        f.setResizable(false);
        f.setVisible(true);
    }


    public static void main(String[] args) {
        new Main();
    }

}
