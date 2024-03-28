package chess.controller;

import chess.db.DBConnector;
import chess.domain.board.Board;
import chess.domain.board.game.GameStatus;
import chess.domain.board.game.MoveCommand;
import chess.view.InputView;
import chess.view.OutputView;

public class GameController {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private Board board = null;

    public void run() {
        outputView.printGameStart();
        proceed();
    }

    private void proceed() {
        try {
            play();
        } catch (RuntimeException exception) {
            outputView.printException(exception);
            proceed();
        }
    }

    private void play() {
        String command = inputView.readCommand();
        while (!InputView.END.equalsIgnoreCase(command) && !isBoardFinish()) {
            playTurn(command);
            command = inputView.readCommand();
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
        outputView.printBoard(board.getBoard());
    }

    private void calculateStatus() {
        checkBoard();
        double blackScore = board.calculateBlackScore();
        double whiteScore = board.calculateWhiteScore();
        outputView.printStatus(blackScore, whiteScore);
    }

    private void move(String command) {
        checkBoard();
        String[] commands = command.split(InputView.DELIMITER);
        MoveCommand moveCommand = new MoveCommand(commands[1], commands[2]);
        GameStatus gameStatus = board.proceedTurn(moveCommand);
        outputView.printBoard(board.getBoard());
        checkFinish(gameStatus);
    }

    private boolean isBoardFinish() {
        return board != null && board.isFinish();
    }

    private void checkFinish(GameStatus gameStatus) {
        if (gameStatus.equals(GameStatus.IN_PROGRESS)) {
            return;
        }
        outputView.printFinish(gameStatus);
    }

    private void checkBoard() {
        if (board == null) {
            throw new IllegalStateException("아직 게임이 시작되지 않았습니다.");
        }
    }
}
