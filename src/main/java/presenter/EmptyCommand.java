package presenter;

public class EmptyCommand implements ICommand {
    @Override
    public void execute(String contextString) throws Exception {
        // Nothing happens...
    }
}
