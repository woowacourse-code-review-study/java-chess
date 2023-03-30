package chess.domain.board;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.Empty;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class BoardGenerator {
    private static final Map<Position, Piece> CACHE = new HashMap<>();

    static {
        CACHE.putAll(initializePiece(Color.WHITE, Rank.ONE));
        CACHE.putAll(initializePawn(Color.WHITE, Rank.TWO));
        CACHE.putAll(initializeEmptyPiece(List.of(Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX)));
        CACHE.putAll(initializePawn(Color.BLACK, Rank.SEVEN));
        CACHE.putAll(initializePiece(Color.BLACK, Rank.EIGHT));
    }

    private static Map<Position, Piece> initializePiece(final Color color, final Rank rank) {
        final List<Piece> pieces = List.of(
                Rook.from(color), Knight.from(color), Bishop.from(color), Queen.from(color),
                King.from(color), Bishop.from(color), Knight.from(color), Rook.from(color)
        );
        final List<File> files = Arrays.stream(File.values()).collect(toList());

        return IntStream.range(0, pieces.size())
                .boxed()
                .collect(toMap(index -> Position.of(files.get(index), rank), pieces::get));
    }

    private static Map<Position, Piece> initializePawn(final Color color, final Rank rank) {
        return Arrays.stream(File.values())
                .map(file -> Position.of(file, rank))
                .collect(toMap(Function.identity(), ignore -> Pawn.from(color)));
    }

    private static Map<Position, Piece> initializeEmptyPiece(final List<Rank> ranks) {
        return ranks.stream()
                .flatMap(rank -> Arrays.stream(File.values()).map(file -> Position.of(file, rank)))
                .collect(toMap(Function.identity(), ignore -> Empty.instance()));
    }

    public static Map<Position, Piece> generate() {
        return Collections.unmodifiableMap(CACHE);
    }
}
