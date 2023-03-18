package chess.domain.board;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum RankCoordinate {
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    ;

    private final int rowNumber;

    RankCoordinate(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public static RankCoordinate findBy(int rowNumber) {
        return Arrays.stream(values())
                .filter(it -> it.rowNumber == rowNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 행 번호를 입력해주세요."));
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public boolean isWhiteRank() {
        return this == RankCoordinate.ONE || this == RankCoordinate.TWO;
    }

    public boolean isBlackRank() {
        return this == RankCoordinate.SEVEN || this == RankCoordinate.EIGHT;
    }

    public boolean isSideRank() {
        return this == RankCoordinate.ONE || this == RankCoordinate.EIGHT;
    }

    public boolean isPawnRank() {
        return this == RankCoordinate.TWO || this == RankCoordinate.SEVEN;
    }

    public List<RankCoordinate> betweenRanks(RankCoordinate other) {
        List<RankCoordinate> result = IntStream.range(min(rowNumber, other.rowNumber), max(rowNumber, other.rowNumber))
                .skip(1)
                .mapToObj(RankCoordinate::findBy)
                .collect(Collectors.toList());
        if (rowNumber > other.rowNumber) {
            Collections.reverse(result);
        }
        return result;
    }


    public int calculateGap(final RankCoordinate other) {
        return rowNumber - other.rowNumber;
    }
}
