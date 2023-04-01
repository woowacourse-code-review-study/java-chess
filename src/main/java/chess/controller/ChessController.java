package chess.controller;

import chess.controller.command.chess.ChessGameCommand;
import chess.controller.command.execute.ExecuteCommand;
import chess.domain.game.ChessGame;
import chess.service.ChessGameService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    private final ChessGameService chessGameService;

    public ChessController(final ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    public void run() {
        outputView.printStartMessage(chessGameService.loadAllChessGameId());
        final ChessGame chessGame = readChessGame();
        while (chessGame.isRunning()) {
            playChessGame(chessGame);
            checkKing(chessGame);
        }
        printGameOver(chessGame);
    }

    private ChessGame readChessGame() {
        while (true) {
            try {
                final ChessGameCommand chessGameCommand = inputView.readChessGameCommand();
                return chessGameCommand.execute(chessGameService, outputView);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void playChessGame(final ChessGame chessGame) {
        try {
            final ExecuteCommand executeCommand = inputView.readExecuteCommand();
            executeCommand.execute(chessGame, chessGameService, outputView);
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void checkKing(final ChessGame chessGame) {
        if (chessGame.isKingDied()) {
            chessGame.done();
            chessGameService.updateChessGameStateAndTurn(chessGame);
        }
    }

    private void printGameOver(final ChessGame chessGame) {
        if (chessGame.isEnd()) {
            outputView.printEndMessage();
        }
        if (chessGame.isDone()) {
            outputView.printDoneMessage();
        }
    }
}
