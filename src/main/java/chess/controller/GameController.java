package chess.controller;

import chess.db.DBConnector;
import chess.db.DBService;
import chess.domain.board.Board;
import chess.domain.board.game.GameStatus;
import chess.domain.board.game.MoveCommand;
import chess.view.InputView;
import chess.view.OutputView;

public class GameController {
    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();
    private static final DBService DB_SERVICE = new DBService(() -> new DBConnector().getConnection());
    private Board board = null;

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
        saveGame();
    }

    private void saveGame() {
        if (board != null && !board.isFinish()) {
            DB_SERVICE.saveGame(board);
        }
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
        board = new Board();
        OUTPUT_VIEW.printBoard(board.getBoard());
    }

    private void calculateStatus() {
        checkBoard();
        double blackScore = board.calculateBlackScore();
        double whiteScore = board.calculateWhiteScore();
        OUTPUT_VIEW.printStatus(blackScore, whiteScore);
    }

    private void move(String command) {
        checkBoard();
        String[] commands = command.split(InputView.DELIMITER);
        MoveCommand moveCommand = new MoveCommand(commands[1], commands[2]);
        GameStatus gameStatus = board.proceedTurn(moveCommand);
        OUTPUT_VIEW.printBoard(board.getBoard());
        checkFinish(gameStatus);
    }

    private boolean isBoardFinish() {
        return board != null && board.isFinish();
    }

    private void checkFinish(GameStatus gameStatus) {
        if (gameStatus.equals(GameStatus.IN_PROGRESS)) {
            return;
        }
        OUTPUT_VIEW.printFinish(gameStatus);
    }

    private void checkBoard() {
        if (board == null) {
            throw new IllegalStateException("아직 게임이 시작되지 않았습니다.");
        }
    }
}
