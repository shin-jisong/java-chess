package chess.view;

import java.util.Scanner;

public class InputView {
    public static final String MOVE = "move";
    public static final String START = "start";
    public static final String END = "end";
    public static final String STATUS = "status";
    public static final String DELIMITER = " ";

    private static final Scanner SCANNER = new Scanner(System.in);

    public String readCommand() {
        String command = SCANNER.nextLine();
        validateCommand(command);
        return command;
    }

    public boolean readLoadGame() {
        System.out.println("저장된 게임이 있습니다. 불러오시겠습니까? (Y 혹은 N)");
        String command = SCANNER.nextLine();
        validateYesOrNo(command);
        return command.equalsIgnoreCase("y");
    }

    public boolean readFinishSave() {
        System.out.println("끝나지 않은 게임이 있습니다. 저장하시겠습니까? (Y 혹은 N)");
        String command = SCANNER.nextLine();
        validateYesOrNo(command);
        return command.equalsIgnoreCase("y");
    }

    private void validateCommand(String command) {
        String[] commands = command.split(DELIMITER);
        if (commands.length == 1) {
            validateSingleCommand(command);
            return;
        }
        if (commands[0].equalsIgnoreCase(MOVE) && commands.length == 3) {
            return;
        }
        throw new IllegalArgumentException("잘못된 입력입니다.");
    }

    private void validateSingleCommand(String command) {
        if (command.equalsIgnoreCase(START)) {
            return;
        }
        if (command.equalsIgnoreCase(END)) {
            return;
        }
        if (command.equalsIgnoreCase(STATUS)) {
            return;
        }
        throw new IllegalArgumentException("잘못된 입력입니다.");
    }

    private void validateYesOrNo(String command) {
        if (!command.equalsIgnoreCase("y") && !command.equalsIgnoreCase("n")) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
