package chess.domain.game;

import chess.domain.piece.Team;

import java.util.List;

public class Turn {

    public static final int DEFAULT_TEAM_SIZE = 2;
    public static final int START_INDEX = 0;

    private final List<Team> order;
    private int currentIndex;

    public Turn(List<Team> teams) {
        validate(teams);
        this.currentIndex = START_INDEX;
        this.order = teams;
    }

    private void validate(final List<Team> teams) {
        if (teams.size() != DEFAULT_TEAM_SIZE) {
            throw new IllegalArgumentException("팀은 2팀만 참가할 수 있습니다.");
        }

        if (teams.stream().distinct().count() != DEFAULT_TEAM_SIZE) {
            throw new IllegalArgumentException("팀은 중복될 수 없습니다.");
        }
    }

    public void next() {
        currentIndex = (currentIndex + 1) % DEFAULT_TEAM_SIZE;
    }

    public boolean isCurrent(Team team) {
        return team == order.get(currentIndex);
    }

    public Team getCurrentTeam() {
        return order.get(currentIndex);
    }
}
