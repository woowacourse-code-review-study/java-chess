package chess.controller.main;

import static chess.controller.main.MainCommand.EMPTY;
import static chess.controller.main.MainCommand.END;

import chess.controller.CommandMapper;
import chess.controller.Controller;
import chess.controller.session.RoomSession;
import chess.controller.session.UserSession;
import chess.view.input.MainInputView;
import chess.view.output.MainOutputView;

public class MainController {
    private final MainInputView inputView;
    private final MainOutputView outputView;
    private final CommandMapper<MainCommand, Controller> commandMapper;

    public MainController(
            final MainInputView inputView,
            final MainOutputView outputView,
            final CommandMapper<MainCommand, Controller> commandMapper
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.commandMapper = commandMapper;
    }
    
    public void run() {
        MainCommand command = EMPTY;
        while (command != END) {
            command = readCommand();
        }
        UserSession.remove();
        RoomSession.remove();
    }

    private MainCommand readCommand() {
        try {
            final String command = inputView.readCommand(UserSession.getName(), RoomSession.getName());
            final MainCommand mainCommand = MainCommand.from(command);
            final Controller controller = commandMapper.getValue(mainCommand);
            controller.run();
            return mainCommand;
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printException(e.getMessage());
            return EMPTY;
        }
    }
}
