package chess.domain.game.state;

import chess.domain.board.Square;
import chess.domain.game.ChessGame;
import chess.domain.piece.Color;

public class DoneState implements ExecuteState {

    public static final DoneState CACHE = new DoneState();

    @Override
    public double calculateScoreOfColor(final Color color, final ChessGame chessGame) {
        throw new IllegalStateException("왕이 잡힌 상태에서는 점수를 계산할 수 없습니다.");
    }

    @Override
    public void move(final Square source, final Square destination, final ChessGame chessGame) {
        throw new IllegalStateException("왕이 잡힌 상태에서는 움직일 수 없습니다.");
    }

    @Override
    public void end() {
        throw new IllegalStateException("왕이 잡힌 상태에서는 이미 종료된 상태입니다.");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
