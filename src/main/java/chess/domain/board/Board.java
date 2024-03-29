package chess.domain.board;

import static chess.domain.board.BoardInitializer.initialBoard;

import chess.domain.board.game.GameStatus;
import chess.domain.board.game.MoveCommand;
import chess.domain.board.game.Score;
import chess.domain.board.game.Turn;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Location, Piece> board;
    private Turn turn;

    public Board() {
        this.board = initialBoard();
        this.turn = Turn.WHITE;
    }

    public Board(Map<Location, Piece> board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    private static GameStatus checkWinTeam(Piece targetPiece) {
        if (targetPiece.isBlack()) {
            return GameStatus.WHITE_WIN;
        }
        return GameStatus.BLACK_WIN;
    }

    private static void countPawnColumn(Location location, Map<Column, Integer> columnCount) {
        Column column = location.getColumn();
        columnCount.put(column, columnCount.getOrDefault(column, 0) + 1);
    }

    public GameStatus proceedTurn(MoveCommand moveCommand) {
        Piece sourcePiece = findPieceAt(moveCommand.getSource());
        Piece targetPiece = board.get(moveCommand.getTarget());
        validateMatchPiece(sourcePiece);
        tryMove(moveCommand, sourcePiece);
        return checkTurn(targetPiece);
    }

    private void tryMove(MoveCommand moveCommand, Piece sourcePiece) {
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

    private GameStatus checkTurn(Piece targetPiece) {
        if (targetPiece != null && targetPiece.equalPieceType(PieceType.KING)) {
            turn = turn.stop();
            return checkWinTeam(targetPiece);
        }
        turn = turn.next();
        return GameStatus.IN_PROGRESS;
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
                .filter(entry -> entry.getValue().equalPieceType(PieceType.PAWN) && isPieceColorMatching(color,
                        entry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(location -> countPawnColumn(location, columnCount));
        return columnCount.values().stream()
                .filter(integer -> integer > 1)
                .mapToInt(i -> i)
                .sum();
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

    public boolean isFinish() {
        return turn.isFinish();
    }

    private Piece findPieceAt(Location source) {
        Piece piece = board.get(source);
        validatePiece(piece);
        return piece;
    }

    private void validateMatchPiece(Piece sourcePiece) {
        if (turn.isMatchPiece(sourcePiece)) {
            return;
        }
        throw new IllegalArgumentException("해당 행동을 수행할 수 있는 순서가 아닙니다.");
    }

    private void validatePiece(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("말이 존재하지 않습니다.");
        }
    }

    public Map<Location, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Turn getTurn() {
        return turn;
    }
}
