package presenter;

import java.util.List;

public interface ICommand {
    void execute(String contextString) throws Exception;
}
