package chess.domain.board;

import chess.domain.game.Score;
import chess.domain.game.board.Board;
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
    @DisplayName("흑팀의 점수를 계산한다.")
    @Test
    void calculateBlackTest() {
        Score score = new Score(new Board());
        Assertions.assertThat(score.getBlackScore()).isEqualTo(38.0);
    }

    @DisplayName("백팀의 점수를 계산한다.")
    @Test
    void calculateWhiteTest() {
        Score score = new Score(new Board());
        Assertions.assertThat(score.getWhiteScore()).isEqualTo(38.0);
    }

}
