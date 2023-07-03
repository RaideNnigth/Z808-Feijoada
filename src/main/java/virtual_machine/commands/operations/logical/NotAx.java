package virtual_machine.commands.operations.logical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class NotAx implements Command {
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) args.get(OpParameters.AX);
        RegFlags sr = (RegFlags) args.get(OpParameters.SR_FLAGS);

        short result = (short) (~ax.getReg());

        sr.setCf(false);
        sr.setPf(OperationsUtils.parityBit(result));
        sr.setZf(OperationsUtils.isZero(result));
        sr.setSf(OperationsUtils.hasSignal(result));
        sr.setOf(false);

        ax.setReg(result);
    }
}
