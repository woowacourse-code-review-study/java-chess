package chess.domain.piece.state;

import chess.domain.piece.ColorCompareResult;
import chess.domain.piece.PieceType;
import chess.domain.piece.exception.IllegalPieceMoveException;

public class EmptyState implements MoveState {

    private static final EmptyState instance = new EmptyState();
    private static final PieceType pieceType = PieceType.EMPTY;

    private EmptyState() {
    }

    public static EmptyState getInstance() {
        return instance;
    }

    @Override
    public boolean canMove(int fileDifference, int rankDifference, ColorCompareResult colorCompareResult) {
        throw new IllegalPieceMoveException();
    }

    @Override
    public MoveState getNextState() {
        throw new IllegalPieceMoveException();
    }

    @Override
    public PieceType getType() {
        return pieceType;
    }
}
