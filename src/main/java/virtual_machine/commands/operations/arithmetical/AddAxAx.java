package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxAx implements Command {
    private void add( RegWork ax, RegWork dx ) {
        ax.setReg( (short) (ax.getReg() + dx.getReg()) );
    }

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() + ax.getReg();
        sr.setOf(ArithmeticUtils.hasOverflow(result));
        sr.setCf(ArithmeticUtils.hasCarry(ax.getReg(), ax.getReg()));
        sr.setPf(ArithmeticUtils.parityBit(result));
        sr.setZf(ArithmeticUtils.isZero(result));
        sr.setSf(ArithmeticUtils.hasSignal(result));

        ax.setReg((short) result);
    }
}
