import console.CommandExecutor;

import java.util.Scanner;

public final class StartService {

    public static void main(String[] args) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()) {
                final String command = scanner.next();
                CommandExecutor.getInstance().executeCommand(command);
            }
        }).start();
    }
}
