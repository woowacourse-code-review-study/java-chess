package chess.controller.game;

import static chess.controller.game.GameCommand.EMPTY;
import static chess.controller.game.GameCommand.END;
import static chess.controller.game.GameCommand.MOVE;
import static chess.controller.game.GameCommand.MOVE_SOURCE_INDEX;
import static chess.controller.game.GameCommand.MOVE_TARGET_INDEX;
import static chess.controller.game.GameCommand.STATUS;

import chess.controller.Action;
import chess.controller.CommandMapper;
import chess.controller.SubController;
import chess.controller.session.RoomSession;
import chess.controller.session.UserSession;
import chess.dto.MoveDto;
import chess.service.GameService;
import chess.view.input.InputView;
import chess.view.output.GameOutputView;
import java.util.List;
import java.util.Map;

public class GameController implements SubController {
    private final InputView inputView;
    private final GameOutputView outputView;
    private final GameService gameService;
    private final CommandMapper<GameCommand, Action> commandMapper;

    public GameController(
            final InputView inputView,
            final GameOutputView outputView,
            final GameService gameService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
        this.commandMapper = new CommandMapper<>(mappingCommand());
    }

    private Map<GameCommand, Action> mappingCommand() {
        return Map.of(
                MOVE, this::move,
                STATUS, ignore -> status(),
                END, Action.EMPTY
        );
    }

    @Override
    public void run() {
        outputView.printGameStart();
        gameService.initialize(RoomSession.getId());
        outputView.printBoard(gameService.getResult(RoomSession.getId()));
        GameCommand command = EMPTY;
        while (command != END) {
            command = play();
            command = checkGameOver(command);
        }
        outputView.printGameEnd();
        gameService.removeBoard(RoomSession.getId());
    }

    private GameCommand play() {
        try {
            outputView.printCommands(UserSession.getName(), RoomSession.getName());
            final List<String> commands = inputView.readCommands();
            final GameCommand command = GameCommand.from(commands);
            command.validateCommandsSize(commands);
            final Action action = commandMapper.getValue(command);
            action.execute(commands);
            return command;
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printException(e.getMessage());
            return EMPTY;
        }
    }

    private void move(final List<String> commands) {
        final MoveDto moveDto = new MoveDto(commands.get(MOVE_SOURCE_INDEX), commands.get(MOVE_TARGET_INDEX));
        gameService.move(moveDto, RoomSession.getId());
        outputView.printBoard(gameService.getResult(RoomSession.getId()));
    }

    private void status() {
        outputView.printStatus(gameService.getResult(RoomSession.getId()));
    }

    private GameCommand checkGameOver(final GameCommand command) {
        if (gameService.isGameOver(RoomSession.getId())) {
            outputView.printStatus(gameService.getResult(RoomSession.getId()));
            return END;
        }
        return command;
    }
}
