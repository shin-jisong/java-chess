package chess.domain.board;

public enum TurnStatus {
    WHITE,
    BLACK,
    FINISH;

    public TurnStatus next() {
        if (this.equals(WHITE)) {
            return BLACK;
        }
        return WHITE;
    }

    public TurnStatus stop() {
        return FINISH;
    }

    public boolean isFinish() {
        return this.equals(FINISH);
    }
}
