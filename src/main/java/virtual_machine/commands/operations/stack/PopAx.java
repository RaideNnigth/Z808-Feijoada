package virtual_machine.commands.operations.stack;

import java.util.HashMap;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

public class PopAx implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        RegWork sp = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSp(); // Get stack pointer register
        RegWork ss = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSs(); // Get start of stack segment
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ax.setValue((short) (mc.getWordBE(sp.getValue())));

        if (sp.getValue() != ss.getValue())
            sp.setValue((short) (sp.getValue() + 1)); // Decrement SP
    }
}
