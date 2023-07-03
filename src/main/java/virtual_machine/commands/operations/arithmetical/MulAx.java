package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MulAx implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork)args.get(OpParameters.AX);
        RegWork dx = (RegWork)args.get(OpParameters.DX);
        RegWork si = (RegWork)args.get(OpParameters.SI);
        RegFlags sr = (RegFlags) args.get(OpParameters.SR_FLAGS);

        int result = ax.getReg() * ax.getReg();
        sr.setCf(OperationsUtils.hasCarry(ax.getReg(), ax.getReg()));
        sr.setOf(OperationsUtils.hasOverflow32(result));


    }
}
