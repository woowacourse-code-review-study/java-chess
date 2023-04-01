package chess.controller.command.chess;

import static chess.domain.piece.Color.WHITE;

import chess.domain.board.BoardFactory;
import chess.domain.game.ChessGame;
import chess.domain.game.state.EndState;
import chess.domain.game.state.GameState;
import chess.service.ChessGameService;
import chess.view.OutputView;

public class EndChessGameCommand implements ChessGameCommand {

    @Override
    public ChessGame execute(final ChessGameService chessGameService, final OutputView outputView) {
        return new ChessGame(BoardFactory.create(), new GameState(WHITE, EndState.CACHE));
    }
}
