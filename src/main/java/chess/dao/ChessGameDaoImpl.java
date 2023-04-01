package chess.dao;

import chess.dao.dto.ChessGameDto;
import chess.domain.game.ChessGame;
import chess.domain.game.state.ExecuteState;
import chess.domain.game.state.GameState;
import chess.domain.piece.Color;
import chess.service.ExecuteStateSerialization;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChessGameDaoImpl implements ChessGameDao {

    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public void save(final ChessGame chessGame) {
        final GameState gameState = chessGame.getGameState();
        final Color turn = gameState.getTurnColor();
        final ExecuteState executeState = gameState.getExecuteState();

        final String query = "insert into chess_game(state, turn) values(?, ?)";
        final List<String> parameters = List.of(ExecuteStateSerialization.serialize(executeState), turn.name());

        jdbcTemplate.executeUpdate(query, parameters);
    }

    @Override
    public Optional<ChessGameDto> findById(final Long id) {
        final String query = "select * from chess_game where id = ?";
        final List<String> parameters = List.of(String.valueOf(id));

        return jdbcTemplate.executeQuery(query, resultSet -> {
            if (resultSet.next()) {
                final ChessGameDto chessGameDto = ChessGameDto.of(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                return Optional.of(chessGameDto);
            }
            return Optional.empty();
        }, parameters);
    }

    @Override
    public Optional<ChessGameDto> findLatest() {
        final String query = "select * from chess_game order by id desc limit 1";

        return jdbcTemplate.executeQuery(query, resultSet -> {
            if (resultSet.next()) {
                final ChessGameDto chessGameDto = ChessGameDto.of(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                return Optional.of(chessGameDto);
            }
            return Optional.empty();
        }, Collections.emptyList());
    }

    @Override
    public List<ChessGameDto> findAll() {
        final String query = "select * from chess_game";

        return jdbcTemplate.executeQuery(query, resultSet -> {
            final List<ChessGameDto> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(ChessGameDto.of(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
            return result;
        }, Collections.emptyList());
    }

    @Override
    public void update(final ChessGame chessGame) {
        final GameState gameState = chessGame.getGameState();
        final Color turnColor = gameState.getTurnColor();
        final ExecuteState executeState = gameState.getExecuteState();

        final String query = "update chess_game set state = ?, turn = ? where id = ?";
        final List<String> parameters = List.of(
                ExecuteStateSerialization.serialize(executeState),
                turnColor.name(),
                String.valueOf(chessGame.getId())
        );

        jdbcTemplate.executeUpdate(query, parameters);
    }

    @Override
    public void delete(final Long id) {
        final String query = "delete from chess_game where id = ?";
        final List<String> parameters = List.of(String.valueOf(id));

        jdbcTemplate.executeUpdate(query, parameters);
    }
}
