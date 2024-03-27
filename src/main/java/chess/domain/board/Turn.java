package chess.domain.board;

public class Turn {
    private TurnStatus turnStatus;

    public Turn() {
        this.turnStatus = TurnStatus.BLACK;
    }

    public void proceedNext() {
        turnStatus = turnStatus.next();
    }

    public void stop() {
        turnStatus = turnStatus.stop();
    }

    public boolean isFinish() {
        return turnStatus.isFinish();
    }
}
