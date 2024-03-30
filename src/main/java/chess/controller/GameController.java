package chess.controller;

import chess.db.ChessDBConnector;
import chess.db.ChessDBService;
import chess.domain.game.Game;
import chess.domain.game.board.Board;
import chess.domain.game.GameStatus;
import chess.domain.game.MoveCommand;
import chess.view.InputView;
import chess.view.OutputView;

public class GameController {
    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();
    private static final ChessDBService DB_SERVICE = new ChessDBService(new ChessDBConnector());

    private Game game = null;

    public void run() {
        OUTPUT_VIEW.printGameStart();
        proceed();
    }

    private void proceed() {
        try {
            play();
        } catch (RuntimeException exception) {
            OUTPUT_VIEW.printException(exception);
            proceed();
        }
    }

    private void play() {
        String command = INPUT_VIEW.readCommand();
        while (!InputView.END.equalsIgnoreCase(command) && !isBoardFinish()) {
            playTurn(command);
            command = INPUT_VIEW.readCommand();
        }
        checkSaveGame();
    }

    private void checkSaveGame() {
        if (game != null && !game.isFinish()) {
            saveGameOrNot();
        }
    }

    private void saveGameOrNot() {
        if (INPUT_VIEW.readFinishSave()) {
            DB_SERVICE.saveGame(game);
            OUTPUT_VIEW.printSave();
            return;
        }
        OUTPUT_VIEW.printNotSave();
    }

    private void playTurn(String command) {
        if (InputView.START.equalsIgnoreCase(command)) {
            createBoard();
            return;
        }
        if (InputView.STATUS.equalsIgnoreCase(command)) {
            calculateStatus();
            return;
        }
        move(command);
    }

    private void createBoard() {
        findBoardIfExist();
        OUTPUT_VIEW.printBoard(game.getBoard());
    }

    private void findBoardIfExist() {
        if (DB_SERVICE.isLatestGame()) {
            loadBoardOrNot();
        }
        if (game == null) {
            game = new Game();
        }
    }

    private void loadBoardOrNot() {
        if (INPUT_VIEW.readLoadGame()) {
            game = DB_SERVICE.loadGame();
            DB_SERVICE.deleteGame();
            OUTPUT_VIEW.printLoad();
            return;
        }
        DB_SERVICE.deleteGame();
        OUTPUT_VIEW.printNotLoad();
    }

    private void calculateStatus() {
        checkBoard();
        double blackScore = game.calculateBlackScore();
        double whiteScore = game.calculateWhiteScore();
        OUTPUT_VIEW.printStatus(blackScore, whiteScore);
    }

    private void move(String command) {
        checkBoard();
        String[] commands = command.split(InputView.DELIMITER);
        MoveCommand moveCommand = new MoveCommand(commands[1], commands[2]);
        GameStatus gameStatus = game.proceedTurn(moveCommand);
        OUTPUT_VIEW.printBoard(game.getBoard());
        checkFinish(gameStatus);
    }

    private boolean isBoardFinish() {
        return game != null && game.isFinish();
    }

    private void checkFinish(GameStatus gameStatus) {
        if (gameStatus.equals(GameStatus.IN_PROGRESS)) {
            return;
        }
        OUTPUT_VIEW.printFinish(gameStatus);
    }

    private void checkBoard() {
        if (game == null) {
            throw new IllegalStateException("아직 게임이 시작되지 않았습니다.");
        }
    }
}
