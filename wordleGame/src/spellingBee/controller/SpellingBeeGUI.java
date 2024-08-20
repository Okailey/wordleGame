package spellingBee.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.LineBorder;
import spellingBee.view.*;
import spellingBee.model.*;
import javax.swing.JOptionPane;

/**
 * SpellingBeeGUI is a program that creates the visual elements of the New York
 * Times game SpellingBee it interacts with a model class SpellingBee game that
 * does much of the functionality
 */
public class SpellingBeeGUI {

    /**
     * the honeycomb that appears on screen
     */
    private Honeycomb honeycomb;

    /**
     * the wordListDisplay that appears on the right of the screen
     */
    private WordListDisplay wordListDisplay;

    /**
     * the text field that users type in when they want to guess a word
     */
    private JTextField textField;

    /**
     * the model class through which complicated actions are controlled
     */
    private SpellingBeeGame game;

    /**
     * constructs a game and displays view items
     */
    public SpellingBeeGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel north = new JPanel();
        JLabel messageLabel = new JLabel("Please type in lowercase letters only");
        north.add(messageLabel);
        f.add(north, BorderLayout.NORTH);
        
        JPanel east = new JPanel();
        initializeWordListDisplay(east);
        game = new SpellingBeeGame(wordListDisplay);

        JPanel center = new JPanel();
        initializeTextField(center);
        initializeButtons(center);
        game = new SpellingBeeGame(wordListDisplay);
        initializeHoneyComb(center);

        f.add(center, BorderLayout.CENTER);
        f.add(east, BorderLayout.EAST);
        f.setSize(800, 550);
        f.setVisible(true);
    }

    /**
     * creates the honeycomb and adds it to panel p
     * 
     * @param p the panel to honeycomb will be added to
     */
    private void initializeHoneyComb(JPanel p) {
        honeycomb = new Honeycomb(game.getStarterLetters(), 50, 50);
        honeycomb.setPreferredSize(new Dimension(500, 400));
        p.add(honeycomb);
        honeycomb.shuffle();
    }

    /**
     * creates the wordlistdisplay and adds it to panel p
     * 
     * @param p the panel wordlistdisplay will be added to
     */
    private void initializeWordListDisplay(JPanel p) {
        wordListDisplay = new WordListDisplay();
        wordListDisplay.setPreferredSize(new Dimension(200, 500));
        wordListDisplay.setBorder(new LineBorder(Color.BLACK));

        JScrollPane scroller = new JScrollPane(wordListDisplay);
        p.add(scroller);
    }

    /**
     * initializes the instance variable textField
     * 
     * @param p
     */
    private void initializeTextField(JPanel p) {
        textField = new JTextField(15);
        p.add(textField);
    }

    /**
     * initalizes the shuffle and enter buttons
     * 
     * @param p
     */
    private void initializeButtons(JPanel p) {
        // the flow panel will hold two buttons
        JPanel flow = new JPanel();

        // the shuffle button will shuffle the honeycomb
        JButton shuffle = new JButton("Shuffle");
        shuffle.addActionListener(event -> honeycomb.shuffle());
        flow.add(shuffle);

        // the enter button will enter a word
        JButton enter = new JButton("Enter");
        enter.addActionListener(event -> submitWord());
        flow.add(enter);
        p.add(flow);
    }

    /**
     * the method called when the enter button is pushed this submits an entered
     * word to the game, to check if its valid it then either adds it to the
     * wordlist or displays an error message
     */
    private void submitWord() {
        String word = new String(textField.getText());
        if (word.isEmpty()) {
            initializeErrorPopup(" Please input a word");
        }
        textField.setText("");

        // creates an error popup (if necessary) using helper methods
        int errorType = game.checkAddWord(word);
        if (errorType != 0) {
            String message = getErrorMessage(errorType);
            initializeErrorPopup(message);
        }
    }

    /**
     * creates an error pop-up 
     * 
     * @param message the error message to be displayed
     */
    private void initializeErrorPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * generates an error message based on the return value of SpellingBeeGame's
     * method checkAddWord
     * 
     * @param errorType the int returned by checkAddWord
     * @return an error message
     */
    private String getErrorMessage(int errorType) {
        if (errorType == 1) {
            return "Not in word list";
        } else if (errorType == 2) {
            return "Already found";
        } else if (errorType == 3) {
            return "Missing center letter";
        } else if (errorType == 4) {
            return "Too short, must be at least 4 letters long";
        } else if (errorType == 5) {
            return "Invalid letters. Some of the letters are not in the honeycomb. Use honeycomb letters only!";
        }

        return "";
    }

    /**
     * the main method
     * 
     * @param args read in paramaters
     */
    public static void main(String[] args) {
        SpellingBeeGUI bee = new SpellingBeeGUI();
    }
}
