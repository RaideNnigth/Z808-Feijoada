package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MulSi implements Command {

    @Override
    public void doOperation(HashMap<OpParameters, Object> args ) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork dx = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getDx();
        RegWork si = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSi();
        RegFlags sr = (RegFlags) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSr();

        long result = ax.getValue() * si.getValue();
        sr.setOf(OperationsUtils.hasOverflow32(result));
        sr.setCf(OperationsUtils.hasCarry(ax.getValue(), si.getValue()));

        long mostSig = result >>> 48;
        mostSig = result << 16;

        long lessSig = result << 48;
        lessSig = lessSig >>> 48;

        dx.setValue((short) mostSig);
        ax.setValue((short) lessSig);
    }
}
