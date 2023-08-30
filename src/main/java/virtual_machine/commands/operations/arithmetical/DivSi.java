package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;
import virtual_machine.utils.BinaryUtils;

import java.util.HashMap;

public class DivSi implements Command {

    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork dx = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getDx();
        RegWork si = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSi();

        int dividend = BinaryUtils.concatShorts(dx.getValue(), ax.getValue());
        int result = dividend / si.getValue();
        int remainder = dividend % si.getValue();

        ax.setValue((short) result);
        dx.setValue((short) remainder);
    }
}
