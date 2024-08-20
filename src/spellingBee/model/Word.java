package spellingBee.model;

public class Word implements Comparable<Word> {

    /**
     * the string the word represents
     */
    private String word;

    /**
     * whether or not the word is a pangram (this means that it has all the
     * letters in the honeycomb)
     */
    private boolean isPangram;

    /**
     * contructs a word with specified pangram status
     * 
     * @param word      the string that the word will hold
     * @param isPangram whether the word will be a pangram
     */
    public Word(String word, boolean isPangram) {
        this.word = word;
        this.isPangram = isPangram;
    }

    /**
     * contructs a non=pangram word
     * 
     * @param word the string version of the word
     */
    public Word(String word) {
        this.word = word;
        this.isPangram = false;
    }

    /**
     * returns a string version of word
     * 
     * @return this word
     */
    public String toString() {
        return this.word;
    }

    /**
     * a setter for the word's pangram status
     */
    public void setPangram(boolean isPangram) {
        this.isPangram = isPangram;
    }

    /**
     * a getter for whether a given word is a pangram
     * 
     * @return whether word is a pangram
     */
    public boolean isPangram() {
        return this.isPangram;
    }

    /**
     * @assert all words should not contain capitalized letters
     * @param newWord the word to be compared with
     * @return 0 if two words are the same, -1 if the word is should be placed
     *         in front of the current word, 1 if the word should be placed
     *         latter
     */
    @Override
    public int compareTo(Word newWord) {
        if (this.word.equals(newWord.toString())) {
            return 0;
        } else if (this.word.compareTo(newWord.toString()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

}
