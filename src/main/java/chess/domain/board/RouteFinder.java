package chess.domain.board;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RouteFinder {

    private static final int END_OF_SIZE = 1;

    public static List<Position> findRoute(final Position from, final Position to) {
        if (from.equals(to)) {
            return Collections.emptyList();
        }

        if (from.getRank() == to.getRank()) {
            return calculateSameRank(from, to);
        }

        if (from.getFile() == to.getFile()) {
            return calculateSameFile(from, to);
        }

        return calculateDiagonal(from, to);
    }

    private static List<Position> calculateSameFile(final Position from, final Position to) {
        List<Position> collect = Rank.sliceBetween(from.getRank(), to.getRank()).stream()
                .map(rank -> Position.of(from.getFile(), rank))
                .collect(Collectors.toList());

        return collect.subList(END_OF_SIZE, collect.size() - END_OF_SIZE);
    }

    private static List<Position> calculateSameRank(final Position from, final Position to) {
        List<Position> collect = File.sliceBetween(from.getFile(), to.getFile()).stream()
                .map(file -> Position.of(file, from.getRank()))
                .collect(Collectors.toList());

        return collect.subList(END_OF_SIZE, collect.size() - END_OF_SIZE);
    }

    private static List<Position> calculateDiagonal(final Position from, final Position to) {
        List<File> files = File.sliceBetween(from.getFile(), to.getFile());
        List<Rank> ranks = Rank.sliceBetween(from.getRank(), to.getRank());

        if (files.size() != ranks.size()) {
            return Collections.emptyList();
        }

        List<File> cutFiles = files.subList(END_OF_SIZE, files.size() - END_OF_SIZE);
        List<Rank> cutRanks = ranks.subList(END_OF_SIZE, ranks.size() - END_OF_SIZE);

        if (from.getFile().getIndex() > to.getFile().getIndex()) {
            Collections.reverse(cutFiles);
        }

        if (from.getRank().getIndex() > to.getRank().getIndex()) {
            Collections.reverse(cutRanks);
        }

        return cutFiles.stream().flatMap(file -> cutRanks.stream()
                        .map(rank -> Position.of(file, rank)))
                .collect(Collectors.toList());
    }
}
