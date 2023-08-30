package virtual_machine.os_interruptions;

import virtual_machine.interpreter.OpParameters;
import virtual_machine.interruptions.InterruptionHandler;
import virtual_machine.os_interruptions.operations.DirectReadChar;
import virtual_machine.commands.operations.Command;
import virtual_machine.os_interruptions.operations.GetSystemDate;
import virtual_machine.os_interruptions.operations.GetSystemTime;

import java.util.HashMap;

public class OSInterruptionHandler implements InterruptionHandler {
    private Command command;

    private final HashMap<Byte, Command> osServiceMap;

    public OSInterruptionHandler() {
        this.osServiceMap = new HashMap<>();
        this.osServiceMap.put((byte) 0x07, new DirectReadChar());
        this.osServiceMap.put((byte) 0x2C, new GetSystemTime());
        this.osServiceMap.put((byte) 0x2C, new GetSystemDate());

    }

    private void setOperation(short intCode) {
        this.command = this.osServiceMap.get(intCode);
    }

    public void doOperation(byte serviceCode, HashMap<OpParameters, Object> args) {
        this.setOperation(serviceCode);
        this.command.doOperation(args);
    }
}
