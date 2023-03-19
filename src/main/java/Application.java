import chess.board.ChessBoard;
import chess.controller.ChessController;
import chess.game.ChessGame;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {

    public static void main(final String[] args) {
        final ChessController chessController = new ChessController(new InputView(), new OutputView(), new ChessGame(ChessBoard.createBoard()));
        chessController.run();
    }
}
