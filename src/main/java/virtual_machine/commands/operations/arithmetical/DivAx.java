package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.RegWork;
import virtual_machine.utils.BinaryUtils;

import java.util.HashMap;

public class DivAx implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork)args.get(OpParameters.AX);
        RegWork dx = (RegWork)args.get(OpParameters.DX);

        int dividend = BinaryUtils.concatShorts(dx.getReg(), ax.getReg());
        int result = dividend / ax.getReg();
        int remainder = dividend % ax.getReg();

        ax.setReg((short) result);
        dx.setReg((short) remainder);
    }
}
