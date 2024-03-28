package chess.db;

import chess.db.dao.GameDao;
import chess.db.dao.PieceDao;
import chess.db.dto.PieceDto;
import chess.domain.board.Board;
import chess.domain.board.game.Turn;
import chess.domain.location.Location;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DBService {
    private final GameDao gameDao;
    private final PieceDao pieceDao;


    public DBService(Supplier<Connection> connector) {
        this.gameDao = new GameDao(connector);
        this.pieceDao = new PieceDao(connector);
    }


    public int saveGame(Board board) {
        int gameId = gameDao.addGame(board.getTurn());
        savePieces(gameId, board.getBoard());
        return gameId;
    }

    private void savePieces(int gameId, Map<Location, Piece> board) {
        List<PieceDto> pieces = board.entrySet().stream()
                .map(entry -> PieceDto.of(entry.getValue(), entry.getKey()))
                .toList();
        pieces.forEach(piece -> pieceDao.addPiece(gameId, piece));
    }

    public Board loadGame(int gameId) {
        Turn turn = findTurn(gameId);
        return new Board();
    }

    private Turn findTurn(int gameId) {
        return gameDao.findTurn(gameId);
    }

    private Map<Location, Piece> findBoard(int gameId) {
        return new HashMap<>();
    }


}
