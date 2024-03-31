package chess.domain.game.board;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Bishop;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.WhitePawn;
import java.util.HashMap;
import java.util.Map;

public class BoardInitializer {

    public static Map<Location, Piece> initialBoard() {
        Map<Location, Piece> initialBoard = new HashMap<>();
        initialPawnSetting(initialBoard);
        initialRookSetting(initialBoard);
        initialKnightSetting(initialBoard);
        initialBishopSetting(initialBoard);
        initialQueenSetting(initialBoard);
        initialKingSetting(initialBoard);
        return initialBoard;
    }

    private static void initialPawnSetting(Map<Location, Piece> board) {
        for (Column value : Column.values()) {
            board.put(new Location(value, Row.TWO), new WhitePawn());
            board.put(new Location(value, Row.SEVEN), new BlackPawn());
        }
    }

    private static void initialRookSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.A, Row.ONE), new Rook(Color.WHITE));
        board.put(new Location(Column.H, Row.ONE), new Rook(Color.WHITE));
        board.put(new Location(Column.A, Row.EIGHT), new Rook(Color.BLACK));
        board.put(new Location(Column.H, Row.EIGHT), new Rook(Color.BLACK));
    }

    private static void initialKnightSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.B, Row.ONE), new Knight(Color.WHITE));
        board.put(new Location(Column.G, Row.ONE), new Knight(Color.WHITE));
        board.put(new Location(Column.B, Row.EIGHT), new Knight(Color.BLACK));
        board.put(new Location(Column.G, Row.EIGHT), new Knight(Color.BLACK));
    }

    private static void initialBishopSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.C, Row.ONE), new Bishop(Color.WHITE));
        board.put(new Location(Column.F, Row.ONE), new Bishop(Color.WHITE));
        board.put(new Location(Column.C, Row.EIGHT), new Bishop(Color.BLACK));
        board.put(new Location(Column.F, Row.EIGHT), new Bishop(Color.BLACK));
    }

    private static void initialQueenSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.D, Row.ONE), new Queen(Color.WHITE));
        board.put(new Location(Column.D, Row.EIGHT), new Queen(Color.BLACK));
    }

    private static void initialKingSetting(Map<Location, Piece> board) {
        board.put(new Location(Column.E, Row.ONE), new King(Color.WHITE));
        board.put(new Location(Column.E, Row.EIGHT), new King(Color.BLACK));
    }
}
