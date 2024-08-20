package wordle.view;

import wordle.model.LetterTile;

/**
     * The interface for the keyboard listener to communicate with the grid
     *
     */
    public interface KeyboardToGridListener {
        
        /**
         * This method is invoked when a letter tile is pressed on the keyboard.
         * It allows the keyboard to communicate the pressed letter tile to the grid.
         * 
         * @param letterTile The letter tile corresponding to the key pressed on the keyboard.
         */
        void keyPressed(LetterTile letterTile);
        
    }

       

