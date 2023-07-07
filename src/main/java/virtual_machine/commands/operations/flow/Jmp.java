package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

// nome de escolha do miguel
public class Jmp implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        // We must get from the memory the 16 bit constant
        ip.setReg((short) (ip.getReg() + 1)); // Increment IP

        short jmpAddr = mc.getWordBE(ip.getReg()); // Get operand addr in dataMem

        ip.setReg((short) (ip.getReg() + jmpAddr));
    }
}
