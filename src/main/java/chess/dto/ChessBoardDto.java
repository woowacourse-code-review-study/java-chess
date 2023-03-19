package chess.dto;

import chess.board.ChessBoard;
import chess.board.File;
import chess.board.Position;
import chess.board.Rank;
import chess.piece.Piece;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessBoardDto {

    private final List<PieceDto> pieceDtos;

    public static ChessBoardDto from(final ChessBoard board) {
        final List<PieceDto> pieces = pieceToView(board.getPiecePosition());
        return new ChessBoardDto(pieces);
    }

    private ChessBoardDto(final List<PieceDto> pieceDtos) {
        this.pieceDtos = pieceDtos;
    }

    private static List<PieceDto> pieceToView(final Map<Position, Piece> piecePosition) {
        List<Rank> ranks = getReverseRank();
        return ranks.stream()
                .flatMap(rank -> Arrays.stream(File.values())
                        .map(file -> {
                            final Piece piece = piecePosition.get(new Position(file, rank));
                            return new PieceDto(piece);
                        }))
                .collect(Collectors.toList());
    }

    private static List<Rank> getReverseRank() {
        List<Rank> ranks = Arrays.stream(Rank.values())
                .collect(Collectors.toList());
        Collections.reverse(ranks);
        return ranks;
    }

    public List<PieceDto> getPieceDtos() {
        return pieceDtos;
    }
}
