package virtual_machine.commands.operations;

import java.util.List;

public interface Command {
    public void doOperation( List<Object> args);
}
