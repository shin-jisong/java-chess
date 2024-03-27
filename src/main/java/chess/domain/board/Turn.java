package chess.domain.board;

import chess.domain.piece.Piece;

public enum Turn {
    WHITE,
    BLACK,
    FINISH_WHITE_WIN,
    FINISH_BLACK_WIN;

    private static final IllegalArgumentException NOT_TURN_EXCEPTION =
            new IllegalArgumentException("해당 행동을 수행할 수 있는 순서가 아닙니다.");

    public Turn next() {
        validateTurn();
        if (this.equals(BLACK)) {
            return WHITE;
        }
        return BLACK;
    }

    public Turn stopByCatchKing(Piece piece) {
        validateTurn();
        if (piece.isBlack()) {
            return FINISH_WHITE_WIN;
        }
        return FINISH_BLACK_WIN;
    }

    public boolean isMatchPiece(Piece piece) {
        validateTurn();
        if (this.equals(BLACK)) {
            return piece.isBlack();
        }
        return !piece.isBlack();
    }

    public boolean isFinish() {
        return this.equals(FINISH_WHITE_WIN) || this.equals(FINISH_BLACK_WIN);
    }

    private void validateTurn() {
        if (isFinish()) {
            throw NOT_TURN_EXCEPTION;
        }
    }
}
