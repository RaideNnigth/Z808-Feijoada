package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxAx implements Command {
    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() + ax.getReg();
        sr.setOf(OperationsUtils.hasOverflow16(result));
        sr.setCf(OperationsUtils.hasCarry(ax.getReg(), ax.getReg()));
        sr.setPf(OperationsUtils.parityBit(result));
        sr.setZf(OperationsUtils.isZero(result));
        sr.setSf(OperationsUtils.hasSignal(result));

        ax.setReg((short) result);
    }
}
