package chess.db.dto;

import chess.domain.location.Location;
import chess.domain.piece.Piece;

public record PieceDto(
        String pieceType,
        String color,
        String location
) {
    public static PieceDto of(Piece piece, Location location) {
        return new PieceDto(
                piece.getPieceType().name(),
                piece.getColor().name(),
                location.toString()
        );
    }
}
