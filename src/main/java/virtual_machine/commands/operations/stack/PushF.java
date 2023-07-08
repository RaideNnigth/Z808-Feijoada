package virtual_machine.commands.operations.stack;

import java.util.HashMap;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

public class PushF implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        /*
        RegWork sr = (RegWork) args.get(OpParameters.SR_FLAGS);
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        RegWork sp = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSp(); // Get stack pointer register
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ip.setValue((short) (ip.getValue() + 1)); // Increment IP
        sp.setValue((short) (sp.getValue() - 1)); // Decrement SP

        mc.writeWord(sr.getValue(), sp.getValue());

         */
    }
}
