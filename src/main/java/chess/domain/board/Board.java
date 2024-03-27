package chess.domain.board;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Bishop;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.WhitePawn;
import chess.view.MoveCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Board {
    private final Map<Location, Piece> board;
    private Turn turn;

    public Board() {
        this.board = initialBoard();
        this.turn = Turn.WHITE;
    }

    public boolean isFinish() {
        return turn.isFinish();
    }

    private Map<Location, Piece> initialBoard() {
        Map<Location, Piece> initialBoard = new HashMap<>();
        initialPawnSetting(initialBoard);
        initialRookSetting(initialBoard);
        initialKnightSetting(initialBoard);
        initialBishopSetting(initialBoard);
        initialQueenSetting(initialBoard);
        initialKingSetting(initialBoard);
        return initialBoard;
    }

    private static void validatePiece(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("말이 존재하지 않습니다.");
        }
    }

    private void initialPawnSetting(Map<Location, Piece> board) {
        for (Column value : Column.values()) {
            board.put(new Location(value, Row.TWO), new WhitePawn());
            board.put(new Location(value, Row.SEVEN), new BlackPawn());
        }
    }

    private void initialRookSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.A, Row.ONE), new Rook(Color.WHITE));
        board.put(new Location(Column.H, Row.ONE), new Rook(Color.WHITE));
        board.put(new Location(Column.A, Row.EIGHT), new Rook(Color.BLACK));
        board.put(new Location(Column.H, Row.EIGHT), new Rook(Color.BLACK));
    }

    private void initialKnightSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.B, Row.ONE), new Knight(Color.WHITE));
        board.put(new Location(Column.G, Row.ONE), new Knight(Color.WHITE));
        board.put(new Location(Column.B, Row.EIGHT), new Knight(Color.BLACK));
        board.put(new Location(Column.G, Row.EIGHT), new Knight(Color.BLACK));
    }

    private void initialQueenSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.D, Row.ONE), new Queen(Color.WHITE));
        board.put(new Location(Column.D, Row.EIGHT), new Queen(Color.BLACK));
    }

    private void initialKingSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.E, Row.ONE), new King(Color.WHITE));
        board.put(new Location(Column.E, Row.EIGHT), new King(Color.BLACK));
    }

    public void proceedTurn(MoveCommand moveCommand) {
        Piece sourcePiece = findPieceAt(moveCommand.getSource());
        Piece targetPiece = board.get(moveCommand.getTarget());
        validateMatchPiece(sourcePiece);
        tryMove(moveCommand, sourcePiece);
        checkTurn(targetPiece);
    }

    private void checkTurn(Piece targetPiece) {
        if (targetPiece != null && targetPiece.equalPieceType(PieceType.KING)) {
            turn = turn.stop();
            return;
        }
        turn = turn.next();
    }

    private void tryMove(MoveCommand moveCommand, Piece sourcePiece) {
        Route route = createPath(moveCommand);
        if (sourcePiece.canMove(route)) {
            move(moveCommand, sourcePiece);
            return;
        }
        throw new IllegalArgumentException("유효하지 않은 움직임입니다.");
    }

    private void validateMatchPiece(Piece sourcePiece) {
        if (turn.isMatchPiece(sourcePiece)) {
            return;
        }
        throw new IllegalArgumentException("해당 행동을 수행할 수 있는 순서가 아닙니다.");
    }

    private void move(MoveCommand moveCommand, Piece movingPiece) {
        board.remove(moveCommand.getSource());
        board.put(moveCommand.getTarget(), movingPiece);
    }

    public double calculateBlackScore() {
        List<Piece> pieces = board.values().stream().toList();
        int deductionPawnCount = countSameColumnPawn(Color.BLACK);
        return Score.calculateBlack(pieces, deductionPawnCount);
    }

    public double calculateWhiteScore() {
        List<Piece> pieces = board.values().stream().toList();
        int deductionPawnCount = countSameColumnPawn(Color.WHITE);
        return Score.calculateWhite(pieces, deductionPawnCount);
    }

    private int countSameColumnPawn(Color color) {
        Map<Column, Integer> columnCount = new HashMap<>();
        board.entrySet().stream()
                .filter(entry -> isPieceColorMatching(color, entry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(location -> countPawnColumn(location, columnCount));
        return columnCount.values().stream()
                .filter(integer -> integer > 1)
                .mapToInt(i -> i)
                .sum();
    }

    private static void countPawnColumn(Location location, Map<Column, Integer> columnCount) {
        Column column = location.getColumn();
        columnCount.put(column, columnCount.getOrDefault(column, 0) + 1);
    }

    private boolean isPieceColorMatching(Color color, Piece piece) {
        return color == Color.BLACK && piece.isBlack() || color == Color.WHITE && !piece.isBlack();
    }


    private Route createPath(MoveCommand moveCommand) {
        List<Direction> directions = DirectionFinder.find(moveCommand.getSource(), moveCommand.getTarget());
        List<SquareState> squareStates = createPathState(moveCommand.getSource(), directions);
        return new Route(directions, squareStates);
    }

    private List<SquareState> createPathState(Location current, List<Direction> directions) {
        Piece movingPiece = findPieceAt(current);
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

    private void initialBishopSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.C, Row.ONE), new Bishop(Color.WHITE));
        board.put(new Location(Column.F, Row.ONE), new Bishop(Color.WHITE));
        board.put(new Location(Column.C, Row.EIGHT), new Bishop(Color.BLACK));
        board.put(new Location(Column.F, Row.EIGHT), new Bishop(Color.BLACK));
    }

    private Piece findPieceAt(Location source) {
        Piece piece = board.get(source);
        validatePiece(piece);
        return piece;
    }

    public Map<Location, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
