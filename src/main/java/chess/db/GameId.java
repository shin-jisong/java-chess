package chess.db;

import java.util.Objects;

public class GameId {
    private final int value;

    public GameId(int gameId) {
        validate(gameId);
        this.value = gameId;
    }

    private void validate(int gameId) {
        if (gameId < 1) {
            throw new IllegalArgumentException("유효하지 않은 게임입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameId gameId1 = (GameId) o;
        return value == gameId1.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }
}
