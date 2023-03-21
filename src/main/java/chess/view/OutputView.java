package chess.view;

import chess.view.dto.ChessBoardDto;

public class OutputView {

    public void printStartMessage() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printChessBoard(final ChessBoardDto chessBoardDto) {
        for (String line : chessBoardDto.getLines()) {
            System.out.println(line);
        }
    }

    public void printErrorMessage(final String message) {
        System.out.println("[ERROR] " + message);
    }
}
