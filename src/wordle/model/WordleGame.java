package wordle.model;

import java.awt.Color;

import wordle.controller.*;



/**
 * A single instance of a game object.
 */
public class WordleGame {
    private LetterTile[][] answerGrid;
    private String answer;
    private int currentRow = 0;
    private int currentCol = 0;
    private WordleGUI gui;
    private WordleWordBank wordBank;
    
    // Error message if the user enters a word that is too short
    private static final String WORD_IS_TOO_SHORT = "This is too short. Please enter a word that is 5 letters long";
    
    // Error message if the user enters an invalid input(numbers, symbols, etc).
    private static final String INVALID_INPUT = "Invalid input, Wordle uses only the alphabets from A-Z so use letters only";

    // Error message if the user enters a word that is too long
    private static final String WORD_IS_TOO_LONG = "This is too long. Please enter a word that is 5 letters long";

    // Error message if the user enters a word that is invalid
    private static final String INVALID_WORD = "Not a valid word. Please enter a valid word";

    // message if the user guesses the word and wins
    private static final String YOU_WIN = "You genius!!! You win!!)";

    /**
     * Fetches the answerGrid array
     * 
     * @return The raw data within the answerGrid
     */
    public LetterTile[][] getAnswerGrid() {
        return answerGrid;
    }

    /**
     * Sets up a new WordleGame (does not start the game on its own)
     * 
     * @param gui the controller GUI that owns the WordleGame - call using
     *            (this) from the GUI
     */
    public WordleGame(WordleGUI gui) {

        // Points back to the gui that created the game
        this.gui = gui;

        // Word bank for the game to draw from
        this.wordBank = new WordleWordBank();

        // Chooses a random answer from the common words in the word bank.
        this.answer = wordBank.getRandomCommonWord();
        
        // Two-dimensional array that will hold the user's answers.
        this.answerGrid = new LetterTile[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++)
                this.answerGrid[i][j] =  new LetterTile(new Color(255, 255, 255, 0), ' ');            
        }
    }

    /**
     * Fetches a tile from the grid
     * @param row the row the tile is found in
     * @param col the column the tile is found in
     * @return the contents of the tile
     */
    public LetterTile getTile(int row, int col) {
        return answerGrid[row][col];
    }
    
    /**
     * Checks to see if the user's guess was correct.
     * 
     * @param guess The user's input guess
     */
    public void checkGuess(String guess) {
        // Check if the guess length is 5.
        if (guess.length() != 5) {
            if (guess.length() < 5) {
                gui.displayMessage(WORD_IS_TOO_SHORT);
            } else {
                gui.displayMessage(WORD_IS_TOO_LONG);
            }
            return;
        }

        // Check if the guess contains only letters.
        for (char c : guess.toCharArray()) {
            if (!Character.isLetter(c)) {
                gui.displayMessage(INVALID_INPUT);
                return;
            }
        }

        // Check if the guess is in the word bank
        if (!wordBank.wordExists(guess)) {
            gui.displayMessage(INVALID_WORD);
            return;
        }


        // Check if the entered word is the right length
        if (guess.length() == 5) {
            if(wordBank.wordExists(guess) == false) {
                System.out.println(guess + " is not a valid word!");
                return;
            }
            
            // If the guess is correct, turn every relevant tile green and win the game
            if (guess.equals(answer)) {
                for (int i = 0; i < 5; i++) {
                    answerGrid[currentRow][i].setLetter(guess.charAt(i));
                    answerGrid[currentRow][i].setColor(Color.green);
                }
                gui.repaint();
                endGame(true);
                return;
                
            } else {
                // If the guess was incorrect, turn all letters in the correct place green,
                // those in the wrong place yellow, those not in the word dark gray.
                // If a tile in the keyboard is already green, does not overwrite it.
                for (int i = 0; i < 5; i++) {
                    answerGrid[currentRow][i].setLetter(guess.charAt(i));
                    if (guess.charAt(i) == (answer.charAt(i))) {
                        answerGrid[currentRow][i].setColor(Color.green);
                        gui.setKeyColor(guess.charAt(i), Color.green);
                        System.out.println(guess.charAt(i) + " is in the correct position!");
                        gui.repaint();

                    } else if (answer.indexOf(guess.charAt(i)) != -1) {
                        answerGrid[currentRow][i].setColor(Color.yellow);
                        if (gui.findColorOfLetter(guess.charAt(i))!=Color.green) {
                            gui.setKeyColor(guess.charAt(i), Color.yellow);
                        }
                        System.out.println(guess.charAt(i) + " is in the word, but not in the correct position!");
                       gui.repaint();
                    } else {
                        answerGrid[currentRow][i].setColor(Color.gray);
                        if (gui.findColorOfLetter(guess.charAt(i))!=Color.green&&gui.findColorOfLetter(guess.charAt(i))!=Color.yellow) {
                            gui.setKeyColor(guess.charAt(i), Color.gray);
                        }
                        System.out.println(guess.charAt(i) + " is not in the word!");
                        gui.repaint();
                    }
                }
            }
        }
        
        // Resets the current column to the beginning and moves down one row
        currentCol = 0;
        currentRow = currentRow + 1;
        
        // If you have run out of rows, lose the game.
        if (currentRow >= answerGrid.length) {
            endGame(false);
        }
        return;
    }

    

    /**
     * Helper method to create a String from the letters in a row
     * 
     * @return A String made of five letters in the same row
     */
    public String concatGuess() {
        String guess = "";
        for (int i = 0; i < 5; i++) {
            guess = guess + answerGrid[currentRow][i].getLetter();
        }
        return guess;
    }

    /**
     * Helper method that ends the game and informs the GUI.
     * 
     * @param won true if the game was won, false if lost.
     */
    private void endGame(boolean won) {

        if (won) {
            gui.displayMessage(YOU_WIN);
        } else {
            gui.displayMessage(
                    "Better luck next time. The word to guess is " + answer);

        }

    }

    /**
     * Adds a letter to the grid in the next available tile.
     * If the letter was the enter key, checkGuess
     * If the letter was delete/backspace, remove the most recent letter and go back one column
     * 
     * @param letter the letter to be added
     */
    public void addLetter(char letter) {
        if (letter == '\u21B5') {
            String guess = concatGuess();
            System.out.println(guess);
            checkGuess(guess);
            gui.repaint();
            return;
        } else if (letter == '\u2190') {
            if (currentCol > 0) {
                currentCol = currentCol - 1;
            }
            answerGrid[currentRow][currentCol].setLetter(' ');
            gui.repaint();
            return;
        } else if (currentCol < answerGrid[currentRow].length
                && answerGrid[currentRow][currentCol].getLetter() == ' ') {
            answerGrid[currentRow][currentCol].setLetter(letter);
            currentCol = currentCol + 1;
            gui.repaint();
            return;
        }
        
    }

    /**
     * This gets the current row
     * 
     * @return the current row the user is on
     */
    public int getCurrentRow() {
        return currentRow;
    }
}




