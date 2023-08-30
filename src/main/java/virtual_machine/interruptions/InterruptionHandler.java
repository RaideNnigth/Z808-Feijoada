package virtual_machine.interruptions;

import virtual_machine.interpreter.OpParameters;

import java.util.HashMap;

public interface InterruptionHandler {
    public void doOperation(byte serviceCode, HashMap<OpParameters, Object> args);
}
