package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;
import virtual_machine.types.byte;
import virtual_machine.types.short;

import java.util.List;

public class AddAxCte implements Command {
    private void add( RegWork ax, byte dx ) {
        ax.setReg( new short( ax.getReg() + dx.getValue() ) );
    }

    @Override
    public void doOperation( List<Object> args ) {
        RegWork ax = (RegWork)args.get( 0 );
        byte cte = (byte)args.get( 1 );
        this.add( ax, cte );
    }
}
