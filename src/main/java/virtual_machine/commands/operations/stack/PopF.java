package virtual_machine.commands.operations.stack;

import java.util.HashMap;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegWork;

public class PopF implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork sr = (RegWork) args.get(OpParameters.SR_FLAGS);
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        RegWork sp = (RegWork) args.get(OpParameters.SP); // Get stack pointer register
        RegWork ss = (RegWork) args.get(OpParameters.SS); // Get start of stack segment
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ip.setReg((short) (ip.getReg() + 1)); // Increment IP

        sr.setReg((short) (mc.getWord(sp.getReg())));

        if (sp.getReg() != ss.getReg())
            sp.setReg((short) (sp.getReg() + 1)); // Increment SP
    }
}
