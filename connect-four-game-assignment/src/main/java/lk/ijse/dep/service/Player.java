package lk.ijse.dep.service;

public abstract class Player{
    protected Board board; //This variable is a member of the Player class and can be accessed by subclasses within the same package.
    public abstract void movePiece(int col);

    public Player(Board newBoard){ //The constructor takes one parameter of type Board named newBoard.

        this.board = newBoard;
    }
}
