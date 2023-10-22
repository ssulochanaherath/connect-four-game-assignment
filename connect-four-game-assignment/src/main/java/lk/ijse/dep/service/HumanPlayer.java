package lk.ijse.dep.service;

public class HumanPlayer extends Player {
    public HumanPlayer(Board board) { //The constructor initializes the HumanPlayer by calling the constructor of the superclass (Player) using the super(board) statement. This sets up the player with the game board.

        super(board);
    }

    @Override
    public void movePiece(int col){ //the human player is making a move by specifying a column (col)
        if(board.isLegalMove(col)){
            board.updateMove(col,Piece.BLUE);
            board.getBoardUI().update(col,true);

            Winner winner = board.findWinner();
            if(winner.getWinningPiece() == Piece.BLUE){ //If the winning piece is blue, it notifies the game board's UI (User Interface) by calling board.getBoardUI().notifyWinner(winner). This likely displays a message or performs some action to indicate that the human player has won the game.
                board.getBoardUI().notifyWinner(winner);
            }else if(!board.existLegalMoves()){  //If there are no legal moves left (indicating a draw or stalemate), it also calls board.getBoardUI().notifyWinner(winner) to notify the UI about the game's outcome.
                board.getBoardUI().notifyWinner(winner);

            }
        }
    }

}
