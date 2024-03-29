package chess.db.dto;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Bishop;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.WhitePawn;
import java.util.Objects;

public record PieceDto(
        String pieceType,
        String color,
        String column,
        int row
) {

    private static final boolean NOT_MOVED = false;
    private static final boolean MOVED = true;

    public static PieceDto of(Piece piece, Location location) {
        return new PieceDto(
                piece.getPieceType().name(),
                piece.getColor().name(),
                location.getColumn().name(),
                location.getRow().getRank()
        );
    }

    public Location makeLocation() {
        return Location.of(column + row);
    }

    public Piece makePiece() {
        PieceType type = PieceType.valueOf(pieceType);
        Color teamColor = Color.valueOf(color);
        if (type == PieceType.KING) {
            return new King(teamColor);
        }
        if (type == PieceType.QUEEN) {
            return new Queen(teamColor);
        }
        return makeMinion(type, teamColor);
    }

    private Piece makeMinion(PieceType type, Color teamColor) {
        if (type == PieceType.ROOK) {
            return new Rook(teamColor);
        }
        if (type == PieceType.BISHOP) {
            return new Bishop(teamColor);
        }
        if (type == PieceType.KNIGHT) {
            return new Knight(teamColor);
        }
        return makePawn(teamColor);
    }

    private Pawn makePawn(Color teamColor) {
        Location location = makeLocation();
        if (teamColor == Color.BLACK) {
            return makeBlackPawn(location);
        }
        return makeWhitePawn(location);

    }

    private BlackPawn makeBlackPawn(Location location) {
        if (location.getRow().equals(Row.SEVEN)) {
            return new BlackPawn(NOT_MOVED);
        }
        return new BlackPawn(MOVED);
    }

    private WhitePawn makeWhitePawn(Location location) {
        if (location.getRow().equals(Row.TWO)) {
            return new WhitePawn(NOT_MOVED);
        }
        return new WhitePawn(MOVED);
    }
}
