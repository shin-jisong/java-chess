package chess.db;

import chess.db.dao.PieceDao;
import chess.db.dto.PieceDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakePieceDao implements PieceDao {

    private final Map<GameId, List<PieceDto>> PIECE = new LinkedHashMap<>();;

    {
        PIECE.put(new GameId(1), List.of(
                new PieceDto("PAWN", "BLACK", "A", 7),
                new PieceDto("KING", "WHITE", "B", 4)
        ));
        PIECE.put(new GameId(2), List.of(
                new PieceDto("PAWN", "WHITE", "A", 2),
                new PieceDto("QUEEN", "BLACK", "B", 4)
        ));
    }

    @Override
    public void addPiece(GameId gameId, PieceDto pieceDto) {
        if (PIECE.containsKey(gameId)) {
            List<PieceDto> pieces = new ArrayList<>(PIECE.get(gameId));
            pieces.add(pieceDto);
            PIECE.put(gameId, pieces);
            return;
        }
        PIECE.put(gameId, List.of(pieceDto));
    }

    @Override
    public List<PieceDto> findAllPiecesByGameId(GameId gameId) {
        if (PIECE.containsKey(gameId)) {
            return PIECE.get(gameId);
        }
        return List.of();
    }

    @Override
    public void deletePieces(GameId gameId) {
        PIECE.remove(gameId);
    }
}
