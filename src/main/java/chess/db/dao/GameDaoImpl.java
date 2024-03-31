package chess.db.dao;

import chess.db.ChessDBConnector;
import chess.db.GameId;
import chess.domain.game.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDaoImpl implements GameDao {
    private static final String TABLE = "game";

    private final ChessDBConnector connector;

    public GameDaoImpl(ChessDBConnector chessDbConnector) {
        this.connector = chessDbConnector;
    }

    @Override
    public GameId addGame(Turn turn) {
        final String query = String.format("INSERT INTO %s(turn) VALUE(?);", TABLE);
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int gameId = resultSet.getInt(1);
                return new GameId(gameId);
            }
            throw new RuntimeException("게임을 저장하는 것을 실패하였습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameId findLatestGameId() {
        final String query = String.format("SELECT game_id FROM %s ORDER BY `game_id` DESC LIMIT 1", TABLE);
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int gameId = resultSet.getInt(1);
                return new GameId(gameId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Turn findTurn(GameId gameId) {
        final String query = String.format("SELECT turn FROM %s WHERE `game_id` = ?", TABLE);
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId.getValue());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return Turn.valueOf(turn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteGame(GameId gameId) {
        final String query = String.format("DELETE FROM %s WHERE `game_id` = ?", TABLE);
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, gameId.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
