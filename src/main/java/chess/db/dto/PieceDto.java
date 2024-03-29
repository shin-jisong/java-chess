package chess.db.dto;

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
        String location
) {
    public static PieceDto of(Piece piece, Location location) {
        return new PieceDto(
                piece.getPieceType().name(),
                piece.getColor().name(),
                location.toString()
        );
    }

    public Location makeLocation() {
        return Location.of(location);
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
        Location location = Location.of(this.location);
        if (teamColor == Color.BLACK) {
            BlackPawn blackPawn = new BlackPawn();
            return checkPawnMoved(blackPawn, Row.SEVEN);
        }
        WhitePawn whitePawn = new WhitePawn();
        return checkPawnMoved(whitePawn, Row.TWO);
    }

    private Pawn checkPawnMoved(Pawn pawn, Row row) {
        Location location = Location.of(this.location);
        if (location.getRow().equals(row)) {
            pawn.settingInitialMoved(false);
            return pawn;
        }
        pawn.settingInitialMoved(true);
        return pawn;
    }
}
