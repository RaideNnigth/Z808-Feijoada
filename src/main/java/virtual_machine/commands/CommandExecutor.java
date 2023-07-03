package virtual_machine.commands;


import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.arithmetical.AddAxAx;
import virtual_machine.commands.operations.arithmetical.AddAxCte;
import virtual_machine.commands.operations.arithmetical.AddAxDir;
import virtual_machine.commands.operations.arithmetical.AddAxDx;
import virtual_machine.commands.operations.flow.Halt;
import virtual_machine.commands.operations.logical.*;
import virtual_machine.commands.operations.move.MovAxCte;
import virtual_machine.commands.operations.move.MovAxDir;
import virtual_machine.commands.operations.move.MovDirAx;
import virtual_machine.commands.operations.move.MovDxAx;
import virtual_machine.interpreter.OpParameters;

import java.util.HashMap;

public class CommandExecutor {

    private Command command;

    private final HashMap<Short, Command> opCodeMap;

    public CommandExecutor() {
        this.opCodeMap = new HashMap<>();
        // Instructions <Hex opcode, Operation Object>
        // ADD Instructions
        this.opCodeMap.put((short) 0x03C0, new AddAxAx());
        this.opCodeMap.put((short) 0x03C2, new AddAxDx());
        this.opCodeMap.put((short) 0x04FF, new AddAxCte());

        // OR Instructions
        this.opCodeMap.put((short) 0x0BC0, new OrAxAx());
        this.opCodeMap.put((short) 0x0BC2, new OrAxDx());
        this.opCodeMap.put((short) 0x0DFF, new OrAxCte());

        // AND Instructions
        this.opCodeMap.put((short) 0x23C0, new AndAxAx());
        this.opCodeMap.put((short) 0x23C2, new AndAxDx());
        this.opCodeMap.put((short) 0x25FF, new AndAxCte());

        // XOR Instructions
        this.opCodeMap.put((short) 0x33C0, new XorAxAx());
        this.opCodeMap.put((short) 0x33C2, new XorAxDx());
        this.opCodeMap.put((short) 0x35FF, new XorAxCte());

        // MOV Instructions
        this.opCodeMap.put((short) 0xB8FF, new MovAxCte());
        this.opCodeMap.put((short) 0xA1FF, new MovAxDir());
        this.opCodeMap.put((short) 0xD08B, new MovDxAx());
        this.opCodeMap.put((short) 0xA3FF, new MovDirAx());

        // HLT Instruction
        this.opCodeMap.put((short) 0x0000, new Halt());
    }

    private void setOperation(short opCode) {
        this.command = this.opCodeMap.get(opCode);
    }

    public void doOperation(short opCode, HashMap<OpParameters, Object> args) {
        this.setOperation(opCode);
        this.command.doOperation(args);
    }

}
