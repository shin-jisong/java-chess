package chess.db;

import chess.db.dao.GameDao;
import chess.db.dao.PieceDao;

public class TestDBService extends ChessDBService {
    public TestDBService(FakeGameDao gameDao, FakePieceDao pieceDao) {
        super(gameDao, pieceDao);
    }
}
