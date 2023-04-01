package chess.controller.command.execute;

import chess.domain.board.Square;
import chess.domain.game.ChessGame;
import chess.service.ChessGameService;
import chess.view.OutputView;
import chess.view.dto.ChessBoardDto;

public class MoveCommand implements ExecuteCommand {

    private final Square source;
    private final Square destination;

    public MoveCommand(final String input) {
        final String[] commands = input.split(" ");
        this.source = Square.from(commands[1]);
        this.destination = Square.from(commands[2]);
    }

    @Override
    public void execute(
            final ChessGame chessGame,
            final ChessGameService chessGameService,
            final OutputView outputView
    ) {
        chessGame.move(source, destination);
        chessGameService.move(chessGame, source, destination);
        outputView.printChessBoard(ChessBoardDto.from(chessGame));
    }
}
