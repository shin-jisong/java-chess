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
    private static final int NO_LATEST_GAME = -1;
    private final GameDao gameDao;
    private final PieceDao pieceDao;


    public DBService(DBConnector connector) {
        this.gameDao = new GameDao(connector);
        this.pieceDao = new PieceDao(connector);
    }


    public void saveGame(Board board) {
        int gameId = gameDao.addGame(board.getTurn());
        savePieces(gameId, board.getBoard());
    }

    public Board loadGame() {
        int gameId = gameDao.findLatestGameId();
        Turn turn = findTurn(gameId);
        Map<Location, Piece> board = findBoard(gameId);
        return new Board(board, turn);
    }

    public void deleteGame() {
        int gameId = gameDao.findLatestGameId();
        pieceDao.deletePieces(gameId);
        gameDao.deleteGame(gameId);
    }

    public boolean isLatestGame() {
        return gameDao.findLatestGameId() != NO_LATEST_GAME;
    }

    private void savePieces(int gameId, Map<Location, Piece> board) {
        List<PieceDto> pieces = board.entrySet().stream()
                .map(entry -> PieceDto.of(entry.getValue(), entry.getKey()))
                .toList();
        pieces.forEach(piece -> pieceDao.addPiece(gameId, piece));
    }

    private Turn findTurn(int gameId) {
        return gameDao.findTurn(gameId);
    }

    private Map<Location, Piece> findBoard(int gameId) {
        List<PieceDto> pieces = pieceDao.findAllPiecesByGameId(gameId);
        Map<Location, Piece> board = new HashMap<>();
        for (PieceDto pieceDto : pieces) {
            board.put(pieceDto.makeLocation(), pieceDto.makePiece());
        }
        return board;
    }


}
