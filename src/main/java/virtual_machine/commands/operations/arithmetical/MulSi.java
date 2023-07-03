package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MulSi implements Command {

    private void div( RegWork ax, RegWork dx, RegWork si ) {
    }

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork)args.get("ax");
        RegWork dx = (RegWork)args.get("dx");
        RegWork si = (RegWork)args.get("si");
        this.div( ax, dx , si);

    }
}
