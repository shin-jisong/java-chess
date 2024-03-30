package chess.domain.piece;

import chess.domain.game.board.Route;
import java.util.Objects;

public abstract class Piece {
    private final Color color;
    private final PieceType pieceType;

    protected Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public abstract boolean canMove(Route route);

    public boolean isBlack() {
        return this.color == Color.BLACK;
    }

    public boolean isAllyPiece(Piece other) {
        return this.color == other.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return color == piece.color && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, pieceType);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Color getColor() {
        return color;
    }
}
