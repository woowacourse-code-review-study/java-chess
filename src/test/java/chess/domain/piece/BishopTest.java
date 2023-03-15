package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class BishopTest {

    @Test
    void 비숍이_정상적으로_생성된다() {
        // given
        final Bishop bishop = new Bishop(Color.WHITE);

        // expect
        assertThat(bishop.type()).isEqualTo(PieceType.PAWN);
    }
}
