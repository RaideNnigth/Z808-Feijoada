package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;

import java.util.List;

public class AddAxCte implements Command {
    private void add( RegWork ax, byte dx ) {
        ax.setReg( (short)(ax.getReg() + dx ) );
    }

    @Override
    public void doOperation( List<Object> args ) {
        RegWork ax = (RegWork)args.get( 0 );
        byte cte = (byte)args.get( 1 );
        this.add( ax, cte );
    }
}
