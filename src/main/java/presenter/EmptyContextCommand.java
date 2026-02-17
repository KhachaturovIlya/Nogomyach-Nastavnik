package presenter;

import java.util.ArrayList;
import java.util.List;

public class EmptyContextCommand implements IContextCommand {
    @Override
    public List<String> execute(String context) throws Exception {
        return new ArrayList<>(); // Nothing happens
    }
}
