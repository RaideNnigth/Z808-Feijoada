package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegWork;

import java.util.List;

public class DivSi implements Command {

    private void div( RegWork ax, RegWork dx, RegWork si ) {
    }

    @Override
    public void doOperation( List<Object> args ) {
        RegWork ax = (RegWork)args.get( 0 );
        RegWork dx = (RegWork)args.get( 1 );
        RegWork si = (RegWork)args.get( 2 );
        this.div( ax, dx , si);

    }
}
