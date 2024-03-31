package chess.db.dao;

import chess.db.GameId;
import chess.domain.game.Turn;

public interface GameDao {
    GameId addGame(Turn turn);

    GameId findLatestGameId();

    Turn findTurn(GameId gameId);

    void deleteGame(GameId gameId);
}
