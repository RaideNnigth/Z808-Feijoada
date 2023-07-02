package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class AddAxCte implements Command {
    private void add( RegWork ax, byte dx ) {
        ax.setReg( (short) (ax.getReg() + dx ) );
    }

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        byte cte = (byte)args.get("cte");
        this.add( ax, cte );
    }
}
