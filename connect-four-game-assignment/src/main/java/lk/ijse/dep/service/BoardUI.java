package lk.ijse.dep.service;

public interface BoardUI { //the interface is accessible from any other class.
    void update(int col, boolean isHuman); //This indicates that the method does not return any value.

    void notifyWinner(Winner winner);
}
