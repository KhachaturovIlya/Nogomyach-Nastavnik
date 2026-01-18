package presenter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLibrary {
    private final Map<String, List<Runnable>> commands = new HashMap<>();

    public void registerCommand(String commandName, List<Runnable> command) {
        commands.put(commandName, command);
    }

    public List<Runnable> getCommand(String commandName) {
        List<Runnable> command = commands.get(commandName);
        if (command == null) {
            throw new RuntimeException("No command with name " + commandName);
        }
        return command;
    }
}
