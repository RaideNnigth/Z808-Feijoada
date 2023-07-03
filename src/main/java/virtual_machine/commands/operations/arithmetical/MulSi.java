package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MulSi implements Command {

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork)args.get("ax");
        RegWork dx = (RegWork)args.get("dx");
        RegWork si = (RegWork)args.get("si");
        RegFlags sr = (RegFlags) args.get("sr");

        long result = ax.getReg() * si.getReg();
        sr.setOf(OperationsUtils.hasOverflow32(result));
        sr.setCf(OperationsUtils.hasCarry(ax.getReg(), si.getReg()));


        long mostSig = result >>> 48;
        mostSig = result << 16;

        long lessSig = result << 48;
        lessSig = lessSig >>> 48;

        dx.setReg((short) mostSig);
        ax.setReg((short) lessSig);




    }
}
