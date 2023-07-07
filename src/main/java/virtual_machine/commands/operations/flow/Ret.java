package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

/**
 * Ret Operation:
 *
 * The programmer has to be careful when using this, because it will return to the address that is at the top of the stack
 */
public class Ret implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        RegWork sp = (RegWork) args.get(OpParameters.SP); // Get stack pointer register
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        short retAddr = mc.getWordBE(sp.getReg()); // Pop from stack where sub routine was called
        sp.setReg((short) (sp.getReg() - 1));

        ip.setReg(retAddr);
    }
}
