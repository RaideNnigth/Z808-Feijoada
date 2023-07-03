package virtual_machine.commands;


import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.arithmetical.AddAxAx;
import virtual_machine.commands.operations.arithmetical.AddAxCte;
import virtual_machine.commands.operations.arithmetical.AddAxDx;
import virtual_machine.commands.operations.flow.Halt;
import virtual_machine.interpreter.OpParameters;

import java.util.HashMap;

public class CommandExecutor {

    private Command command;

    private final HashMap<Short, Command> opCodeMap;

    public CommandExecutor() {
        this.opCodeMap = new HashMap<>();
        // Instructions <Hex opcode, Operation Object>
        this.opCodeMap.put((short) 0x03C0, new AddAxAx());
        this.opCodeMap.put((short) 0x03C2, new AddAxDx());
        this.opCodeMap.put((short) 0x05FF, new AddAxCte());
        this.opCodeMap.put((short) 0xEEFF, new Halt());
    }

    private void setOperation(short opCode) {
        this.command = this.opCodeMap.get(opCode);
    }

    public void doOperation(short opCode, HashMap<OpParameters, Object> args) {
        this.setOperation(opCode);
        this.command.doOperation(args);
    }

}
