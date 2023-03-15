package chess.domain.piece;

import chess.domain.board.Position;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    boolean canMove(Position sourcePosition, Position targetPosition) {
        int sourceColumnNumber = sourcePosition.getFileCoordinate().getColumnNumber();
        int targetColumnNumber = targetPosition.getFileCoordinate().getColumnNumber();
        int sourceRankNumber = sourcePosition.getRankCoordinate().getRowNumber();
        int targetRankNumber = targetPosition.getRankCoordinate().getRowNumber();

        if (Math.abs(sourceColumnNumber - targetColumnNumber) == 2) {
            return Math.abs(sourceRankNumber - targetRankNumber) == 1;
        }
        if (Math.abs(sourceColumnNumber - targetColumnNumber) == 1) {
            return Math.abs(sourceRankNumber - targetRankNumber) == 2;
        }
        return false;
    }

    @Override
    boolean isKing() {
        return false;
    }

    @Override
    Piece move() {
        return null;
    }
}
