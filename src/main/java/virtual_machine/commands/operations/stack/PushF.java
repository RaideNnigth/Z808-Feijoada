package virtual_machine.commands.operations.stack;

import java.util.HashMap;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegWork;

public class PushF implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork sr = (RegWork) args.get(OpParameters.SR_FLAGS);
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        RegWork sp = (RegWork) args.get(OpParameters.SP); // Get stack pointer register
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ip.setReg((short) (ip.getReg() + 1)); // Increment IP
        sp.setReg((short) (sp.getReg() - 1)); // Decrement SP

        mc.writeStack(sr.getReg(), sp.getReg());
    }
}
