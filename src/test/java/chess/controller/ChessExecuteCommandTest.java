package chess.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChessExecuteCommandTest {

    @ParameterizedTest
    @CsvSource(value = {"START:start", "END:end"}, delimiter = ':')
    void 정상적인_게임_실행명령을_전달받으면_실행상태를_반환한다(final ChessExecuteCommand expected, final String input) {
        final ChessExecuteCommand chessExecuteCommand = ChessExecuteCommand.from(input);

        assertThat(chessExecuteCommand).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"merry"})
    void 정상적이지_않은_게임_실행명령을_전달받으면_예외를_던진다(final String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ChessExecuteCommand.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("start나 end 중 입력하세요.");
    }
}
