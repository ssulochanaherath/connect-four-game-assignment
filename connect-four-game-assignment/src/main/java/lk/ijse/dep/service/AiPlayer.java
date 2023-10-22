package lk.ijse.dep.service;

public class AiPlayer extends Player {
    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
        // Let the AI decide the best column to play
        col = predictColumn();

        // Make the move in the chosen column
        board.updateMove(col, Piece.GREEN);
        board.getBoardUI().update(col, false);

        // Check for a winner or a tie
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            board.getBoardUI().notifyWinner(winner);
        } else {
            if (!board.existLegalMoves()) {
                board.getBoardUI().notifyWinner(winner);
            }
        }
    }

    private int predictColumn() {
        boolean isUserWarning = false;
        int tiedColumn = 0;

        // Iterate through columns
        for (int col = 0; col < 6; ++col) {
            if (board.isLegalMove(col)) {
                int row = board.findNextAvailableSpot(col);

                // Make a hypothetical move for the AI and calculate a heuristic value
                board.updateMove(col, Piece.GREEN);
                int heuristicValue = minMax(0, false);
                board.updateMove(col, row, Piece.EMPTY);

                if (heuristicValue == 1) {
                    return col; // If the AI can win, return this column
                }
                if (heuristicValue == -1) {
                    isUserWarning = true;
                } else {
                    tiedColumn = col; // Store a column where there is no immediate win or loss
                }
            }
        }

        if (isUserWarning && board.isLegalMove(tiedColumn)) {
            return tiedColumn; // Block the user's win
        } else {
            // If there is no winning move or blocking move, choose a random column
            int randomCol;
            do {
                randomCol = (int) (Math.random() * 6.0);
            } while (!board.isLegalMove(randomCol));
            return randomCol;
        }
    }

    private int minMax(int depth, boolean maximizingPlayer) {
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            return 1; // AI wins
        } else if (winner.getWinningPiece() == Piece.BLUE) {
            return -1; // User wins
        } else if (board.existLegalMoves() && depth == 2) {
            return 0; // It's a tie
        }

        int bestScore;

        if (maximizingPlayer) {
            bestScore = Integer.MIN_VALUE;
            for (int col = 0; col < 6; ++col) {
                if (board.isLegalMove(col)) {
                    int row = board.findNextAvailableSpot(col);

                    // Make a hypothetical move for the AI and call minMax recursively
                    board.updateMove(col, Piece.GREEN);
                    int score = minMax(depth + 1, false);
                    board.updateMove(col, row, Piece.EMPTY);

                    // Update the best score
                    if (score > bestScore) {
                        bestScore = score;
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int col = 0; col < 6; ++col) {
                if (board.isLegalMove(col)) {
                    int row = board.findNextAvailableSpot(col);

                    // Make a hypothetical move for the user and call minMax recursively
                    board.updateMove(col, Piece.BLUE);
                    int score = minMax(depth + 1, true);
                    board.updateMove(col, row, Piece.EMPTY);

                    // Update the best score
                    if (score < bestScore) {
                        bestScore = score;
                    }
                }
            }
        }
        return bestScore;
    }
}
