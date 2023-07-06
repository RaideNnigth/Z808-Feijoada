package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxInd implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {

        // Get regs
        RegWork ax = (RegWork) args.get(OpParameters.AX);
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        RegFlags sr = (RegFlags) args.get(OpParameters.SR_FLAGS);

        // Get memory controller
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        // We must get from the memory the 16 bit constant
        ip.setReg((short) (ip.getReg() + 1)); // Increment IP
        short indirectAddr = mc.getInstructionBE(ip.getReg()); // Get operand addr in dataMem
        short cte = mc.getDataBE(mc.getDataBE(indirectAddr)); // Get operand from dataMem

        int result = ax.getReg() + cte; // Doing op

        // Flags
        sr.setOf(OperationsUtils.hasOverflow16(result));
        sr.setCf(OperationsUtils.hasCarry(ax.getReg(), (int) cte));
        sr.setPf(OperationsUtils.parityBit(result));
        sr.setZf(OperationsUtils.isZero(result));
        sr.setSf(OperationsUtils.hasSignal(result));

        // Set result in AX
        ax.setReg((short) result);
    }
}