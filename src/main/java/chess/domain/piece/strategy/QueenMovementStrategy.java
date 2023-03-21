package chess.domain.piece.strategy;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.position.PiecePosition;

public class QueenMovementStrategy extends AbstractPieceMovementStrategy {

    public QueenMovementStrategy(final Color color) {
        super(color);
    }

    @Override
    protected void validateMoveWithNoAlly(final PiecePosition source,
                                          final PiecePosition destination,
                                          final Piece nullableEnemy) throws IllegalArgumentException {
        if (!isStraight(source, destination) && !isDiagonal(source, destination)) {
            throw new IllegalArgumentException("퀸은 대각선 혹은 직선으로만 이동가능합니다.");
        }
    }
}
