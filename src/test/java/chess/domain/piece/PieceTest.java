package chess.domain.piece;

import chess.domain.piece.strategy.RookMovementStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static chess.domain.piece.position.PiecePosition.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Piece 은")
class PieceTest {

    private final Color myColor = Color.WHITE;
    private final Color enemyColor = Color.BLACK;

    @Test
    void 경유지탐색_시_같은_위치면_예외() {
        // given
        Piece myPiece = new Piece(of(1, 'a'), new RookMovementStrategy(myColor));
        // when & then
        assertThatThrownBy(() -> myPiece.waypoints(of(1, 'a'), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 경유지탐색_시_도달불가능하면_오류() {
        // given
        Piece myPiece = new Piece(of(1, 'a'), new RookMovementStrategy(myColor));

        // when & then
        assertThatThrownBy(() -> myPiece.waypoints(of(2, 'c'), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 단순_이동할_수_있다() {
        // given
        final Piece pawn = new Piece(of("b6"), new RookMovementStrategy(myColor));

        // when
        final Piece next = pawn.move(of("b5"), null);

        // then
        assertThat(next.piecePosition()).isEqualTo(of("b5"));
    }

    @Test
    void 이동할_수_없는_경로로_이동하면_오류() {
        // given
        final Piece pawn = new Piece(of("b6"), new RookMovementStrategy(myColor));
        // when & then
        assertThatThrownBy(() -> pawn.move(of("c5"), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 죽이기_위해_이동할_수_있따() {
        // given
        final Piece piece = new Piece(of("b6"), new RookMovementStrategy(myColor));
        final Piece enemy = new Piece(of("b7"), new RookMovementStrategy(enemyColor));

        // when
        final Piece next = piece.move(enemy.piecePosition(), enemy);

        // then
        assertThat(next.piecePosition()).isEqualTo(of("b7"));
        assertThat(next.pieceMovement()).isInstanceOf(RookMovementStrategy.class);
    }
}
