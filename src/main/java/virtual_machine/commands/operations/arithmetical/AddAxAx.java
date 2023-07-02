package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxAx implements Command {
    private void add( RegWork ax, RegWork dx ) {
        ax.setReg( (short) (ax.getReg() + dx.getReg()) );
    }

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        this.add( ax, ax );
    }
}
