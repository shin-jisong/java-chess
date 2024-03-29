package chess.db.dao;

import chess.db.dto.PieceDto;
import java.util.List;

public interface PieceDao {
    void addPiece(int gameId, PieceDto pieceDto);

    List<PieceDto> findAllPiecesByGameId(int gameId);

    void deletePieces(int gameId);
}
