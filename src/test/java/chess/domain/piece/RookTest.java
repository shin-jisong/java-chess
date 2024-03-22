package chess.domain.piece;

import chess.domain.board.Direction;
import chess.domain.board.Route;
import chess.domain.board.SquareState;
import chess.domain.board.Step;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RookTest {
    private static final Rook ROOK = new Rook(Color.BLACK);

    /*
    @DisplayName("한 방향으로 이루어지지 않은 경로로 움직일 수 없다.")
    @Test
    void tooManyDirectionTest() {
        Route manyDirectionRoute = new Route(List.of(
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.UP, SquareState.EMPTY)
        ));

        Assertions.assertThat(ROOK.canMove(manyDirectionRoute))
                .isFalse();
    }

    @DisplayName("경로 중간에 기물이 위치한다면 움직일 수 없다.")
    @Test
    void pathHasPieceTest() {
        Route notEmptyRoute = new Route(List.of(
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.ALLY),
                new Step(Direction.DOWN, SquareState.EMPTY)
        ));

        Assertions.assertThat(ROOK.canMove(notEmptyRoute))
                .isFalse();
    }

    @DisplayName("목적지에 아군이 존재한다면 움직일 수 없다.")
    @Test
    void allyLocatedAtTargetTest() {
        Route manyDirectionRoute = new Route(List.of(
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.ALLY)
        ));

        Assertions.assertThat(ROOK.canMove(manyDirectionRoute))
                .isFalse();
    }

    @DisplayName("대각선 방향으로 움직일 수 없다.")
    @Test
    void canNotMoveDiagonalTest() {
        Route diagonalDirectionRoute = new Route(List.of(
                new Step(Direction.UP_RIGHT, SquareState.EMPTY),
                new Step(Direction.UP_RIGHT, SquareState.EMPTY),
                new Step(Direction.UP_RIGHT, SquareState.EMPTY)
        ));

        Assertions.assertThat(ROOK.canMove(diagonalDirectionRoute))
                .isFalse();
    }

    @DisplayName("최대 7칸까지 움직일 수 있다.")
    @Test
    void maxDistanceMoveTest() {
        Route manyDirectionRoute = new Route(List.of(
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.EMPTY),
                new Step(Direction.DOWN, SquareState.ENEMY)
        ));

        Assertions.assertThat(ROOK.canMove(manyDirectionRoute))
                .isTrue();
    }
     */
}
