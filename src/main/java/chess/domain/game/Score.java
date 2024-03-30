package chess.domain.game;

import chess.domain.game.board.Board;
import chess.domain.piece.Bishop;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.Knight;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.WhitePawn;
import java.util.Map;

public class Score {
    private static final Map<Piece, Double> scoreCard =
            Map.of(
                    new Queen(Color.BLACK), 9.0, new Queen(Color.WHITE), 9.0,
                    new Rook(Color.BLACK), 5.0, new Rook(Color.WHITE), 5.0,
                    new Bishop(Color.BLACK), 3.0, new Bishop(Color.WHITE), 3.0,
                    new Knight(Color.BLACK), 2.5, new Knight(Color.WHITE), 2.5,
                    new BlackPawn(), 1.0, new WhitePawn(), 1.0
            );
    private static final Double PAWN_DEDUCTION_SCORE = 0.5;
    private static final Double ZERO_SCORE = 0.0;

    private final double whiteScore;
    private final double blackScore;

    public Score(Board board) {
        whiteScore = calculateScore(board, Color.WHITE);
        blackScore = calculateScore(board, Color.BLACK);
    }

    private double calculateScore(Board board, Color color) {
        int deductionPawnCount = board.countSameColumnPawn(color);
        double score = board.getPieces().stream()
                .filter(piece -> piece.isBlack() == color.isBlack())
                .mapToDouble(this::findScore)
                .sum();
        return score - (PAWN_DEDUCTION_SCORE * deductionPawnCount);
    }

    private double findScore(Piece piece) {
        return scoreCard.entrySet().stream()
                .filter(entry -> piece.equals(entry.getKey()))
                .mapToDouble(Map.Entry::getValue)
                .findFirst()
                .orElse(ZERO_SCORE);
    }

    public double getWhiteScore() {
        return whiteScore;
    }

    public double getBlackScore() {
        return blackScore;
    }
}
