package chess.domain.board.game;

import chess.domain.piece.Piece;

public enum Turn {
    WHITE,
    BLACK,
    FINISH;

    private static final IllegalArgumentException NOT_TURN_EXCEPTION =
            new IllegalArgumentException("해당 행동을 수행할 수 있는 순서가 아닙니다.");

    public Turn next() {
        validateTurn();
        if (this.equals(BLACK)) {
            return WHITE;
        }
        return BLACK;
    }

    public Turn stop() {
        validateTurn();
        return FINISH;
    }

    public boolean isMatchPiece(Piece piece) {
        validateTurn();
        if (this.equals(BLACK)) {
            return piece.isBlack();
        }
        return !piece.isBlack();
    }

    public boolean isFinish() {
        return this.equals(FINISH);
    }

    private void validateTurn() {
        if (isFinish()) {
            throw NOT_TURN_EXCEPTION;
        }
    }
}
