package chess.db.dao;

import chess.db.DBConnector;
import chess.domain.board.game.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDao {
    private static final String TABLE = "game";
    private static GameDao instance = null;

    private GameDao() {
    }

    public static GameDao getInstance() {
        if (instance == null) {
            instance = new GameDao();
        }
        return instance;
    }

    Connection getConnection() {
        return DBConnector.getInstance().getConnection();
    }

    public int addGame(Turn turn) {
        final String query = String.format("INSERT INTO %s(turn) VALUE(?);", TABLE);
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public int findLatestGameId() {
        final String query = String.format("SELECT game_id FROM %s ORDER BY `game_id` DESC LIMIT 1", TABLE);
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public Turn findTurn(int gameId) {
        final String query = String.format("SELECT turn FROM %s WHERE `game_id` = ?", TABLE);
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
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
}
