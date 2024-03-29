package chess.db;

import chess.db.dao.GameDao;
import chess.domain.board.game.Turn;
import java.util.HashMap;
import java.util.Map;

public class FakeGameDao implements GameDao {
    private final Map<Integer, Turn> GAME = new HashMap<>();

     {
        GAME.put(1, Turn.WHITE);
        GAME.put(2, Turn.BLACK);
    }

    @Override
    public int addGame(Turn turn) {
        int autoIncrement = GAME.size() + 1;
        GAME.put(autoIncrement, turn);
        return autoIncrement;
    }

    @Override
    public int findLatestGameId() {
         if (GAME.isEmpty()) {
             return -1;
         }
        return GAME.size();
    }

    @Override
    public Turn findTurn(int gameId) {
        return GAME.get(gameId);
    }

    @Override
    public void deleteGame(int gameId) {
        GAME.remove(gameId);
    }
}
