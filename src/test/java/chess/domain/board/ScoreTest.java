package chess.domain.board;

import chess.domain.piece.Bishop;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.piece.WhitePawn;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {
    private final List<Piece> pieces = List.of(
            new Bishop(Color.BLACK),
            new BlackPawn(),
            new Knight(Color.BLACK),
            new Rook(Color.BLACK),
            new King(Color.WHITE),
            new Queen(Color.WHITE),
            new WhitePawn()
    );

    @DisplayName("흑팀의 점수를 계산한다.")
    @Test
    void calculateBlackTest() {
        Assertions.assertThat(Score.calculateBlack(pieces, 0)).isEqualTo(11.5);
    }

    @DisplayName("백팀의 점수를 계산한다.")
    @Test
    void calculateWhiteTest() {
        Assertions.assertThat(Score.calculateWhite(pieces, 0)).isEqualTo(10);
    }



}
