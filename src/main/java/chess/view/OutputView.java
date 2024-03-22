package chess.view;

import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class OutputView {
    public void printGameStart() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printBoard(Map<Location, Piece> board) {
        Arrays.stream(Row.values()).sorted(Comparator.reverseOrder())
                .forEach(row -> {
                    printBoardRow(row, board);
                    System.out.println();
                });
    }

    private void printBoardRow(Row row, Map<Location, Piece> board) {
        Arrays.stream(Column.values())
                .map(column -> new Location(column, row))
                .map(location -> board.get(location))
                .map(this::convertPieceToString)
                .forEach(System.out::print);
    }

    private String convertPieceToString(Piece piece) {
        if (piece == null) {
            return ".";
        }
        String pieceName = piece.getName();
        if (piece.isBlack()) {
            return pieceName.toUpperCase();
        }
        return pieceName.toLowerCase();
    }

    public void printException(RuntimeException exception) {
        System.out.println(exception.getMessage());
    }
}
