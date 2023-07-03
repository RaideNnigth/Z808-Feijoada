package virtual_machine.interruptions;

import virtual_machine.interruptions.operations.Command;

import java.util.HashMap;

public class InterruptionHandler {
    private Command command;

    private final HashMap<Short, Command> intCodeMap;

    public InterruptionHandler() {
        this.intCodeMap = new HashMap<>();

    }

    private void setOperation(short intCode) {
        this.command = this.intCodeMap.get(intCode);
    }

    public void doOperation(short intCode, HashMap<IntParameters, Object> args) {
        this.setOperation(intCode);
        this.command.doOperation(args);
    }
}
