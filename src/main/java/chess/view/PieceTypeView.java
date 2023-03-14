package chess.view;

import chess.domain.PieceType;
import java.util.Arrays;

public enum PieceTypeView {
    BISHOP(PieceType.BISHOP, "b", "B"),
    KING(PieceType.KING, "k", "K"),
    KNIGHT(PieceType.KNIGHT, "n", "N"),
    PAWN(PieceType.PAWN, "p", "P"),
    QUEEN(PieceType.QUEEN, "q", "Q"),
    ROOK(PieceType.ROOK, "r", "R"),
    EMPTY(PieceType.EMPTY, ".", "."),
    ;

    private final PieceType pieceType;
    private final String whiteTeamView;
    private final String blackTeamView;

    PieceTypeView(PieceType pieceType, String whiteTeamView, String blackTeamView) {
        this.pieceType = pieceType;
        this.whiteTeamView = whiteTeamView;
        this.blackTeamView = blackTeamView;
    }

    public static PieceTypeView of(PieceType pieceType) {
        return Arrays.stream(values())
                .filter(x -> x.getPieceType() == pieceType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }

    private PieceType getPieceType() {
        return pieceType;
    }

    public String getWhiteTeamView() {
        return whiteTeamView;
    }

    public String getBlackTeamView() {
        return blackTeamView;
    }
}
