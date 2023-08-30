package virtual_machine.commands.operations.stack;

import java.util.HashMap;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

public class PopF implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        /*
        RegFlags sr = (RegFlags) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSr();
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        RegWork sp = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSp(); // Get stack pointer register
        RegWork ss = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSs(); // Get start of stack segment
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ip.setValue((short) (ip.getValue() + 1)); // Increment IP

        sr.setValue((short) (mc.getWordBE(sp.getValue())));

        if (sp.getValue() != ss.getValue())
            sp.setValue((short) (sp.getValue() + 1)); // Increment SP

         */
    }
}
