package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;
import virtual_machine.utils.BinaryUtils;

import java.util.HashMap;

public class DivAx implements Command {
    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork)args.get("ax");
        RegWork dx = (RegWork)args.get("dx");

        int dividend = BinaryUtils.concatShorts(dx.getReg(), ax.getReg());
        int result = dividend / ax.getReg();
        int remainder = dividend % ax.getReg();

        ax.setReg((short) result);
        dx.setReg((short) remainder);
    }
}
