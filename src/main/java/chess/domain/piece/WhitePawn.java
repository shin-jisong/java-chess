package chess.domain.piece;

import chess.domain.board.Route;

public class WhitePawn extends Pawn {
    private static final boolean INITIAL_MOVED = false;

    public WhitePawn() {
        super(Color.WHITE, INITIAL_MOVED);
    }

    public WhitePawn(boolean moved) {
        super(Color.WHITE, moved);
    }

    @Override
    boolean isBackward(Route route) {
        return route.isDownside();
    }
}
