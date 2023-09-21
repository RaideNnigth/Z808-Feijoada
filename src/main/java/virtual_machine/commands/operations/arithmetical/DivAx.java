package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;
import utils.BinaryUtils;

import java.util.HashMap;

public class DivAx implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork dx = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getDx();

        int dividend = BinaryUtils.concatShorts(dx.getValue(), ax.getValue());
        int result = dividend / ax.getValue();
        int remainder = dividend % ax.getValue();

        ax.setValue((short) result);
        dx.setValue((short) remainder);
    }
}
