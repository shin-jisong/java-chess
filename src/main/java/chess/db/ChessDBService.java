package chess.db;

import chess.db.dao.GameDao;
import chess.db.dao.GameDaoImpl;
import chess.db.dao.PieceDao;
import chess.db.dao.PieceDaoImpl;
import chess.db.dto.PieceDto;
import chess.domain.game.Game;
import chess.domain.game.board.Board;
import chess.domain.game.Turn;
import chess.domain.location.Location;
import chess.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessDBService {
    private final GameDao gameDao;
    private final PieceDao pieceDao;


    public ChessDBService(ChessDBConnector connector) {
        this.gameDao = new GameDaoImpl(connector);
        this.pieceDao = new PieceDaoImpl(connector);
    }

    protected ChessDBService(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public void saveGame(Game game) {
        GameId gameId = gameDao.addGame(game.getTurn());
        savePieces(gameId, game.getBoard());
    }

    public Game loadGame() {
        GameId gameId = gameDao.findLatestGameId();
        Turn turn = findTurn(gameId);
        Map<Location, Piece> board = findBoard(gameId);
        return new Game(board, turn);
    }

    public void deleteGame() {
        GameId gameId = gameDao.findLatestGameId();
        pieceDao.deletePieces(gameId);
        gameDao.deleteGame(gameId);
    }

    public boolean isLatestGame() {
        return gameDao.findLatestGameId() != null;
    }

    private void savePieces(GameId gameId, Map<Location, Piece> board) {
        List<PieceDto> pieces = board.entrySet().stream()
                .map(entry -> PieceDto.of(entry.getValue(), entry.getKey()))
                .toList();
        pieces.forEach(piece -> pieceDao.addPiece(gameId, piece));
    }

    private Turn findTurn(GameId gameId) {
        return gameDao.findTurn(gameId);
    }

    private Map<Location, Piece> findBoard(GameId gameId) {
        List<PieceDto> pieces = pieceDao.findAllPiecesByGameId(gameId);
        Map<Location, Piece> board = new HashMap<>();
        for (PieceDto pieceDto : pieces) {
            board.put(pieceDto.makeLocation(), pieceDto.makePiece());
        }
        return board;
    }
}
