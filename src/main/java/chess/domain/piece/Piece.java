package chess.domain.piece;

import chess.domain.piece.position.Path;
import chess.domain.piece.position.PiecePosition;
import chess.domain.piece.strategy.PieceMovement;

import java.util.List;

public class Piece {

    protected final Color color;
    protected final PiecePosition piecePosition;
    protected final PieceMovement pieceMovement;

    public Piece(final Color color, final PiecePosition piecePosition, final PieceMovement pieceMovement) {
        this.color = color;
        this.piecePosition = piecePosition;
        this.pieceMovement = pieceMovement;
    }

    /**
     * @throws IllegalArgumentException 이동할 수 없는 경로가 들어온 경우
     */
    public List<PiecePosition> waypoints(final PiecePosition destination, final Piece nullablePiece) throws IllegalArgumentException {
        return pieceMovement.waypoints(color, path(destination), nullablePiece);
    }

    /**
     * @throws IllegalArgumentException 이동할 수 없는 경로가 들어온 경우
     */
    public Piece move(final PiecePosition destination, final Piece nullablePiece) throws IllegalArgumentException {
        final Path path = path(destination);
        pieceMovement.validateMove(color, path, nullablePiece);
        return new Piece(color, destination, pieceMovement);
    }

    public boolean existIn(final PiecePosition piecePosition) {
        return this.piecePosition.equals(piecePosition);
    }

    private Path path(final PiecePosition destination) {
        return Path.of(piecePosition, destination);
    }

    public Color color() {
        return color;
    }

    public PiecePosition piecePosition() {
        return piecePosition;
    }

    public PieceMovement pieceMovement() {
        return pieceMovement;
    }
}
