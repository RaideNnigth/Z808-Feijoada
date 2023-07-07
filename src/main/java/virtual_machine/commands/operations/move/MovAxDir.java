package virtual_machine.commands.operations.move;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MovAxDir implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) args.get(OpParameters.AX);
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ip.setReg((short) (ip.getReg() + 1)); // Increment IP

        short operandAddr = mc.getWordBE(ip.getReg()); // Get operand addr in dataMem
        short cte = mc.getWordBE(operandAddr);

        ax.setReg(cte);
    }
}
