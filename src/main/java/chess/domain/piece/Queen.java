package chess.domain.piece;

import chess.domain.board.File;
import chess.domain.board.Position;
import chess.domain.board.Rank;

public final class Queen extends Piece {

    public Queen(final Team team) {
        super(team, PieceType.QUEEN);
    }

    @Override
    public boolean isMovable(final Position from, final Position to) {

        final int fileInterval = File.calculateInterval(from.getFile(), to.getFile());
        final int rankInterval = Rank.calculateInterval(from.getRank(), to.getRank());

        return (from.getRank() == to.getRank()) || (from.getFile() == to.getFile()) || (fileInterval == rankInterval);
    }
}
