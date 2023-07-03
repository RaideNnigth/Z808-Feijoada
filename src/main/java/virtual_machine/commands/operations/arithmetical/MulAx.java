package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MulAx implements Command {
    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork)args.get("ax");
        RegWork dx = (RegWork)args.get("dx");
        RegWork si = (RegWork)args.get("si");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() * ax.getReg();
        sr.setCf(ArithmeticUtils.hasCarry(ax.getReg(), ax.getReg()));
        sr.setOf(ArithmeticUtils.hasOverflow32(result));


    }
}
