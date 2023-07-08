package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MulAx implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork dx = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getDx();
        RegWork si = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSi();
        RegFlags sr = (RegFlags) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSr();

        long result = ax.getValue() * ax.getValue();
        sr.setCf(OperationsUtils.hasCarry(ax.getValue(), ax.getValue()));
        sr.setOf(OperationsUtils.hasOverflow32(result));

        dx.setValue((short) ((result >>> 48) & 0x7FFFFFFF));
        ax.setValue((short) ((result << 48) >>> 48));
    }
}
