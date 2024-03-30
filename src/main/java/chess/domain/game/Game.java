package chess.domain.game;

import chess.domain.game.board.Board;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Game {
    public static final int INITIAL_KING_COUNT = 2;
    private final Board board;
    private Turn turn;

    public Game() {
        this.board = new Board();
        this.turn = Turn.WHITE;
    }

    public Game(Map<Location, Piece> board, Turn turn) {
        this.board = new Board(board);
        this.turn = turn;
    }

    public GameStatus proceedTurn(MoveCommand moveCommand) {
        board.tryMove(moveCommand, turn);
        return checkTurn();
    }

    private GameStatus checkTurn() {
        if (isKingCaught()) {
            Turn winTeam = turn;
            turn = turn.stop();
            return checkWinTeam(winTeam);
        }
        turn = turn.next();
        return GameStatus.IN_PROGRESS;
    }

    private boolean isKingCaught() {
        return board.countKing() != INITIAL_KING_COUNT;
    }

    private GameStatus checkWinTeam(Turn turn) {
        if (turn == Turn.WHITE) {
            return GameStatus.WHITE_WIN;
        }
        return GameStatus.BLACK_WIN;
    }

    public double calculateBlackScore() {
        int deductionPawnCount = board.countSameColumnPawn(Color.BLACK);
        return Score.calculateBlack(board.getPieces(), deductionPawnCount);
    }

    public double calculateWhiteScore() {
        int deductionPawnCount = board.countSameColumnPawn(Color.WHITE);
        return Score.calculateWhite(board.getPieces(), deductionPawnCount);
    }

    public boolean isFinish() {
        return turn.isFinish();
    }

    public Map<Location, Piece> getBoard() {
        return Collections.unmodifiableMap(board.getBoard());
    }

    public Turn getTurn() {
        return turn;
    }
}
