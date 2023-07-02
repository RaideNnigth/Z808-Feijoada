package virtual_machine.commands.operations;

import java.util.HashMap;

public interface Command {
    public void doOperation( HashMap<String, Object> args);
}
