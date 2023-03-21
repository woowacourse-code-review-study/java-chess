package chess.domain.position;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Position implements Comparable<Position> {

    private final File file;
    private final Rank rank;

    private Position(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    private Position(final int file, final int rank) {
        this.file = File.findByIndex(file);
        this.rank = Rank.findByIndex(rank);
    }

    public static Position from(String position) {
        List<String> parsedPosition = parsing(position);
        File file = File.findByLabel(parsedPosition.get(0));
        Rank rank = Rank.findByLabel(parsedPosition.get(1));
        return new Position(file, rank);
    }

    public static Position from(File file, Rank rank) {
        return new Position(file, rank);
    }

    private static List<String> parsing(final String position) {
        return Arrays.stream(position.split("")).collect(Collectors.toList());
    }

    public Direction calculateDirection(Position destination) {
        int fileGap = getFileGap(destination);
        int rankGap = getRankGap(destination);

        int gcd = getGreatestCommonDivisor(Math.max(fileGap, rankGap), Math.min(fileGap, rankGap));
        return Direction.findByUnitVector(fileGap / gcd, rankGap / gcd);
    }

    public int getGreatestCommonDivisor(int bigNum, int smallNum) {
        while (smallNum != 0) {
            int remainder = bigNum % smallNum;

            bigNum = smallNum;
            smallNum = remainder;
        }
        return Math.abs(bigNum);
    }

    public int calculateDistance(Position destination) {
        int fileGap = getFileGap(destination);
        int rankGap = getRankGap(destination);

        return fileGap * fileGap + rankGap * rankGap;
    }

    private int getFileGap(Position destination) {
        return destination.file.getIndex() - this.file.getIndex();
    }

    private int getRankGap(Position destination) {
        return destination.rank.getIndex() - this.rank.getIndex();
    }

    public Position addDirection(Direction direction) {
        int fileIndex = file.getIndex();
        int rankIndex = rank.getIndex();

        return new Position(fileIndex + direction.getX(), rankIndex + direction.getY());
    }

    public boolean isRank(final int index) {
        Rank rank = Rank.findByIndex(index);
        return this.rank == rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.file, this.rank);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return this.file == position.file && this.rank == position.rank;
    }

    @Override
    public String toString() {
        return "Position{" +
                "file=" + this.file +
                ", rank=" + this.rank +
                '}';
    }

    @Override
    public int compareTo(final Position o) {
        int thisRank = this.getRank().getIndex();
        int otherRank = o.getRank().getIndex();
        if (thisRank < otherRank) {
            return -1;
        }
        if (thisRank > otherRank) {
            return 1;
        }
        int thisFile = this.getFile().getIndex();
        int otherFile = o.getFile().getIndex();
        return Integer.compare(thisFile, otherFile);
    }

    public File getFile() {
        return this.file;
    }

    public Rank getRank() {
        return this.rank;
    }
}
