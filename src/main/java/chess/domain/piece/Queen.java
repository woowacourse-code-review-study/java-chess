package chess.domain.piece;

import chess.domain.board.FileCoordinate;
import chess.domain.board.Position;
import chess.domain.board.RankCoordinate;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean canMove(Position sourcePosition, Position targetPosition, Color color) {

        return (isStraight(sourcePosition, targetPosition) || isDiagonal(sourcePosition, targetPosition))
                && isNotMyPosition(sourcePosition, targetPosition) && getColor() != color;
    }

    @Override
    public List<Position> findPath(Position sourcePosition, Position targetPosition) {
        int nowFileCoordinate = sourcePosition.getColumn();
        int nowRankCoordinate = sourcePosition.getRow();
        int targetFileCoordinate = targetPosition.getColumn();
        int targetRankCoordinate = targetPosition.getRow();

        if (nowFileCoordinate - targetFileCoordinate == 0) {
            return getPathByRank(sourcePosition, targetRankCoordinate);
        }
        if (nowRankCoordinate - targetRankCoordinate == 0) {
            return getPathByFile(sourcePosition, targetFileCoordinate);
        }
        return getPathByDiagonal(sourcePosition, targetFileCoordinate, targetRankCoordinate);
    }

    private List<Position> getPathByDiagonal(Position sourcePosition, int targetFileCoordinate,
                                             int targetRankCoordinate) {
        List<Position> paths = new ArrayList<>();
        int nowFileCoordinate = sourcePosition.getColumn();
        int nowRankCoordinate = sourcePosition.getRow();

        int columnStep = getStep(nowFileCoordinate, targetFileCoordinate);
        int rowStep = getStep(nowRankCoordinate, targetRankCoordinate);

        while (nowFileCoordinate + columnStep != targetFileCoordinate) {
            nowFileCoordinate += columnStep;
            nowRankCoordinate += rowStep;
            Position position = new Position(FileCoordinate.findBy(nowFileCoordinate),
                    RankCoordinate.findBy(nowRankCoordinate));
            paths.add(position);
        }
        return paths;
    }

    private List<Position> getPathByFile(Position sourcePosition, int targetFileCoordinate) {
        List<Position> paths = new ArrayList<>();
        int nowFileCoordinate = sourcePosition.getColumn();
        int columnStep = getStep(nowFileCoordinate, targetFileCoordinate);
        while (nowFileCoordinate + columnStep != targetFileCoordinate) {
            nowFileCoordinate += columnStep;
            Position position = new Position(FileCoordinate.findBy(nowFileCoordinate),
                    sourcePosition.getRankCoordinate());
            paths.add(position);
        }
        return paths;
    }

    private List<Position> getPathByRank(Position sourcePosition, int targetRankCoordinate) {
        List<Position> paths = new ArrayList<>();
        int nowRankCoordinate = sourcePosition.getRow();
        int rowStep = getStep(nowRankCoordinate, targetRankCoordinate);
        while (nowRankCoordinate + rowStep != targetRankCoordinate) {
            nowRankCoordinate += rowStep;
            Position position = new Position(sourcePosition.getFileCoordinate(),
                    RankCoordinate.findBy(nowRankCoordinate));
            paths.add(position);
        }
        return paths;
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
        return new Queen(getColor());
    }
}
