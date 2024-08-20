package spellingBee.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import spellingBee.model.Word;
import spellingBee.model.WordList;

public class WordListDisplay extends JEditorPane{
    
    private WordList wordList;
    String words;
    int score;

    /**
     * initializes a display with wordList
     * @param wordList the WordList to be displayed
     */
    public WordListDisplay(WordList wordList) {
        super.setContentType("text/html");
        this.wordList = wordList;
        updateWordsString();
        score= wordList.getSize();
    }
    
    /**
     * initializes a display with a starter word
     * @param word the word to be displayed
     */
    public WordListDisplay(Word word) {
        super.setContentType("text/html");
        wordList = new WordList(word);
        updateWordsString();
        score = 1;
        
    }
    
    /**
     * intializes a display with an empty word list
     */
    public WordListDisplay() {
        super.setContentType("text/html");
        wordList = new WordList(0);
        words = new String();
        score = 0;
    }
    
    /**
     * adds a word to the display
     * @param word word to be added to the display
     */
    public void add (Word word) {
        wordList.add(word);
        score++;
        updateWordsString();
    }
    
    /**
     * updates the text displayed by the editor pane
     */
    private void updateWordsString() {
        words = new String (" You have found "+score+" words!"+"<br> <br>");
        for(int i=0; i<wordList.getSize(); i++) {
            if(wordList.get(i).isPangram()) {
                words = words +("<b> "+wordList.get(i).toString()+"</b> <br>");
            } else {
                words = words + (" "+wordList.get(i).toString() +"<br>");
            }
        }
        this.setText(words);
    }
    
    /**
     * a getter for score
     * @return the number of words found
     */
    public int getScore() {
        return score;
    }
    
    /**
     * a getter for the wordList that is displayed
     * @return
     */
    public WordList getWordList() {
        return wordList;
    }
    
}
