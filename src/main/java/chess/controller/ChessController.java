package chess.controller;

import chess.board.ChessBoard;
import chess.board.Position;
import chess.dto.ChessBoardDto;
import chess.game.ChessGame;
import chess.game.Command;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.PositionConvertor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGame chessGame;

    public ChessController(final InputView inputView, final OutputView outputView, final ChessGame chessGame) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessGame = chessGame;
    }

    public void run() {
        outputView.printInitGame();
        processGame();
    }

    private void processGame() {
        final Command firstCommand = readValidateInput(this::readCommand);
        chessGame.receiveCommand(firstCommand);

        while (!chessGame.isEnd()) {
            final List<String> movePositions = new ArrayList<>();
            renderChessBoard();

            final Command command = readMoveCommand(movePositions);
            if (command == Command.END) {
                break;
            }
            final Position from = PositionConvertor.convert(movePositions.get(0));
            final Position to = PositionConvertor.convert(movePositions.get(1));
            chessGame.movePiece(from, to);
        }
    }

    private Command readMoveCommand(final List<String> movePositions) {
        return readValidateInput(() -> {
            final List<String> commands = inputView.readGameCommand();
            final Command result = Command.from(commands.get(0));
            if (result != Command.MOVE) {
                return result;
            }
            movePositions.add(commands.get(1));
            movePositions.add(commands.get(2));
            return result;
        });
    }

    private Command readCommand() {
        final List<String> commands = inputView.readGameCommand();
        return Command.from(commands.get(0));
    }

    private void renderChessBoard() {
        final ChessBoard chessBoard = chessGame.getChessBoard();
        final ChessBoardDto chessBoardDto = ChessBoardDto.from(chessBoard);
        outputView.printChessBoard(chessBoardDto);
    }

    private <T> T readValidateInput(final Supplier<T> function) {
        Optional<T> input;
        do {
            input = repeatByEx(function);
        } while (input.isEmpty());
        return input.get();
    }

    private <T> Optional<T> repeatByEx(final Supplier<T> function) {
        try {
            return Optional.of(function.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
