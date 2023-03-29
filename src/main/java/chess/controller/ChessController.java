package chess.controller;

import chess.dao.ChessGameDao;
import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.game.Command;
import chess.domain.game.GameStatus;
import chess.domain.game.Turn;
import chess.domain.piece.Team;
import chess.dto.ChessBoardDto;
import chess.view.InputView;
import chess.view.OutputView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static chess.domain.game.Command.END;
import static chess.domain.game.Command.MOVE;
import static chess.domain.game.Command.START;
import static chess.domain.game.Command.STATUS;
import static chess.domain.game.GameStatus.GAME_OVER;
import static chess.domain.game.GameStatus.IDLE;
import static chess.domain.game.GameStatus.PLAYING;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGameDao chessGameDao;

    private final Map<Command, GameAction> commandMapper = Map.of(
            START, this::start,
            MOVE, this::move,
            END, this::end,
            STATUS, this::status
    );

    public ChessController(final InputView inputView, final OutputView outputView, final ChessGameDao chessGameDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessGameDao = chessGameDao;
    }

    public void run() {
        outputView.printInitGame();
        processGame();
    }

    private void processGame() {
        ChessGame chessGame = loadChessGame();
        initCommend(chessGame);
        play(chessGame);
        endGame(chessGame);
    }

    private ChessGame loadChessGame() {
        ChessGame chessGame = chessGameDao.select();
        if (chessGame == null) {
            chessGame = new ChessGame(ChessBoard.createBoard(), new Turn(), IDLE);
            chessGameDao.save(chessGame);
        }
        return chessGame;
    }

    private void initCommend(final ChessGame chessGame) {
        if (chessGame.isSameStatus(PLAYING)) {
            return;
        }
        Command command = readValidateInput(this::readCommand);
        chessGame.receiveCommand(command);
        chessGameDao.update(chessGame);
    }

    private Command readCommand() {
        final List<String> commands = inputView.readGameCommand();
        Command command = Command.from(commands.get(0));
        GameAction gameAction = commandMapper.get(command);
        gameAction.execute(commands);

        return command;
    }

    private void play(final ChessGame chessGame) {
        while (!isEnd(chessGame)) {
            renderCurrentTeam();
            renderChessBoard();
            Command command = readValidateInput(this::readCommand);
            chessGame.receiveCommand(command);
        }
    }

    private void renderCurrentTeam() {
        final ChessGame chessGame = chessGameDao.select();
        outputView.printTurn(chessGame.getCurrentTeam());
    }

    private void renderChessBoard() {
        final ChessGame chessGame = chessGameDao.select();
        final ChessBoard chessBoard = chessGame.getChessBoard();
        final ChessBoardDto chessBoardDto = ChessBoardDto.from(chessBoard);
        outputView.printChessBoard(chessBoardDto);
    }

    private void endGame(ChessGame chessGame) {
        renderChessBoard();
        printStatus(chessGame);
        if (chessGame.isSameStatus(GAME_OVER)) {
            System.out.println("게임 종료 " + chessGame.getCurrentTeam() + "의 패배!");
        }
    }

    private void start(final List<String> commands) {
        final ChessGame chessGame = chessGameDao.select();
        Command.validateCommandSize(commands.size(), START);
        if (!isEnd(chessGame)) {
            throw new IllegalArgumentException("이미 체스 게임이 시작되었습니다.");
        }
        chessGameDao.update(chessGame);
    }

    private void move(final List<String> commands) {
        final ChessGame chessGame = chessGameDao.select();
        Command.validateCommandSize(commands.size(), MOVE);
        if (isEnd(chessGame)) {
            throw new IllegalArgumentException("체스게임을 시작하려면 START를 입력하세요.");
        }

        PositionConvertor convertor = new PositionConvertor(commands);
        chessGame.movePiece(convertor.getFromPosition(), convertor.getToPosition());

        chessGameDao.update(chessGame);
    }

    private void status(final List<String> commands) {
        final ChessGame chessGame = chessGameDao.select();
        Command.validateCommandSize(commands.size(), STATUS);
        if (isEnd(chessGame)) {
            throw new IllegalArgumentException("체스게임을 시작하려면 START를 입력하세요.");
        }
        printStatus(chessGame);
    }

    private void printStatus(final ChessGame chessGame) {
        BigDecimal blackScore = chessGame.calculateScore(Team.BLACK);
        outputView.printStatus(Team.BLACK, blackScore.longValue());

        BigDecimal whiteScore = chessGame.calculateScore(Team.WHITE);
        outputView.printStatus(Team.WHITE, whiteScore.longValue());
    }

    private void end(final List<String> commands) {
        final ChessGame chessGame = chessGameDao.select();
        Command.validateCommandSize(commands.size(), END);
        if (isEnd(chessGame)) {
            throw new IllegalArgumentException("체스게임을 시작하려면 START를 입력하세요.");
        }
        chessGameDao.update(chessGame);
    }

    private boolean isEnd(final ChessGame chessGame) {
        GameStatus status = chessGame.getStatus();
        return status == IDLE || status == GameStatus.GAME_OVER;
    }

    private <T> T readValidateInput(final Supplier<T> function) {
        Optional<T> input = repeatByEx(function);
        while (input.isEmpty()) {
            input = repeatByEx(function);
        }
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
