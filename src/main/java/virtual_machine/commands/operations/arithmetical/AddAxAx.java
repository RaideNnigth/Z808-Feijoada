package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxAx implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegFlags sr = (RegFlags) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSr();

        int result = ax.getValue() + ax.getValue();
        sr.setOf(OperationsUtils.hasOverflow16(result));
        sr.setCf(OperationsUtils.hasCarry(ax.getValue(), ax.getValue()));
        sr.setPf(OperationsUtils.parityBit(result));
        sr.setZf(OperationsUtils.isZero(result));
        sr.setSf(OperationsUtils.hasSignal(result));

        ax.setValue((short) result);
    }
}
