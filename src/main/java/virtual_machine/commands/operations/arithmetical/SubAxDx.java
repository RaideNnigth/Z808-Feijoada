package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class SubAxDx implements Command {

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        RegWork dx = (RegWork) args.get("dx");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() - dx.getReg();
        sr.setOf(ArithmeticUtils.hasOverflow(result));
        sr.setCf(ArithmeticUtils.hasCarry(ax.getReg(), dx.getReg()));
        sr.setPf(ArithmeticUtils.parityBit(result));
        sr.setZf(ArithmeticUtils.isZero(result));
        sr.setSf(ArithmeticUtils.hasSignal(result));

        ax.setReg((short) result);
    }
}
