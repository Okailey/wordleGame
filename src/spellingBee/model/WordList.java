package spellingBee.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WordList {

    ArrayList<Word> words;

    /**
     * creates a wordList of input size wordlist will expand if more words than
     * size are added
     * 
     * @param size length of list
     */
    public WordList(int size) {
        words = new ArrayList<Word>(size);
    }

    /**
     * creates a wordlist containing the input word
     * 
     * @param word word to initialize with
     */
    public WordList(Word word) {
        words = new ArrayList<Word>();
        words.add(word);
    }

    /**
     * adds a word to the list
     * 
     * @param word the word to be added
     */
    public void add(Word word) {
        words.add(word);
    }

    /**
     * a getter for the word at a specific location
     * 
     * @param i the location in the list of a specific word
     * @return the word at that location
     */
    public Word get(int i) {
        return words.get(i);
    }

    /**
     * adds a word to the WordList, baypasses alphebatization
     * 
     * @param word the word to be added
     */
    public void directlyAdd (Word word) {
    	words.add(word);
    }	


    /**
     * checks if a certain word is in the list
     * 
     * @param word to find in the list
     * @return true if the word exists, false otherwise
     */
    public boolean contains(Word word) {
    	for (Word thisWord : words) {
    		if (thisWord.compareTo(word) == 0) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * a getter for the size of the wordList
     * 
     * @return the size of the list
     */
    public int getSize() {
        return words.size();
    }
}
