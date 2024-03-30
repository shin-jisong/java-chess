package chess.domain.board.game;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class Score {
    private static final Map<PieceType, Double> scoreCard =
            Map.of(
                    PieceType.QUEEN, 9.0,
                    PieceType.ROOK, 5.0,
                    PieceType.BISHOP, 3.0,
                    PieceType.KNIGHT, 2.5,
                    PieceType.PAWN, 1.0
            );
    private static final Double PAWN_DEDUCTION_SCORE = 0.5;
    private static final Double ZERO_SCORE = 0.0;

    public static double calculateBlack(List<Piece> pieces, int deductionPawnCount) {
        double score = pieces.stream()
                .filter(Piece::isBlack)
                .mapToDouble(Score::findScore)
                .sum();
        return score - (PAWN_DEDUCTION_SCORE * deductionPawnCount);
    }

    public static double calculateWhite(List<Piece> pieces, int deductionPawnCount) {
        double score = pieces.stream()
                .filter(piece -> !piece.isBlack())
                .mapToDouble(Score::findScore)
                .sum();
        return score - (PAWN_DEDUCTION_SCORE * deductionPawnCount);
    }

    private static double findScore(Piece piece) {
        return scoreCard.entrySet().stream()
                .filter(entry -> piece.equalPieceType(entry.getKey()))
                .mapToDouble(Map.Entry::getValue)
                .findFirst()
                .orElse(ZERO_SCORE);
    }
}
