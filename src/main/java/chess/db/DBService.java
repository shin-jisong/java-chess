package chess.db;

public class DBService {
    private final GameDao gameDao = GameDao.getInstance();
    private final PieceDao pieceDao = PieceDao.getInstance();
}
