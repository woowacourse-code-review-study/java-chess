package chess.domain.game;

import chess.domain.game.exception.ChessGameException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum Rank {
    ONE('1', 1),
    TWO('2', 2),
    THREE('3', 3),
    FOUR('4', 4),
    FIVE('5', 5),
    SIX('6', 6),
    SEVEN('7', 7),
    EIGHT('8', 8);

    private static final int SKIP_FIRST = 1;

    private final char rankName;
    private final int order;

    Rank(char rankName, int order) {
        this.rankName = rankName;
        this.order = order;
    }

    public static Rank from(char rankName) {
        return Arrays.stream(Rank.values())
                .filter(it -> it.rankName == rankName)
                .findAny()
                .orElseThrow(() -> new ChessGameException("존재하지 않는 rank 입니다"));
    }

    public static Rank from(int rankOrder) {
        return Arrays.stream(Rank.values())
                .filter(it -> it.order == rankOrder)
                .findAny()
                .orElseThrow(() -> new ChessGameException("존재하지 않는 rank 입니다"));
    }

    public int getDifference(Rank other) {
        return other.order - order;
    }

    public List<Rank> createPath(Rank other) {
        List<Rank> ranks = IntStream.range(Math.min(order, other.order), Math.max(order, other.order))
                .skip(SKIP_FIRST)
                .mapToObj(Rank::from)
                .collect(Collectors.toList());
        if (order > other.order) {
            Collections.reverse(ranks);
        }
        return ranks;
    }
}
