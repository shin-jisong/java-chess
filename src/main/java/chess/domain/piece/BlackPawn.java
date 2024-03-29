package chess.domain.piece;

import chess.domain.board.Route;

public class BlackPawn extends Pawn {
    private static final boolean INITIAL_MOVED = false;

    public BlackPawn() {
        super(Color.BLACK, INITIAL_MOVED);
    }

    public BlackPawn(boolean moved) {
        super(Color.BLACK, moved);
    }

    @Override
    boolean isBackward(Route route) {
        return route.isUpside();
    }
}
