package chess.domain.piece;

import chess.domain.board.Position;
import chess.domain.board.RankCoordinate;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece {

    private int moveCount;

    public Pawn(Color color) {
        super(color);
        moveCount = 0;
    }

    public Pawn(Color color, int moveCount) {
        super(color);
        this.moveCount = moveCount;
    }

    private boolean isFirstMove() {
        return this.moveCount == 0;
    }

    @Override
    public boolean canMove(Position sourcePosition, Position targetPosition, Color color) {
        boolean isSameFileCoordinate = sourcePosition.getFileCoordinate() == targetPosition.getFileCoordinate();
        int sourceRankNumber = sourcePosition.getRow();
        int targetRankNumber = targetPosition.getRow();
        int direction = this.getColor().getDirection();
        int nextRankNumber = sourceRankNumber + direction;

        boolean diagonalPath = isDiagonalPath(sourcePosition, targetPosition, color);
        if (isFirstMove()) {
            nextRankNumber = sourceRankNumber + (2 * direction);
        }
        return (isSameFileCoordinate && sourceRankNumber < targetRankNumber && targetRankNumber <= nextRankNumber)
                || diagonalPath;
    }

    private boolean isDiagonalPath(Position sourcePosition, Position targetPosition, Color color) {
        if (!this.getColor().isOpposite(color)) {
            return false;
        }
        int sourceRankNumber = sourcePosition.getRow();
        int targetRankNumber = targetPosition.getRow();
        int direction = this.getColor().getDirection();
        int diagonalRankNumber = sourceRankNumber + direction;
        int sourceFileNumber = sourcePosition.getColumn();
        int targetFileNumber = targetPosition.getColumn();

        return Math.abs(sourceFileNumber - targetFileNumber) == 1 && diagonalRankNumber == targetRankNumber
                && getColor() != color;
    }

    @Override
    public List<Position> findPath(Position sourcePosition, Position targetPosition) {
        int sourceRankNumber = sourcePosition.getRow();
        int targetRankNumber = targetPosition.getRow();
        if (Math.abs(sourceRankNumber - targetRankNumber) == 2) {
            int direction = this.getColor().getDirection();
            return List.of(new Position(sourcePosition.getFileCoordinate(),
                    RankCoordinate.findBy(sourceRankNumber + direction)));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece move() {
        return new Pawn(this.getColor(), moveCount + 1);
    }
}
