package chess.domain.piece;

import chess.domain.piece.position.PiecePosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Piece 은")
class PieceTest {

    @Test
    void 위치와_색상을_가지고_생성된다() {
        // when & then
        assertDoesNotThrow(() -> new Piece(Staunton.KING, PiecePosition.of(1, 'a'), Color.WHITE) {
        });
    }
}
