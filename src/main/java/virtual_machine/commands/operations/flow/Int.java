package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class Int implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        RegFlags sr = (RegFlags) args.get(OpParameters.SR_FLAGS);
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        // We must get from the memory the 16 bit constant
        ip.setReg((short) (ip.getReg() + 1)); // Increment IP
        short jmpAddr = mc.getInstruction(ip.getReg()); // Get operand addr in dataMem

        if (sr.getZf() == true)
            ip.setReg((short) (ip.getReg() + jmpAddr));
    }
}
