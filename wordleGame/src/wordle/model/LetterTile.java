package wordle.model;

import java.awt.Color;

/**
 * Class that holds a letter and a color. Intended to be used in a graphical display.
 * Works for both game tiles and keyboard tiles.
 */
public class LetterTile {
    /**
     * The color of the tile
     */
    private Color color;
    
    /**
     * The letter displayed on the tile
     */

    private char letter;



    /**
     * Creates a new LetterTile.
     * @param color the color of the tile
     * @param letter the letter contained within the tile
     */
    public LetterTile (Color color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    /**
     * Getter for the tile's letter
     * @return the letter displayed on the tile
     */

    public char getLetter() {
        return this.letter;
    }

    /**
     * Sets the letter on the tile
     * @param letter The letter to be displayed
     */

    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Getter for color
     * @return The color of the tile
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Setter for color
     * @param color The color for the tile to display
     */
    public void setColor (Color color) {
        this.color = color;
    }
}





