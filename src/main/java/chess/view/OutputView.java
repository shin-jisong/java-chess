package chess.view;

import chess.domain.board.game.GameStatus;
import chess.domain.location.Column;
import chess.domain.location.Location;
import chess.domain.location.Row;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class OutputView {
    public static final String ONE_SQUARE = ".";

    private static String checkMinionPieceName(PieceType pieceType) {
        if (pieceType == PieceType.ROOK) {
            return "R";
        }
        if (pieceType == PieceType.BISHOP) {
            return "B";
        }
        if (pieceType == PieceType.KNIGHT) {
            return "N";
        }
        return "P";
    }

    public void printGameStart() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : " + InputView.START);
        System.out.println("> 게임 현황 : " + InputView.STATUS);
        System.out.println("> 게임 종료 : " + InputView.END);
        System.out.println("> 게임 이동 : " + InputView.MOVE + " source위치 target위치 - 예. " + InputView.MOVE + " b2 b3");
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
                .map(board::get)
                .map(this::convertPieceToString)
                .forEach(System.out::print);
    }

    public void printStatus(double blackScore, double whiteScore) {
        System.out.println("현재 각 진영의 점수입니다.");
        System.out.println("흑팀: " + blackScore);
        System.out.println("백팀: " + whiteScore);
        printOutcome(blackScore, whiteScore);
    }

    private void printOutcome(double blackScore, double whiteScore) {
        if (blackScore > whiteScore) {
            System.out.println("현재 흑팀이 이기고 있습니다.");
            return;
        }
        if (whiteScore > blackScore) {
            System.out.println("현재 백팀이 이기고 있습니다.");
            return;
        }
        System.out.println("현재 흑팀과 백팀이 비기고 있습니다.");
    }

    private String convertPieceToString(Piece piece) {
        if (piece == null) {
            return ONE_SQUARE;
        }
        String pieceName = checkPieceName(piece);
        if (piece.isBlack()) {
            return pieceName.toUpperCase();
        }
        return pieceName.toLowerCase();
    }

    private String checkPieceName(Piece piece) {
        PieceType pieceType = piece.getPieceType();
        if (pieceType == PieceType.KING) {
            return "K";
        }
        if (pieceType == PieceType.QUEEN) {
            return "Q";
        }
        return checkMinionPieceName(pieceType);
    }

    public void printException(RuntimeException exception) {
        System.out.println(exception.getMessage());
    }

    public void printFinish(GameStatus gameStatus) {
        if (gameStatus.equals(GameStatus.BLACK_WIN)) {
            System.out.println("흑팀의 승리로 게임이 종료되었습니다.");
        }
        if (gameStatus.equals(GameStatus.WHITE_WIN)) {
            System.out.println("백팀의 승리로 게임이 종료되었습니다.");
        }
    }

    public void printSave() {
        System.out.println("게임의 현황을 저장하고 게임을 종료합니다.");
    }

    public void printNotSave() {
        System.out.println("게임의 현황을 저장하지 않고 게임을 종료합니다.");
    }

    public void printLoad() {
        System.out.println("보드를 불러왔습니다. 게임을 시작합니다.");
    }

    public void printNotLoad() {
        System.out.println("지난 보드 이력을 삭제하고 새로운 게임을 시작합니다.");
    }
}
