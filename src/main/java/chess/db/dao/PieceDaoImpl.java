package chess.db.dao;

import chess.db.ChessDBConnector;
import chess.db.GameId;
import chess.db.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDaoImpl implements PieceDao {
    private static final String TABLE = "piece";
    private final ChessDBConnector connector;

    public PieceDaoImpl(ChessDBConnector connector) {
        this.connector = connector;
    }

    @Override
    public void addPiece(GameId gameId, PieceDto pieceDto) {
        final String query = String.format("INSERT INTO %s VALUES(?, ?, ?, ?, ?)", TABLE);
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId.getValue());
            preparedStatement.setString(2, pieceDto.pieceType());
            preparedStatement.setString(3, pieceDto.color());
            preparedStatement.setString(4, pieceDto.column());
            preparedStatement.setInt(5, pieceDto.row());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("Error add piece", e);
        }
    }

    @Override
    public List<PieceDto> findAllPiecesByGameId(GameId gameId) {
        final String query = String.format("SELECT * FROM %s WHERE `game_id` = ?", TABLE);
        try (final Connection connection = connector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, gameId.getValue());
            ResultSet resultSet = statement.executeQuery();

            List<PieceDto> pieces = new ArrayList<>();
            while (resultSet.next()) {
                String pieceType = resultSet.getString("piece_type");
                String color = resultSet.getString("color");
                String column = resultSet.getString("column");
                int row = resultSet.getInt("row");
                pieces.add(new PieceDto(pieceType, color, column, row));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding pieces", e);
        }
    }

    @Override
    public void deletePieces(GameId gameId) {
        final String query = String.format("DELETE FROM %s WHERE `game_id` = ?", TABLE);
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, gameId.getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
