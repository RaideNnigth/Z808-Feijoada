package virtual_machine.commands.operations;

import virtual_machine.registers.RegWork;
import virtual_machine.types.UnsignedByte;
import virtual_machine.types.UnsignedShort;

import java.util.List;

public class Add implements Command {
    private void add( RegWork ax, RegWork dx ) {
        ax.setReg( new UnsignedShort( ax.getReg() + dx.getReg() ) );
    }

    @Override
    public void doOperation( List<Object> args ) {
        RegWork ax = (RegWork)args.get( 0 );
        RegWork dx = (RegWork)args.get( 1 );
        this.add( ax, dx );
    }
}
