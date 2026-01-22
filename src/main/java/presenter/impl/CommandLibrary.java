package presenter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLibrary {
    private final Map<String, Runnable> commands = new HashMap<>();

    public void registerCommand(String commandName, Runnable command) {
        commands.put(commandName, command);
    }

    public Runnable getCommand(String commandName) {
        Runnable command = commands.get(commandName);
        if (command == null) {
            throw new RuntimeException("No command with name " + commandName);
        }
        return command;
    }
}
