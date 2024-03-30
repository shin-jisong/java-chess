package chess.db;

import chess.db.dao.GameDao;
import chess.domain.game.Turn;
import java.util.LinkedHashMap;
import java.util.Map;

public class FakeGameDao implements GameDao {
    private final Map<GameId, Turn> GAME = new LinkedHashMap<>();

    {
        GAME.put(new GameId(1), Turn.WHITE);
        GAME.put(new GameId(2), Turn.BLACK);
    }

    @Override
    public GameId addGame(Turn turn) {
        int autoIncrement = GAME.size() + 1;
        GameId gameId = new GameId(autoIncrement);
        GAME.put(gameId, turn);
        return gameId;
    }

    @Override
    public GameId findLatestGameId() {
        if (GAME.isEmpty()) {
            return null;
        }
        return new GameId(GAME.size());
    }

    @Override
    public Turn findTurn(GameId gameId) {
        return GAME.get(gameId);
    }

    @Override
    public void deleteGame(GameId gameId) {
        GAME.remove(gameId);
    }
}
