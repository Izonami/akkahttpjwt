package console;

import service.MasterService;

import java.util.HashMap;
import java.util.Map;

public final class CommandExecutor {

    private static CommandExecutor instance;

    private final Map<String, Command> commands = new HashMap<>();

    private CommandExecutor() {
        commands.put("startm", () -> MasterService.getInstance().start());
        commands.put("stopm", () -> MasterService.getInstance().stop());
    }

    public void executeCommand(final String command) {
        if(command != null && command.length() > 0) {
            if(commands.containsKey(command)) {
                commands.get(command).execute();
            }
        }
    }

    public static CommandExecutor getInstance() {
        if(instance == null) {
            instance = new CommandExecutor();
        }

        return instance;
    }
}
