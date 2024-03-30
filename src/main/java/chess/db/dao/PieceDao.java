package chess.db.dao;

import chess.db.GameId;
import chess.db.dto.PieceDto;
import java.util.List;

public interface PieceDao {
    void addPiece(GameId gameId, PieceDto pieceDto);

    List<PieceDto> findAllPiecesByGameId(GameId gameId);

    void deletePieces(GameId gameId);
}
