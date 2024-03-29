package chess.db.dao;

import chess.domain.board.game.Turn;

public interface GameDao {
    int addGame(Turn turn);

    int findLatestGameId();

    Turn findTurn(int gameId);

    void deleteGame(int gameId);
}
