package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;
import virtual_machine.utils.BinaryUtils;

import java.util.HashMap;

public class DivSi implements Command {

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork)args.get("ax");
        RegWork dx = (RegWork)args.get("dx");
        RegWork si = (RegWork)args.get("si");

        int dividend = BinaryUtils.concatShorts(dx.getReg(), ax.getReg());
        int result = dividend / si.getReg();
        int remainder = dividend % si.getReg();

        ax.setReg((short) result);
        dx.setReg((short) remainder);
    }
}
