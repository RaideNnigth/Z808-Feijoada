package virtual_machine.interruptions.operations;

import virtual_machine.interruptions.IntParameters;

import java.util.HashMap;

public interface Command {
    public void doOperation(HashMap<IntParameters, Object> args);
}
