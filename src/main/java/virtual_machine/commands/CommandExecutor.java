package virtual_machine.commands;


import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.arithmetical.*;
import virtual_machine.commands.operations.flow.*;
import virtual_machine.commands.operations.logical.*;
import virtual_machine.commands.operations.move.*;
import virtual_machine.commands.operations.stack.*;
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
        this.opCodeMap.put((short) 0x05FF, new AddAxDir());
        this.opCodeMap.put((short) 0x06FF, new AddAxInd()); // Verificar possível conflito

        // SUB Instructions
        this.opCodeMap.put((short) 0x2BC0, new SubAxAx());
        this.opCodeMap.put((short) 0x2BC2, new SubAxDx());
        this.opCodeMap.put((short) 0x2CFF, new SubAxCte());
        this.opCodeMap.put((short) 0x2DFF, new SubAxDir());
        this.opCodeMap.put((short) 0x2EFF, new SubAxInd()); // Verificar possível conflito

        // DIV Instructions
        this.opCodeMap.put((short) 0xF7F0, new DivAx());
        this.opCodeMap.put((short) 0xF7F6, new DivSi());

        // MUL Instructions
        this.opCodeMap.put((short) 0xF7E0, new MulAx());
        this.opCodeMap.put((short) 0xF7E6, new MulSi());

        // CMP Instructions
        this.opCodeMap.put((short) 0x3BC2, new CmpAxDx());
        this.opCodeMap.put((short) 0x3DFF, new CmpAxCte());
        this.opCodeMap.put((short) 0x3CFF, new CmpAxDir());
        this.opCodeMap.put((short) 0x3EFF, new CmpAxInd()); // Vericar possível conflito

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
        this.opCodeMap.put((short) 0x8BD1, new MovAxDx());
        this.opCodeMap.put((short) 0x8BD0, new MovDxAx());
        this.opCodeMap.put((short) 0xA3FF, new MovDirAx());

        // HLT Instruction
        this.opCodeMap.put((short) 0x0000, new Halt());

        // FLOW Instructions
        this.opCodeMap.put((short) 0xEBFF, new Jmp());
        this.opCodeMap.put((short) 0x74FF, new JmpZero());
        this.opCodeMap.put((short) 0x75FF, new JmpNotZero());
        this.opCodeMap.put((short) 0x7AFF, new JmpNotNeg());
        this.opCodeMap.put((short) 0xE8FF, new Call());
        this.opCodeMap.put((short) 0xCDFF, new Int());
        this.opCodeMap.put((short) 0xC3FF, new Ret());

        // STACK Instructions
        this.opCodeMap.put((short) 0x58FF, new PopAx());
        //this.opCodeMap.put((short) 0x9DFF, new PopF());
        this.opCodeMap.put((short) 0x50FF, new PushAx());
        //this.opCodeMap.put((short) 0x9CFF, new PushF());

    }

    private void setOperation(short opCode) {
        this.command = this.opCodeMap.get(opCode);
        if (this.command == null)
            this.command = new Halt();
    }

    public void doOperation(short opCode, HashMap<OpParameters, Object> args) {
        this.setOperation(opCode);
        this.command.doOperation(args);
    }

}
