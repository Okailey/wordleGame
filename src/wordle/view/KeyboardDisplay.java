package wordle.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import wordle.model.*;
import java.awt.event.KeyEvent;

/**
 * 
 * This class displays a virtual keyboard on the screen for users to interact
 * with it by clicking on the keys in the keyboard to help display their guess
 * on the grid
 * 
 */

public class KeyboardDisplay extends JPanel implements KeyboardToGridListener {

    // the width of a key in the keyboard
    private static final int KEY_WIDTH = 40;

    // the height of a key in the keyboard
    private static final int KEY_HEIGHT = 60;

    // the padding between keys in the keyboard
    private static final int PADDING = 10;

    // this is the 2D array letters of the keyboard, each has a letterTile which
    // has properties of a color and a letter
    private LetterTile[][] LETTER_TILES;

    // this is the listener for interaction between the keyboard and grid
    private KeyboardToGridListener listener;

    /**
     * This is the constructor for the KeyboardDisplay class
     * 
     * @param listener for keyboard events
     */

    public KeyboardDisplay(KeyboardToGridListener listener) {
        this.listener = listener;
        this.LETTER_TILES = new LetterTile[][] {
                { new LetterTile(Color.LIGHT_GRAY, 'Q'),
                        new LetterTile(Color.LIGHT_GRAY, 'W'),
                        new LetterTile(Color.LIGHT_GRAY, 'E'),
                        new LetterTile(Color.LIGHT_GRAY, 'R'),
                        new LetterTile(Color.LIGHT_GRAY, 'T'),
                        new LetterTile(Color.LIGHT_GRAY, 'Y'),
                        new LetterTile(Color.LIGHT_GRAY, 'U'),
                        new LetterTile(Color.LIGHT_GRAY, 'I'),
                        new LetterTile(Color.LIGHT_GRAY, 'O'),
                        new LetterTile(Color.LIGHT_GRAY, 'P') },
                { new LetterTile(Color.LIGHT_GRAY, 'A'),
                        new LetterTile(Color.LIGHT_GRAY, 'S'),
                        new LetterTile(Color.LIGHT_GRAY, 'D'),
                        new LetterTile(Color.LIGHT_GRAY, 'F'),
                        new LetterTile(Color.LIGHT_GRAY, 'G'),
                        new LetterTile(Color.LIGHT_GRAY, 'H'),
                        new LetterTile(Color.LIGHT_GRAY, 'J'),
                        new LetterTile(Color.LIGHT_GRAY, 'K'),
                        new LetterTile(Color.LIGHT_GRAY, 'L') },
                { new LetterTile(Color.LIGHT_GRAY, '\u2190'),
                        new LetterTile(Color.LIGHT_GRAY, 'Z'),
                        new LetterTile(Color.LIGHT_GRAY, 'X'),
                        new LetterTile(Color.LIGHT_GRAY, 'C'),
                        new LetterTile(Color.LIGHT_GRAY, 'V'),
                        new LetterTile(Color.LIGHT_GRAY, 'B'),
                        new LetterTile(Color.LIGHT_GRAY, 'N'),
                        new LetterTile(Color.LIGHT_GRAY, 'M'),
                        new LetterTile(Color.LIGHT_GRAY, '\u21B5') } };

        // a listener for mouse clicks on the keyboard
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = (getWidth() - (10 * (KEY_WIDTH + PADDING) + PADDING))
                        / 2;
                int y = (getHeight() - (3 * (KEY_HEIGHT + PADDING) + PADDING))
                        / 2;

                int horizontalDirectionKeyboard = e.getX() - x;
                int verticalDirectionKeyboard = e.getY() - y;

                // identify which row/column has been clicked
                int rowClicked = verticalDirectionKeyboard
                        / (KEY_HEIGHT + PADDING);
                int colClicked = horizontalDirectionKeyboard
                        / (KEY_WIDTH + PADDING);

                // If the click is within valid bounds, notify the listener by
                // printing to console what was clicked
                if (rowClicked >= 0 && rowClicked < LETTER_TILES.length
                        && colClicked >= 0
                        && colClicked < LETTER_TILES[rowClicked].length) {
                    LetterTile tile = LETTER_TILES[rowClicked][colClicked];
                    if (listener != null) {
                        listener.keyPressed(tile);
                        System.out.println("Key pressed: " + tile.getLetter());
                    }
                }
            }
        });

        // listener for key
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

            }
        });
    }

    /**
     * Responds to a key press
     * 
     * @param letterTile the letter pressed
     */

    @Override
    public void keyPressed(LetterTile letterTile) {
        if (listener != null) {
            listener.keyPressed(letterTile);
        }
    }

    /**
     * Override the paintComponent
     * 
     * @param g is the graphics used to draw
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Total width and height of the keyboard
        int totalWidth = 10 * KEY_WIDTH + 11 * PADDING;
        int totalHeight = 3 * KEY_HEIGHT + 4 * PADDING;

        // Calculate the x and y coordinates to center the keyboard
        int xCoordinate = (getWidth() - totalWidth) / 2;
        int yCoordinate = (getHeight() - totalHeight) / 2;

        // draw it
        drawKeysForKeyboard(g2d, xCoordinate, yCoordinate);
    }

    /**
     * This method draws the keys for the keyboard
     * 
     * @param xCoordinate of the starting point of the keyboard
     * @param yCoordinate of the starting point of the keyboard
     * @param g2d         is the graphics object used to draw the keyboard
     * 
     */

    private void drawKeysForKeyboard(Graphics2D g2d, int xCoordinate,
            int yCoordinate) {
        int y = yCoordinate;

        // Iterate over each row of keys in LETTER_TILES array
        for (LetterTile[] row : LETTER_TILES) {
            int x = xCoordinate;

            // iterate over each key tile in the row
            for (LetterTile tile : row) {

                g2d.setColor(tile.getColor());

                // Fill a rectangle representing the key at the current position
                g2d.fillRect(x, y, KEY_WIDTH, KEY_HEIGHT);
                g2d.setColor(Color.LIGHT_GRAY);

                // draw the border for each rectangle
                g2d.drawRect(x, y, KEY_WIDTH, KEY_HEIGHT);
                g2d.setColor(Color.BLACK);

                // set font and size
                g2d.setFont(new Font("Arial", Font.BOLD, 16));

                // Calculate the position to draw the letter while centering
                FontMetrics fm = g2d.getFontMetrics();
                int x_text = x + (KEY_WIDTH
                        - fm.stringWidth(Character.toString(tile.getLetter())))
                        / 2;
                int y_text = y + ((KEY_HEIGHT - fm.getHeight()) / 2)
                        + fm.getAscent();

                // draw the letter
                g2d.drawString(Character.toString(tile.getLetter()), x_text,
                        y_text);
                x += KEY_WIDTH + PADDING;
            }

            y += KEY_HEIGHT + PADDING;
        }
    }

    /**
     * Set the preferred size
     * 
     * @return the preferred size of the component
     */

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(2 * PADDING + 10 * (KEY_WIDTH + PADDING),
                3 * PADDING + 3 * KEY_HEIGHT);
    }

    /**
     * Helper method that finds the coordinates of a letter in the keyboard
     * 
     * @param c the letter to search for
     * @return a set of coordinates corresponding to the letter's location in
     *         the keyboard
     */
    public int[] findLetter(char c) {
        int[] coordinates = { -1, -1 };
        for (int i = 0; i < LETTER_TILES.length; i++) {
            for (int j = 0; j < LETTER_TILES[i].length; j++) {
                if (LETTER_TILES[i][j].getLetter() == c) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                }
            }
        }
        return coordinates;
    }

    /**
     * Gets the color of a letter in the keyboard
     * 
     * @param c the letter to search for
     * @return the current color of that letter's tile
     */
    public Color findColorOfLetter(char c) {
        for (int i = 0; i < LETTER_TILES.length; i++) {
            for (int j = 0; j < LETTER_TILES[i].length; j++) {
                if (LETTER_TILES[i][j].getLetter() == c) {
                    return LETTER_TILES[i][j].getColor();
                }
            }
        }
        return Color.lightGray;
    }

    /**
     * Sets the color of a tile
     * 
     * @param c     the character on the tile to be altered
     * @param color the color to set the tile to
     */
    public void setKeyColor(char c, Color color) {
        for (int i = 0; i < LETTER_TILES.length; i++) {
            for (int j = 0; j < LETTER_TILES[i].length; j++) {
                if (LETTER_TILES[i][j].getLetter() == c) {
                    LETTER_TILES[i][j].setColor(color);
                }
            }

        }
    }
}
