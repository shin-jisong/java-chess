package chess.db;

public class TestDBService extends ChessDBService {
    public TestDBService(FakeGameDao gameDao, FakePieceDao pieceDao) {
        super(gameDao, pieceDao);
    }
}
