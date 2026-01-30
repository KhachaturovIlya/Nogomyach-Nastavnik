package presenter;

import java.util.List;

public interface IContextCommand {
    List<String> execute(String context) throws Exception;
}
