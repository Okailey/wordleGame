package wordle.view;

import javax.swing.*;
import wordle.model.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * This is the class that shows the 2D grid
 * for users to enter their letters
 * 
 */

public class TileGrid extends JPanel implements KeyboardToGridListener {
    
  //the size of each cell in the grid
    private static final int CELL_SIZE = 60;
    
  //the grid width
    private static final int GRID_COL = 5; 
    
  //the grid height
    private static final int GRID_ROW = 6; 
    
    //our 2D grid
    private LetterTile[][] grid;

    //instance of our wordleGame
    private WordleGame game;

    //this is the what will map the color to the letter in the grid
    private Map<Character, Color> gridLetterColor;
    
    
    /**
     * This is the constructor for TileGrid
     * @param theGrid is the 2D grid
     * @param wordleGame is the WordleGame instance associated with this TileGrid
     */

    public TileGrid(LetterTile[][] theGrid, WordleGame wordleGame) {
        // Perform null check first
        if (theGrid == null || theGrid.length != GRID_ROW || theGrid[0].length != GRID_COL) {
            throw new IllegalArgumentException("Dimensions are invalid or the grid is null.");
        }
        
       grid = wordleGame.getAnswerGrid();
        setFocusable(true);

        // debugging
        System.out.println("Provided Grid Dimensions: " + theGrid.length + "x" + theGrid[0].length);
        
        gridLetterColor = new HashMap<>();
        
        this.game = wordleGame;

    }
    
    /**
     * This is the method to draw the Wordle grid.
     * @param g is the graphics used to draw
     * 
     * 
     */
    private void drawGrid(Graphics g) {


      //total height and width of the 2D grid
      int totalWidth = GRID_COL * CELL_SIZE;
      int totalHeight = getGridRow() * CELL_SIZE;

      //get the grid to be centered
      int centerHorizontally = (getWidth() - totalWidth) / 2;
      int centerVertically = (getHeight() - totalHeight) / 2;

      
        
        //iterate over the rows and columns of the grid 
        for (int row = 0; row < getGridRow(); row++) {
            for (int col = 0; col < GRID_COL; col++) {
                
              //calculate the x and y coordinates of the cell
                int x = centerHorizontally + col * CELL_SIZE;
                int y = centerVertically + row * CELL_SIZE;
                g.setColor(Color.WHITE);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                
             // Draw if the col and row at that index is not ' '
                if (grid[row][col] != null && grid[row][col].getLetter() != ' ') {
                    
                    // Get the color for this letter from the map
                    grid = game.getAnswerGrid();
                    Color letterColor = game.getTile(row, col).getColor();
                    if (letterColor != null) {
                        // Set the color
                        g.setColor(letterColor);
                    } else {
                        // If no color is mapped, don't set any color (leave it as it is)
  
                    }
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(grid[row][col].getLetter()), x + CELL_SIZE / 2 - 5, y + CELL_SIZE / 2 + 5);
                }

            }
        }
      }
      
    
    /**
     * Override the paintComponent 
     * @param g is the graphics used to draw
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }
    
    
    /**
     * This is the method to update the grid once a letter has been 
     * clicked from the keyboard
     * @param letterTile is the tile to be updated with a letter and a color
     * 
     */

    public void updateGridWithLetter(LetterTile letterTile) {
        char letter = letterTile.getLetter();

        for (int row = 0; row < GRID_ROW; row++) {
            for (int col = 0; col < GRID_COL; col++) {

                //if the position in the grid is null, put the letter there and display it
                if (grid[row][col] == null) {
                    grid[row][col].setLetter(letter);
                    repaint();
                    return;

                }
            }
        }

        repaint();
    }
    
    /**
     * This methods sets the color of the key in the grid
     * @param color is the color to be set
     * @param c is the character that will change color
     */
 
    public void setKeyColor(char c, Color color) {
        gridLetterColor.put(c, color);
        
    }
    
    /**
     * Responds to a key press
     * @param letterTile the letter pressed
     * 
     */
    @Override
    public void keyPressed(LetterTile letterTile) {
        updateGridWithLetter(letterTile);
    }

    /**
     * Set the preferred size
     * 
     * @return the preferred size of the component
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GRID_COL * CELL_SIZE, getGridRow() * CELL_SIZE);
    }
    
    
    /**
     * @return the current row in the grid
     */
    public static int getGridRow() {
        return GRID_ROW;
    }
    
    /**
     * @return the current column in the grid
     */
    public static int getGridCol() {
        return GRID_COL;
    }
    
  
}

