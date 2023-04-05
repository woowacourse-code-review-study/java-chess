package chess.domain.state;

import static chess.domain.piece.ColorCompareResult.DIFFERENT_COLOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chess.domain.piece.state.KingState;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings({"NonAsciiCharacters"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KingStateTest {

    private static final KingState kingState = KingState.getInstance();

    @ParameterizedTest
    @CsvSource(value = {"1, 1", "1, 0", "0, -1", "-1, 1", "0, 1"})
    void 킹은_대각선_혹은_직선으로_1칸_움직일_수_있다(int xChange, int yChange) {
        //expect
        assertTrue(() -> kingState.canMove(xChange, yChange, DIFFERENT_COLOR));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 3", "0, 2", "-2, -2", "2, 0", "-1, -2"})
    void 킹은_대각선이나_직선으로_1칸만_이동_가능하다(int xChange, int yChange) {
        //expect
        assertFalse(() -> kingState.canMove(xChange, yChange, DIFFERENT_COLOR));
    }

    @Test
    void 킹의_다음_상태는_처음과_같다() {
        //expect
        assertSame(kingState, kingState.getNextState());
    }

    @Test
    void 킹의_점수는_0점이다() {
        //expect
        assertThat(kingState.getScore()).isEqualTo(0, withPrecision(0.0001));
    }

    @Test
    void 킹은_킹이다() {
        //expect
        assertTrue(kingState.isKing());
    }

    @Test
    void 제자리로_움직이면_움직일_수_없다() {
        //expect
        assertFalse(kingState.canMove(0, 0, DIFFERENT_COLOR));
    }
}
