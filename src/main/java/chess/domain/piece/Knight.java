package chess.domain.piece;

import chess.domain.board.position.File;
import chess.domain.board.position.Position;
import chess.domain.board.position.Rank;

public final class Knight extends Piece {

    public static final int INTERVAL_LIMIT = 2;

    public Knight(final Team team) {
        super(team, PieceType.KNIGHT);
    }

    @Override
    public boolean isMovable(final Position from, final Position to) {

        final int fileInterval = File.calculateInterval(from.getFile(), to.getFile());
        final int rankInterval = Rank.calculateInterval(from.getRank(), to.getRank());

        return (fileInterval <= INTERVAL_LIMIT) && (rankInterval <= INTERVAL_LIMIT) && ((fileInterval + rankInterval) == 3);
    }
}
