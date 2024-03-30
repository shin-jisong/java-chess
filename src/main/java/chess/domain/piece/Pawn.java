package chess.domain.piece;

import chess.domain.game.board.Route;

public abstract class Pawn extends Piece {
    private static final int INIT_MOVE_DISTANCE = 2;
    private static final int NORMAL_MOVE_DISTANCE = 1;
    private static final int ONE_DIRECTION = 1;

    private boolean isMoved;

    protected Pawn(Color color, boolean isMoved) {
        super(color, PieceType.PAWN);
        this.isMoved = isMoved;
    }

    abstract boolean isBackward(Route route);

    @Override
    public boolean canMove(Route route) {
        boolean movable = checkMovable(route);
        checkMoved(movable);
        return movable;
    }

    private boolean checkMovable(Route route) {
        if (isNotGeneralMove(route)) {
            return false;
        }
        if (route.containsDiagonal()) {
            return route.isSizeOf(NORMAL_MOVE_DISTANCE) && route.isTargetHasEnemy();
        }
        if (route.isSizeOf(INIT_MOVE_DISTANCE)) {
            return route.isAllEmpty() && isFirstMove();
        }
        return route.isSizeOf(NORMAL_MOVE_DISTANCE) && route.isAllEmpty();
    }

    private void checkMoved(boolean movable) {
        if (movable) {
            isMoved = true;
        }
    }

    private boolean isNotGeneralMove(Route route) {
        if (!route.isDirectionsCount(ONE_DIRECTION)) {
            return true;
        }
        return isBackward(route);
    }

    private boolean isFirstMove() {
        return !isMoved;
    }
}
