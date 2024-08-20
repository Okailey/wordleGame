package spellingBee.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import spellingBee.view.WordListDisplay;

/**
 * a program that runs functionality that is needed for the New York Time's game
 * SpellingBee
 */
public class SpellingBeeGame {

    /**
     * wordListDisplay that holds the wordList this is needed so that the
     * graphics and the game are in sync
     */
    private WordListDisplay wordListDisplay;

    /**
     * the letters that will make up the honeycomb
     */
    private String starterLetters;

    /**
     * a random generator
     */
    private Random distGen = new Random();

    /**
     * the length of the SpellingBeeWords file
     */
    private static final int SPELLING_BEE_FILE_LENGTH = 4125;

    /**
     * a list holding all words that can be entered
     */
    WordList dictionary;

    /**
     * a constructor - builds a game based on the wordListDisplay
     * 
     * @param wordListDisplay
     */
    public SpellingBeeGame(WordListDisplay wordListDisplay) {
        this.wordListDisplay = wordListDisplay;
        dictionary = new WordList(0);
        initStarterLetters();

        try {
            initDictionary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializes starterletters
     */
    private void initStarterLetters() {
        String starterWord;
        // generates a word from the SpellingBeeWords file
        try {
            starterWord = findSolutionWord("SpellingBeeWords.txt");
        } catch (FileNotFoundException e) {
            starterWord = "helpful";
        }

        dictionary.add(new Word(starterWord, true));
        starterWord = removeDuplicateLetters(starterWord);
        starterLetters = chooseMiddleLetter(starterWord);
    }

    /**
     * adds all possible words to the dictionary
     * 
     * @throws FileNotFoundException
     */
    private void initDictionary() throws FileNotFoundException {
        // goes through the common_words file
        try (Scanner in = new Scanner(new File("common_words.txt"))) {
            String next = in.nextLine();

            while (in.hasNext()) {
                next = in.nextLine();
                checkAddWordForDictionary(next);
            }
          
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        // goes through the englishWords file
        try (Scanner in = new Scanner(new File("EnglishWords.txt"))) {
            String next = in.nextLine();

            while (in.hasNext()) {
                next = in.nextLine();
                checkAddWordForDictionary(next);
            }
          
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
  
    /**
     * initializes the letters that will be appeared in honeyComb when the program starts
     */
    public void initRandomStarterLetters() {
    	char[] allLetters = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    	char[] starters = new char[7];
    	ArrayList<Integer> cnt = new ArrayList<Integer>();
    	for (int i = 0; i < starters.length;i++) {
    		int j = (int) (Math.random() * (allLetters.length - 1));
    		while (cnt.contains(j)) {
    			j = (int) (Math.random() * (allLetters.length - 1));
    		}
    		cnt.add(j);
    		starters[i] = allLetters[j];
    	}
    	
    	starterLetters = new String(starters);
    }
   
    /**
     * gets the letters the program would start with
     * @return the letters the program would start with, contained in a string of 7 characters. 
     * The letter in the middle of the honeyComb will appear as the first letter.
     */
    public String getStarterLetters() {
        return this.starterLetters;
    }

    /**
     * takes in a string and removes any duplicate letters
     * 
     * @param word the word to remove duplicates from
     * @return the duplicate-free word
     */
    private String removeDuplicateLetters(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            int extraLetterIndex = word.indexOf(word.charAt(i), i + 1);
            if (extraLetterIndex > i) {
                word = word.substring(0, extraLetterIndex)
                        + word.substring(extraLetterIndex + 1);
            }
        }
        return word;
    }

    /**
     * shuffles the starter word so that a random letter becomes the one in the
     * middle
     * 
     * @param starterWord
     * @return reconfigured word
     */
    private String chooseMiddleLetter(String starterWord) {
        String returnWord;
        int rand = distGen.nextInt(0, 6);
        if (rand == 6) {
            returnWord = starterWord.substring(rand)
                    + starterWord.substring(0, rand);
        } else {
            returnWord = starterWord.substring(rand, rand + 1)
                    + starterWord.substring(0, rand)
                    + starterWord.substring(rand + 1);
        }
        return returnWord;
    }

    /**
     * Checks if a guess is valid and returns a number based on its status and
     * what error message (if any) ought to be displayed about it
     *
     * Error message "Not in word list" if word is not in dictionary - return 1                  
     * Error message "Already found" if word already in word list - return 2                                SOLVED
     * Error message "Missing center letter" if center letter not included in user’s word - return 3         SOLVED
     * Error message "Too short" if fewer than 4 letters - return 4                                         SOLVED
     * Error message "Invalid letters" if letters entered that are not in the honeycomb. -return 5           SOLVED
     * Also checks if the word is pangram
     * If correct return 0
     *
     * @param wordToAdd
     * @return an int as described above
     */
    public int checkAddWord(String wordToAdd) {
        //convert to lowercase
        String lowerCase= wordToAdd.toLowerCase(); 
        System.out.println("Word to add: " + lowerCase);
        System.out.println("Starter letters: " + starterLetters);
        System.out.println("Middle letter present: " + (lowerCase.indexOf(starterLetters.charAt(0)) >= 0));

        // checks if word is too short
        if (wordToAdd.length() < 4) {
            return 4;
        }
        // checks if word has middle letter
        else if (wordToAdd.indexOf(starterLetters.charAt(0)) < 0) {
            System.out.println("Middle letter present: false");
            return 3;
        
        } else {
            boolean willBePangram = false;
            String wordToCheck = removeDuplicateLetters(wordToAdd);
            int letterCount = 0;
            // for loop checks for both pangram status
            // and for letters not in the honeycomb
            for (int i = 0; i < wordToCheck.length(); i++) {
                char currentLetter = wordToCheck.charAt(i);
                System.out.println("Checking letter: " + currentLetter);
                boolean inHoneycomb = starterLetters.indexOf(currentLetter) >= 0;
                System.out.println("Is in honeycomb: " + inHoneycomb);
                if (!inHoneycomb) {
                    return 5;
                }
                letterCount++;
            }
            // if we've gotten to this point, the word is a pangram
            if (letterCount == 7) {
                willBePangram = true;
            }
            Word word = new Word(wordToAdd, willBePangram);
            if (wordListDisplay.getWordList().contains(word)) {
                return 2;
            }
            if (!(dictionary.contains(word))) {
                return 1;
            }
         // if we've gotten this far, word is good to be added
            wordListDisplay.add(word);
            return 0;
        }
    }
            
            
            

    /**
     * reads in a random word from SpellingBeeWords.txt
     * 
     * @param filename should be "sepllingBeeWords.txt"
     * @return a pangram string
     * @throws FileNotFoundException
     */
    private String findSolutionWord(String filename)
            throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(filename))) {
            String next = in.nextLine();
            int stopPoint = distGen.nextInt(0, SPELLING_BEE_FILE_LENGTH);
            for (int i = 0; i < stopPoint; i++) {
                next = in.nextLine();
            }
            return next;
        }
    } 
      
    /**
     * a method very similar to checkAddWord, but instead adds the word to the
     * dictionary
     * 
     * @param wordToAdd a string to check
     * @return return true if word was added, false otherwise
     */
    public boolean checkAddWordForDictionary(String wordToAdd) {
        // checks if word is too short
        if (!(wordToAdd.length() < 4)
                && !(wordToAdd.indexOf(starterLetters.charAt(0)) < 0)) {
            boolean willBePangram = false;
            String wordToCheck = removeDuplicateLetters(wordToAdd);
            int letterCount = 0;
            // for loop checks for both pangram status
            // and for letters not in the honeycomb
            for (int i = 0; i < wordToCheck.length(); i++) {
                // if a letter isn't in the honeycomb
                if (starterLetters.indexOf(wordToAdd.charAt(i)) < 0) {
                    return false;
                }
                letterCount++;
            }
            // if we've gotten to this point, the word is a pangram
            if (letterCount == 7) {
                willBePangram = true;
            }
            // if we've gotten this far, word is good to be added
            dictionary.add(new Word(wordToAdd, willBePangram));
            return true;
        }
        return false;
    }

}
