package chess.db;

import chess.domain.game.Game;
import chess.domain.game.board.Board;
import chess.domain.game.Turn;
import chess.domain.location.Location;
import chess.domain.piece.BlackPawn;
import chess.domain.piece.Color;
import chess.domain.piece.PieceType;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChessDBServiceTest {
    private TestDBService dbService = null;

    @BeforeEach
    void setUp() {
        dbService = new TestDBService(new FakeGameDao(), new FakePieceDao());
    }

    @DisplayName("게임을 저장할 수 있다.")
    @Test
    void saveGameTest() {
        Game game = new Game(
                Map.of(Location.of("A1"), new BlackPawn()),
                Turn.BLACK
        );
        dbService.saveGame(game);
        Game loadGame = dbService.loadGame();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(loadGame.getTurn()).isEqualTo(game.getTurn()),
                () -> Assertions.assertThat(loadGame.getBoard().get(Location.of("A1")).getPieceType())
                        .isEqualTo(PieceType.PAWN),
                () -> Assertions.assertThat(loadGame.getBoard().get(Location.of("A1")).getColor())
                        .isEqualTo(Color.BLACK)
        );
    }

    @DisplayName("게임을 불러올 수 있다.")
    @Test
    void loadGameTest() {
        Game loadGame = dbService.loadGame();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(loadGame.getTurn()).isEqualTo(Turn.BLACK),
                () -> Assertions.assertThat(loadGame.getBoard().get(Location.of("B4")).getPieceType())
                        .isEqualTo(PieceType.QUEEN),
                () -> Assertions.assertThat(loadGame.getBoard().get(Location.of("B4")).getColor())
                        .isEqualTo(Color.BLACK)
        );
    }

    @DisplayName("게임을 삭제할 수 있다.")
    @Test
    void deleteGameTest() {
        dbService.deleteGame();
        Game loadGame = dbService.loadGame();

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(loadGame.getTurn()).isEqualTo(Turn.WHITE),
                () -> Assertions.assertThat(loadGame.getBoard().get(Location.of("B4")).getPieceType())
                        .isEqualTo(PieceType.KING),
                () -> Assertions.assertThat(loadGame.getBoard().get(Location.of("B4")).getColor())
                        .isEqualTo(Color.WHITE)
        );
    }

    @DisplayName("현재 게임이 있는지 확인할 수 있다.")
    @Nested
    class DeleteGameTest {
        @DisplayName("현재 게임이 존재한다.")
        @Test
        void trueTest() {
            Assertions.assertThat(dbService.isLatestGame()).isTrue();
        }

        @DisplayName("현재 게임이 존재하지 않는다.")
        @Test
        void falseTest() {
            dbService.deleteGame();
            dbService.deleteGame();
            Assertions.assertThat(dbService.isLatestGame()).isFalse();
        }
    }


}
