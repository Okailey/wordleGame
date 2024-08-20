package spellingBee.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random;
import javax.swing.JComponent;

/**
 * a swing component that creates a seven-hexagon shape that forms a honeycomb
 * pattern it contains letters in the middle of each hexagon and can shuffle
 * these letters on screen
 */
public class Honeycomb extends JComponent {

    /**
     * the letters that the honeycomb will display the very first letter is the
     * one in the middle
     */
    private String letters;

    /**
     * the left-most x-coordinate of the entire honeycomb
     */
    private final int leftX;

    /**
     * the top-most y-coordinate of the entire honeycomb
     */
    private final int topY;

    /**
     * the distance from one vertex of a hexagon to another
     */
    private static final int EDGE_LENGTH = 50;

    /**
     * the x-axis size of a hexagon
     */
    private static final int HEXAGON_WIDTH = (EDGE_LENGTH * 3);

    /**
     * the y-axis size of a hexagon
     */
    private static final int HEXAGON_HEIGHT = EDGE_LENGTH * (2);

    /**
     * the distance from the leftmost point of the hexagon to the main body of
     * the shape (where the body is square made from connecting the top two and
     * bottom two points)
     */
    private static final int X_OFFSET = (HEXAGON_WIDTH / 4);

    /**
     * the number of pixels that seperate one hexagon from another this causes a
     * white space, so that they don't blend together
     */
    private static final int SPACE_BETWEEN = 3;

    /**
     * a random generator that is used for the shuffle functionality
     */
    private Random distGen = new Random();

    /**
     * creates a honeycomb component in yellow and grayscale
     * 
     * @param letters a seven-letter string where the first letter is the one to
     *                be in the middle, in yellow
     * @param leftX   the top left corner of the entire honeycomb
     * @param topY    the top right hand corner of the entire honeycomb
     */
    public Honeycomb(String letters, int leftX, int topY) {
        super();
        this.leftX = leftX;
        this.topY = topY;
        this.letters = letters.toUpperCase();
        if (letters.length() != 7) {
            throw new IllegalArgumentException(
                    "honeycomb letters must be length 7");
        }
    }

    /**
     * draws seven hexagons in a honeycomb pattern each contains a letter from
     * the letters String
     * 
     * @param g Graphics
     */
    public void paintComponent(Graphics g) {
        // top
        drawHexagon(g, leftX + HEXAGON_WIDTH - X_OFFSET, topY,
                letters.substring(1, 2));
        // firstRowLeft
        drawHexagon(g, leftX, topY + (HEXAGON_HEIGHT / 2) + SPACE_BETWEEN,
                letters.substring(2, 3));
        // firstRowRight
        drawHexagon(g,
                leftX + (HEXAGON_WIDTH * 2) - (X_OFFSET * 2) + SPACE_BETWEEN,
                topY + (HEXAGON_HEIGHT / 2), letters.substring(3, 4));
        // yellowMiddle
        drawMiddleHexagon(g, leftX + HEXAGON_WIDTH - X_OFFSET + SPACE_BETWEEN,
                topY + (HEXAGON_HEIGHT) + (SPACE_BETWEEN));
        // secondRowLeft
        drawHexagon(g, leftX, topY + HEXAGON_HEIGHT + (HEXAGON_HEIGHT / 2)
                + (SPACE_BETWEEN * 2), letters.substring(4, 5));
        // secondRowRight
        drawHexagon(g,
                leftX + (HEXAGON_WIDTH * 2) - (X_OFFSET * 2)
                        + (SPACE_BETWEEN * 2),
                topY + HEXAGON_HEIGHT + (HEXAGON_HEIGHT / 2) + (SPACE_BETWEEN),
                letters.substring(5, 6));
        // bottomRight
        drawHexagon(g, leftX + HEXAGON_WIDTH - X_OFFSET + (SPACE_BETWEEN),
                topY + (HEXAGON_HEIGHT * 2) + (SPACE_BETWEEN * 2),
                letters.substring(6));

    }

    /**
     * draws a hexagon with the top-left corner at the sepcified location
     * 
     * @param g    Graphics
     * @param left the x value of the upper left-hand corner
     * @param top  the y value of the upper left-hand corner
     */
    private void drawHexagon(Graphics g, int left, int top, String letter) {
        // adding the six points that will make up the polygon
        // the points start at the upper lefthand corner
        // and go counterclockwise
        Polygon hex = new Polygon();
        // topLeft
        hex.addPoint(left + X_OFFSET, top);
        // middleLeft
        hex.addPoint((left), top + (HEXAGON_HEIGHT / 2));
        // bottomLeft
        hex.addPoint(left + X_OFFSET, (top + HEXAGON_HEIGHT));
        // bottomRight
        hex.addPoint(left + X_OFFSET + (HEXAGON_WIDTH / 2),
                (top + HEXAGON_HEIGHT));
        // middleRight
        hex.addPoint((left + (HEXAGON_WIDTH / 2) + (X_OFFSET * 2)),
                (top + (HEXAGON_HEIGHT / 2)));
        // topRight
        hex.addPoint((left + X_OFFSET + (HEXAGON_WIDTH / 2)), top);

        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(hex);
        g.setColor(Color.BLACK);
        g.drawString(letter, left + (HEXAGON_WIDTH / 2),
                top + (HEXAGON_HEIGHT / 2));
    }

    /**
     * draws a yellow hexagon with the middle letter
     * 
     * @param g    Graphics
     * @param left the x value of the upper left-hand corner
     * @param top  the y value of the upper left-hand corner
     */
    private void drawMiddleHexagon(Graphics g, int left, int top) {
        // adding the six points that will make up the polygon
        // the points start at the upper lefthand corner
        // and go counterclockwise
        Polygon hex = new Polygon();
        // topLeft
        hex.addPoint(left + X_OFFSET, top);
        // middleLeft
        hex.addPoint((left + X_OFFSET - X_OFFSET), top + (HEXAGON_HEIGHT / 2));
        // bottomLeft
        hex.addPoint(left + X_OFFSET, (top + HEXAGON_HEIGHT));
        // bottomRight
        hex.addPoint(left + X_OFFSET + (HEXAGON_WIDTH / 2),
                (top + HEXAGON_HEIGHT));
        // middleRight
        hex.addPoint((left + (HEXAGON_WIDTH / 2) + (X_OFFSET * 2)),
                (top + (HEXAGON_HEIGHT / 2)));
        // topRight
        hex.addPoint((left + X_OFFSET + (HEXAGON_WIDTH / 2)), top);

        g.setColor(new Color(238, 217, 51));
        g.fillPolygon(hex);
        g.setColor(Color.BLACK);
        g.drawString(letters.substring(0, 1), left + (HEXAGON_WIDTH / 2),
                top + (HEXAGON_HEIGHT / 2));
    }

    /**
     * shuffles the outside letters of the honeycomb
     */
    public void shuffle() {
        String toShuffle = letters.substring(1);
        letters = new String(letters.substring(0, 1));
        // adds outside honeycomb chars to letters randomly
        for (int i = 5; i > 0; i--) {
            int rand = distGen.nextInt(0, i);

            if (rand == i) {
                letters = letters + (toShuffle.substring(rand));
                toShuffle = toShuffle.substring(0, rand);
            } else {
                letters = letters + (toShuffle.substring(rand, rand + 1));
                toShuffle = toShuffle.substring(0, rand)
                        + toShuffle.substring(rand + 1);
            }
        }
        letters = letters + toShuffle;
        repaint();
    }

}
