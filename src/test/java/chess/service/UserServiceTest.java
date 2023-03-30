package chess.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.user.User;
import chess.repository.UserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class UserServiceTest {

    private UserDao mockUserDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockUserDao = new UserDaoStub();
        userService = new UserService(mockUserDao);
    }

    @Test
    void 이미_등록된_이름을_입력받아_회원가입을_진행하는_경우_예외를_던진다() {
        // given
        final String name = "herb";
        userService.save(name);

        // expect
        assertThatThrownBy(() -> userService.save(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 등록된 이름입니다.");
    }

    @Test
    void 사용자의_이름을_받아_사용자를_저장한다() {
        // given
        final String name = "herb";

        // when
        userService.save(name);

        // then
        final User result = userService.findByName(name);
        assertThat(result.getName()).isEqualTo("herb");
    }

    @Test
    void 존재하지_않는_사용자를_조회하는_경우_예외를_던진다() {
        // given
        final String name = "herb";

        // expect
        assertThatThrownBy(() -> userService.findByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름을 가진 유저가 없습니다.");
    }

    @Test
    void 사용자의_이름을_받아_사용자를_조회한다() {
        // given
        final String name = "herb";
        userService.save(name);

        // when
        final User result = userService.findByName(name);

        // then
        assertAll(
                () -> assertThat(result.getId()).isPositive(),
                () -> assertThat(result.getName()).isEqualTo("herb")
        );
    }

    private class UserDaoStub implements UserDao {
        private final List<User> users = new ArrayList<>();
        private int index = 0;

        @Override
        public void save(final String name) {
            users.add(new User(++index, name));
        }

        @Override
        public Optional<User> findByName(final String name) {
            return users.stream()
                    .filter(user -> user.getName().equals(name))
                    .findFirst();
        }
    }
}
