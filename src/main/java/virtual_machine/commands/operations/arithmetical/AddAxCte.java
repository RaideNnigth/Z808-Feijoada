package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxCte implements Command {

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        byte cte = (byte) args.get("cte");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() + cte;
        sr.setOf(ArithmeticUtils.hasOverflow16(result));
        sr.setCf(ArithmeticUtils.hasCarry(ax.getReg(), (int) cte));
        sr.setPf(ArithmeticUtils.parityBit(result));
        sr.setZf(ArithmeticUtils.isZero(result));
        sr.setSf(ArithmeticUtils.hasSignal(result));

        ax.setReg((short) result);
    }
}
