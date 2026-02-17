package presenter.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import presenter.EmptyCommand;
import presenter.EmptyContextCommand;
import presenter.ICommand;
import presenter.IContextCommand;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandLibrary {
    @Getter
    private final Map<String, ICommand> commands = new HashMap<>();
    @Getter
    private final Map<String, IContextCommand> contextCommands = new HashMap<>();

    static private final ICommand emptyCommand = new EmptyCommand();
    static private final IContextCommand emptyContextCommand = new EmptyContextCommand();

    public void registerCommand(String commandName, ICommand command) {
        log.trace("Add command {}.", commandName);
        commands.put(commandName, command);
    }

    public void registerContextCommand(String commandName, IContextCommand command) {
        log.trace("Add context command {}.", commandName);
        contextCommands.put(commandName, command);
    }

    public ICommand getCommand(String commandName) {
        ICommand command = commands.get(commandName);
        if (command == null) {
            log.debug("Command {} wasn't found", commandName);
            return emptyCommand;
        }
        return command;
    }

    public IContextCommand getContextCommand(String commandName) {
        IContextCommand command = contextCommands.get(commandName);
        if (command == null) {
            log.debug("Context command {} wasn't found", commandName);
            return emptyContextCommand;
        }
        return command;
    }
}
