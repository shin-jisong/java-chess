package chess.db;

import java.sql.Connection;
import java.util.function.Supplier;

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
}
