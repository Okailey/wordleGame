package wordle.controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import wordle.model.*;
import wordle.view.*;

/**
 * This class provides a graphical user interface that allows a user to play the
 * wordle game.
 *
 */
public class WordleGUI  {
    private JFrame mainView;
    private KeyboardDisplay keyboard;
    private TileGrid grid;
    private JTextField userInput;
    private WordleGame game;
    

    /**
     * Constructor for WordleGUI
     * 
     */

    public WordleGUI() {

        // Generates a game
        this.game = new WordleGame(this);
      

        mainView = new JFrame("Wordle");
        mainView.setBackground(Color.WHITE);
        mainView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainView.setLayout(new BorderLayout());

        // a panel to hold the KeyboardTile and GridTile
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Update the instantiation to pass the WordleGame instance
        grid = new TileGrid(game.getAnswerGrid(), game);
        keyboard = new KeyboardDisplay(new KeyboardToGridListener() {
            @Override
            public void keyPressed(LetterTile letterTile) {
                game.addLetter(letterTile.getLetter());
                grid.updateGridWithLetter(letterTile);
                grid.repaint(); 
            }

        });

        mainPanel.add(grid, BorderLayout.CENTER);
        mainPanel.add(keyboard, BorderLayout.SOUTH);
        
     // Add the JTextField for user input
        userInput = new JTextField();
        userInput.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String guess = userInput.getText().trim();
                
                //after you enter the guess, clear the textfield
                userInput.setText(""); 
                game.checkGuess(guess);
                grid.repaint();
            }
        });
        mainPanel.add(userInput, BorderLayout.NORTH);


        // Set padding around the grid
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add the main panel to the frame
        mainView.add(mainPanel, BorderLayout.CENTER);

        mainView.pack();
        mainView.setLocationRelativeTo(null);
        mainView.setVisible(true);
    }
    
    /**
     * Finds a letter in the keyboard
     * @param c the letter to search for
     * @return the coordinates of that letter in the keyboard
     */
    public int[] findLetter(char c) {
        return keyboard.findLetter(c);
    }

    /**
     * Display a message dialog with the given message.
     * 
     * @param message The message to display for the errors
     */
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(mainView, message);
    }
    
    /**

     * Sets the color of a tile in the keyboard
     * @param c the letter to set the color for
     * @param color the color to set the tile to

     */
    public void setKeyColor(char c, Color color) {
        keyboard.setKeyColor(c, color);
        grid.setKeyColor(c, color);
    }

    /**
     * Repaints the grid and keyboard
     */
    public void repaint() {
        grid.repaint();
        keyboard.repaint();
    }
    
    /**
     * Gets the color of a letter in the keyboard
     * @param c the letter to search for
     * @return the color of that letter's tile in the keyboard
     */
    public Color findColorOfLetter(char c) {
        return keyboard.findColorOfLetter(c);
    }
    
    
    /** Gets a LetterTile data packet
     * @param row the row the tile is found in
     * @param col the column the tile is found in
     * @return the contents of the tile
     */
    public LetterTile getTile(int row, int col) {
        return game.getTile(row, col);
    }
    
    /**
     * The main method to start the program.
     * 
     * @param args is the argument
     */
    
    public static void main(String[] args) {
        new WordleGUI();
    }
    
}
    

    
    
    

