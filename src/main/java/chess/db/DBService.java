package chess.db;

import chess.db.dao.GameDao;
import chess.db.dao.PieceDao;

public class DBService {
    private final GameDao gameDao = GameDao.getInstance();
    private final PieceDao pieceDao = PieceDao.getInstance();

}
