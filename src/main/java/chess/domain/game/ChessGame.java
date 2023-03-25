package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;

import java.math.BigDecimal;

import static chess.domain.game.GameStatus.END;
import static chess.domain.game.GameStatus.START;

public class ChessGame {

    private final ChessBoard chessBoard;
    private final Turn turn;
    private GameStatus gameStatus;

    public ChessGame(final ChessBoard chessBoard, final Turn turn, GameStatus status) {
        this.chessBoard = chessBoard;
        this.turn = turn;
        this.gameStatus = status;
    }

    public void receiveCommand(final Command command) {
        this.gameStatus = command.getStatus();
    }

    public void movePiece(final Position from, final Position to) {
        validateStatus();
        validateTurn(from);
        chessBoard.movePiece(from, to);
        
        if (chessBoard.isEnd()) {
            gameStatus = END;
        }
        turn.next();
    }

    private void validateTurn(final Position from) {
        Piece currentPiece = chessBoard.get(from);
        if (!turn.isCurrent(currentPiece.getTeam())) {
            throw new IllegalArgumentException(turn.getCurrentTeam() + "의 차례입니다.");
        }
    }

    private void validateStatus() {
        if (this.isEnd()) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }

    }

    public boolean isEnd() {
        return gameStatus != START;
    }

    public BigDecimal getScore(Team team) {
        return chessBoard.calculateScore(team);
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public Team getCurrentTeam() {
        return turn.getCurrentTeam();
    }
}
