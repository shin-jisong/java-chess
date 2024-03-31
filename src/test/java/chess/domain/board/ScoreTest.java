package chess.domain.board;

import chess.domain.game.Score;
import chess.domain.game.board.Board;
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
