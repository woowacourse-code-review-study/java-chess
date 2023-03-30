package chess.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.db.JdbcTemplate;
import chess.db.TestConnectionPool;
import chess.domain.user.User;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class UserJdbcDaoTest {
    private static TestConnectionPool connectionPool;
    private static JdbcTemplate jdbcTemplate;
    private static UserDao userDao;

    @BeforeAll
    static void beforeAll() {
        connectionPool = new TestConnectionPool();
        jdbcTemplate = new JdbcTemplate(connectionPool);
        userDao = new UserJdbcDao(jdbcTemplate);
        final String query = "CREATE TABLE IF NOT EXISTS user ("
                + "  id int PRIMARY KEY AUTO_INCREMENT,"
                + "  name varchar(255)"
                + ");";
        jdbcTemplate.executeUpdate(query);
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.executeUpdate("DELETE FROM user");
    }

    @AfterAll
    static void afterAll() {
        connectionPool.closeConnection();
    }

    @Test
    void 사용자의_이름을_받아_사용자를_저장한다() {
        // given
        final String name = "herb";

        // when
        userDao.save(name);

        // then
        final Optional<User> user = userDao.findByName(name);
        final User result = user.get();
        assertThat(result.getName()).isEqualTo("herb");
    }

    @Test
    void 사용자의_이름을_받아_사용자를_조회한다() {
        // given
        final String name = "herb";
        userDao.save(name);

        // when
        final Optional<User> user = userDao.findByName(name);

        // then
        final User result = user.get();
        assertAll(
                () -> assertThat(result.getId()).isPositive(),
                () -> assertThat(result.getName()).isEqualTo("herb")
        );
    }
}
