package lk.ijse.dep.service;

import lk.ijse.dep.controller.BoardController;

public class BoardImpl implements Board{
    private BoardUI boardUI;
    private Piece[][] pieces;
    private static final int NUM_OF_COLS = 6;
    private static final int NUM_OF_ROWS = 5;

    public BoardImpl(BoardUI boardUI){ //constructor for BoardImpl. It initializes the boardUI field with the provided instance and creates an empty game board by initializing the pieces array.
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        initializeBoard();
    }

    private void initializeBoard(){ //It populates the pieces array with Piece.EMPTY to represent that the game board is initially empty.
        for(int col = 0; col < NUM_OF_COLS; col++){
            for(int row = 0; row < NUM_OF_ROWS; row++){
                pieces[col][row] = Piece.EMPTY;
            }
        }
    }

    @Override
    public BoardUI getBoardUI() { //It provides access to the boardUI field, allowing other classes to interact with the user interface.

        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col){ //starts from the bottom of the column and goes upward, returning the row index of the first empty spot. If the column is full, it returns -1.
        for(int row = NUM_OF_ROWS - 1; row >= 0; row--){
            if(pieces[col][row] == Piece.EMPTY) {
                return row;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col){ //This method checks if making a move in the specified column is a legal move

        return findNextAvailableSpot(col) != -1;
    }

    @Override
    public boolean existLegalMoves(){ //if any column has at least one legal move. If it finds at least one legal move, it returns true
        for(int col = 0; col < NUM_OF_COLS; col++){
            if(isLegalMove(col)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col,Piece move){  //find the row where the piece should be placed in the specified column and updates the pieces array accordingly.

        pieces[col][findNextAvailableSpot(col)] = move;
    }

    @Override
    public void updateMove(int col,int row,Piece move){ //both the column and the row where the Piece should be placed. This is used for moves where you want to specify a particular position on the board.

        pieces[col][row] = move;
    }
    @Override
    public Winner findWinner(){
        for(int row = 0; row < NUM_OF_ROWS; row++){ //check the horizontal win
            for(int col = 0; col < NUM_OF_COLS - 3; col++){  //if the same piece exists in the next three columns (horizontally). If all four pieces are the same, a horizontal win is detected.
                Piece piece = pieces[col][row];
                if(piece != Piece.EMPTY && piece == pieces[col + 1][row] && piece == pieces[col + 2][row] && piece ==
                        pieces[col + 3][row]){
                    return new Winner(piece , col , row ,col + 3, row);
                }
            }
        }

        for(int col = 0; col < NUM_OF_COLS; col++){ //check the vertical win
            for(int row = 0; row < NUM_OF_ROWS - 3; row++){
                Piece piece = pieces[col][row];
                if(piece != Piece.EMPTY && piece == pieces[col][row + 1] && piece == pieces[col][row + 2] && piece ==
                        pieces[col][row + 3]){
                    return new Winner(piece,col,row,col,row + 3);
                }
            }
        }
        return new Winner(Piece.EMPTY,-1,-1,-1,-1); //the method returns a Winner object with an EMPTY piece, and all coordinates set to -1. This likely indicates that the game has not been won, and it's still ongoing.
    }
}
