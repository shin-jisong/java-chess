package chess.domain.board;

import chess.domain.piece.Bishop;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class TurnTest {
    @DisplayName("다음 턴을 잘 넘긴다.")
    @Nested
    class nextTest {
        @DisplayName("지금의 턴이 흑일 때, 백의 턴이 된다.")
        @Test
        void nextWhiteTurnTest() {
            Turn turn = Turn.BLACK;
            turn = turn.next();
            Assertions.assertThat(turn).isEqualTo(Turn.WHITE);
        }

        @DisplayName("지금의 턴이 백일 때, 흑의 턴이 된다.")
        @Test
        void nextBlackTurnTest() {
            Turn turn = Turn.WHITE;
            turn = turn.next();
            Assertions.assertThat(turn).isEqualTo(Turn.BLACK);
        }

        @DisplayName("턴이 멈춘 상태라면, 다음 턴을 진행할 수 없다.")
        @Test
        void invalidNextTurnTest() {
            Turn turn = Turn.FINISH;
            Assertions.assertThatThrownBy(turn::next)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 행동을 수행할 수 있는 순서가 아닙니다.");
        }
    }

    @DisplayName("턴을 멈춘다.")
    @Nested
    class stopTest {
        @DisplayName("지금의 턴이 진행 중일 때, 턴을 멈출 수 있다.")
        @ParameterizedTest
        @EnumSource(value = Turn.class, names = {"BLACK", "WHITE"})
        void stopTurnTest(Turn turn) {
            Turn stopTurn = turn.stop();
            Assertions.assertThat(stopTurn).isEqualTo(Turn.FINISH);
        }

        @DisplayName("턴을 이미 멈춘 상태라면 또 다시 멈출 수 없다.")
        @Test
        void invalidNextTurnTest() {
            Turn turn = Turn.FINISH;
            Assertions.assertThatThrownBy(turn::stop)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 행동을 수행할 수 있는 순서가 아닙니다.");
        }
    }

    @DisplayName("해당 턴에 맞는 기물인지 확인한다.")
    @Nested
    class isMatchPieceTest {
        @DisplayName("지금의 기물이 흑일 때, 맞는 기물인지 확인한다.")
        @ParameterizedTest
        @CsvSource({"BLACK, true", "WHITE, false"})
        void isMatchBlackPiece(Turn turn, boolean result) {
            Bishop bishop = new Bishop(Color.BLACK);
            Assertions.assertThat(turn.isMatchPiece(bishop)).isEqualTo(result);
        }

        @DisplayName("지금의 기물이 백일 때, 맞는 기물인지 확인한다.")
        @ParameterizedTest
        @CsvSource({"BLACK, false", "WHITE, true"})
        void isMatchWhitePiece(Turn turn, boolean result) {
            Bishop bishop = new Bishop(Color.WHITE);
            Assertions.assertThat(turn.isMatchPiece(bishop)).isEqualTo(result);
        }


        @DisplayName("턴을 멈춘 상태라면, 기물을 확인할 수 없다.")
        @Test
        void invalidMatchPieceTest() {
            Turn turn = Turn.FINISH;
            Bishop bishop = new Bishop(Color.WHITE);
            Assertions.assertThatThrownBy(() -> turn.isMatchPiece(bishop))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 행동을 수행할 수 있는 순서가 아닙니다.");
        }
    }

    @DisplayName("턴이 끝났는지 확인한다.")
    @ParameterizedTest
    @CsvSource({"BLACK, false", "WHITE, false", "FINISH, true"})
    void isMatchWhitePiece(Turn turn, boolean result) {
        Assertions.assertThat(turn.isFinish()).isEqualTo(result);
    }
}
