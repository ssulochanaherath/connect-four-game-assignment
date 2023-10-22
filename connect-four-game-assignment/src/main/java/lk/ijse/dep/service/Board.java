package lk.ijse.dep.service;

public interface Board {
    int NUM_OF_ROWS = 5;
    int NUM_OF_COLS = 6;

    BoardUI getBoardUI(); //return an object of type BoardUI

    int findNextAvailableSpot(int col); //This method is expected to find the next available spot (row) in the specified column and return its index as an integer.

    boolean isLegalMove(int col); //if a move in the specified column is legal, and it returns a boolean value (true if the move is legal, false otherwise).

    boolean existLegalMoves(); //check if there are any legal moves left on the game board and return a boolean value

    void updateMove(int col,Piece move); //This method is intended to update the game board with the specified move in the given column.

    void updateMove(int col,int row,Piece move); //This method is intended to update the game board with the specified move in the specified cell.

    Winner findWinner(); //represents the outcome or result of the game

}
