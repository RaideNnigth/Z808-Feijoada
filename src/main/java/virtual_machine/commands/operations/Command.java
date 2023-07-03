package virtual_machine.commands.operations;

import virtual_machine.interpreter.OpParameters;
import java.util.HashMap;

public interface Command {
    public void doOperation(HashMap<OpParameters, Object> args);
}
