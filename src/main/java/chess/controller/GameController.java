package chess.controller;

import chess.domain.board.Board;
import chess.view.InputView;
import chess.view.MoveCommand;
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
        while (!InputView.END.equalsIgnoreCase(command) && !checkBoardFinish()) {
            playTurn(command);
            command = inputView.readCommand();
        }
        outputView.printFinish();
    }

    private boolean checkBoardFinish() {
        return board != null && board.isFinish();
    }

    private void playTurn(String command) {
        if (InputView.START.equalsIgnoreCase(command)) {
            createBoard();
            return;
        }
        if (InputView.STATUS.equalsIgnoreCase(command)) {
            checkStatus();
            return;
        }
        move(command);
    }

    private void checkStatus() {
        double blackScore = board.calculateBlackScore();
        double whiteScore = board.calculateWhiteScore();
        OutputView.printStatus(blackScore, whiteScore);
    }

    private void createBoard() {
        board = new Board();
        outputView.printBoard(board.getBoard());
    }

    private void move(String command) {
        checkBoard();
        String[] commands = command.split(InputView.DELIMITER);
        MoveCommand moveCommand = new MoveCommand(commands[1], commands[2]);
        board.proceedTurn(moveCommand);
        outputView.printBoard(board.getBoard());
    }

    private void checkBoard() {
        if (board == null) {
            throw new IllegalStateException("아직 게임이 시작되지 않았습니다.");
        }
    }
}
