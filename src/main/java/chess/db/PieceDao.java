package chess.db;

import java.sql.Connection;

public class PieceDao {
    private static final String TABLE = "pieces";
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
}
