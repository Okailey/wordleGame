package wordle.model;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class that holds an array of five-letter common words
 * And an array of almost all the five-letter words in the English alphabet.
 */
public class WordleWordBank {
    private File commonWordFile;
    private File allWordsFile;
    private String[] commonWords;
    private String[] allWords;
    private Scanner commonReader;
    private Scanner allReader;
    
    /**
     * Creates a new WordBank for WordleGame to use
     */
    public WordleWordBank() {
        this.commonWordFile = new File("common_words.txt");
        this.allWordsFile = new File("EnglishWords.txt");
        
        try {
            this.commonReader = new Scanner(commonWordFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        
        try {
            
            this.allReader = new Scanner(allWordsFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        
        
        // Creates an ArrayList to hold all the common words, then casts it to an Array
        ArrayList<String> tempCommonList = new ArrayList<String>();
        while(commonReader.hasNextLine()) {
            String word = commonReader.nextLine();
            if (word.length() == 5) {
                tempCommonList.add(word.toUpperCase());
            }
        }
        
        commonReader.close();
        
        this.commonWords = tempCommonList.toArray(new String[0]);
        
        // Creates an ArrayList to hold all the words, then casts it to an Array
        ArrayList<String> tempAllWordList = new ArrayList<String>();
        while(allReader.hasNextLine()){
            String word = allReader.nextLine();
            if(word.length()==5) {
                tempAllWordList.add(word.toUpperCase());
            }
        }
        
        allReader.close();
        
        this.allWords = tempAllWordList.toArray(new String[0]);
        
    }
    
    /**
     * Access the list of common words
     * @return An array of common five-letter words
     */
    public String[] getCommonWords() {
        return commonWords;
    }
    
    /**
     * Access the list of all words
     * @return An array of all five-letter words
     */
    public String[] getAllWords() {
        return allWords;
    }
    
    /**
     * Fetches a word from commonWords array
     * @param index the index to fetch the word from
     * @return the word at index
     */
    public String getCommonWordAt(int index) {
        return commonWords[index];
    }
    
    /**
     * Gets a word from allWords array
     * @param index the index of the word
     * @return the word at index
     */
    public String getAnyWordAt(int index) {
        return allWords[index];
    }
    
    /**
     * Fetches a random common word
     * @return a random entry from commonWords array
     */
    public String getRandomCommonWord() {
        int index = (int) ((commonWords.length) * Math.random());
        return commonWords[index];
    }
    
    /**
     * Fetches a random word from allWords
     * @return a random entry from allWords array
     */
    public String getRandomAnyWord() {
        int index = (int) ((allWords.length) * Math.random());
        return allWords[index];
    }
    
    /**
     * Searches the list of all words to see if a certain word exists
     * @param s The word to be searched for
     * @return True if the word exists, false if not.
     * i changed the == to .equals as we are checking for the content 
     */
    public Boolean wordExists(String s) {
        boolean exists = false;
        for(int i = 0; i < allWords.length; i++) {
            if(allWords[i].equals(s.toUpperCase())){
                exists = true;
            }
        }
        return exists;
    }
}
