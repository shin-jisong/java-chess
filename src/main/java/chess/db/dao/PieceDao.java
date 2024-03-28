package chess.db.dao;

import chess.db.DBConnector;
import chess.db.dto.PieceDto;
import chess.domain.board.game.Turn;
import chess.domain.location.Location;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {
    private static final String TABLE = "piece";
    private static PieceDao instance = null;

    private PieceDao() {
    }

    public static PieceDao getInstance() {
        if (instance == null) {
            instance = new PieceDao();
        }
        return instance;
    }

    Connection getConnection() {
        return DBConnector.getInstance().getConnection();
    }

    public void addPiece(int gameId, PieceDto pieceDto) {
        final String query = String.format("INSERT INTO %s VALUES(?, ?, ?, ?, ?)", TABLE);
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, pieceDto.pieceType());
            preparedStatement.setString(3, pieceDto.color());
            preparedStatement.setString(4, pieceDto.location());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("Error add piece", e);
        }
    }

    public List<PieceDto> findAllPiecesByGameId(int gameId) {
        final String query = String.format("SELECT * FROM %s WHERE `game_id` = ?", TABLE);
        try (final Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<PieceDto> pieces = new ArrayList<>();
            while (resultSet.next()) {
                String pieceType = resultSet.getString("piece_type");
                String color = resultSet.getString("color");
                String location = resultSet.getString("location");
                pieces.add(new PieceDto(pieceType, color, location));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding pieces", e);
        }
    }
}
