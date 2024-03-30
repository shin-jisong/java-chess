package chess.domain.game.board;

import static chess.domain.game.board.BoardInitializer.initialBoard;

import chess.domain.game.MoveCommand;
import chess.domain.game.Turn;
import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.WhitePawn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Location, Piece> board;

    public Board() {
        this.board = initialBoard();
    }

    public Board(Map<Location, Piece> board) {
        this.board = board;
    }

    public void tryMove(MoveCommand moveCommand, Turn turn) {
        validatePieceAtLocation(moveCommand.getSource());
        Piece sourcePiece = board.get(moveCommand.getSource());
        Piece targetPiece = board.get(moveCommand.getTarget());
        validateMatchPiece(sourcePiece, turn);
        Route route = createPath(moveCommand);
        if (sourcePiece.canMove(route)) {
            move(moveCommand, sourcePiece);
            return;
        }
        throw new IllegalArgumentException("유효하지 않은 움직임입니다.");
    }

    private void move(MoveCommand moveCommand, Piece movingPiece) {
        board.remove(moveCommand.getSource());
        board.put(moveCommand.getTarget(), movingPiece);
    }

    public int countSameColumnPawn(Color color) {
        Pawn pawn = makeMatchPawn(color);
        Map<Column, Integer> columnCount = new HashMap<>();
        board.entrySet().stream()
                .filter(entry -> entry.getValue().equals(pawn))
                .map(Map.Entry::getKey)
                .forEach(location -> countPawnColumn(location, columnCount));
        return columnCount.values().stream()
                .filter(integer -> integer > 1)
                .mapToInt(i -> i)
                .sum();
    }

    private Pawn makeMatchPawn(Color color) {
        if (color.equals(Color.BLACK)) {
            return new BlackPawn();
        }
        return new WhitePawn();
    }

    private void countPawnColumn(Location location, Map<Column, Integer> columnCount) {
        Column column = location.getColumn();
        columnCount.put(column, columnCount.getOrDefault(column, 0) + 1);
    }

    private Route createPath(MoveCommand moveCommand) {
        List<Direction> directions = DirectionFinder.find(moveCommand.getSource(), moveCommand.getTarget());
        List<SquareState> squareStates = createPathState(moveCommand.getSource(), directions);
        return new Route(directions, squareStates);
    }

    private List<SquareState> createPathState(Location current, List<Direction> directions) {
        validatePieceAtLocation(current);
        Piece movingPiece = board.get(current);
        List<SquareState> squareStates = new ArrayList<>();
        Location moved = current;
        for (Direction direction : directions) {
            moved = moved.move(direction);
            Piece movedPiece = board.get(moved);
            squareStates.add(checkSquareStates(movingPiece, movedPiece));
        }
        return squareStates;
    }

    private SquareState checkSquareStates(Piece movingPiece, Piece movedPiece) {
        if (movedPiece == null) {
            return SquareState.EMPTY;
        }
        if (movingPiece.isAllyPiece(movedPiece)) {
            return SquareState.ALLY;
        }
        return SquareState.ENEMY;
    }

    private void validateMatchPiece(Piece sourcePiece, Turn turn) {
        if (turn.isMatchPiece(sourcePiece)) {
            return;
        }
        throw new IllegalArgumentException("해당 행동을 수행할 수 있는 순서가 아닙니다.");
    }

    private void validatePieceAtLocation(Location location) {
        if (board.get(location) == null) {
            throw new IllegalArgumentException("말이 존재하지 않습니다.");
        }
    }


    public int countKing() {
        return (int) board.values()
                .stream()
                .filter(piece -> piece.equals(new King(Color.WHITE)) || piece.equals(new King(Color.BLACK)))
                .count();
    }

    public Map<Location, Piece> getBoard() {
        return board;
    }

    public List<Piece> getPieces() {
        return board.values().stream().toList();
    }
}
