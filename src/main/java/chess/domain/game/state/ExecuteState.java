package chess.domain.game.state;

import chess.domain.board.Square;
import chess.domain.game.ChessGame;
import chess.domain.piece.Color;

public interface ExecuteState {

    double calculateScoreOfColor(final Color color, final ChessGame chessGame);

    void move(final Square source, final Square destination, final ChessGame chessGame);

    void end();

    boolean isRunning();

    boolean isEnd();

    boolean isDone();
}
