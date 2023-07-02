package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;
import java.util.List;

public class AddAxDx implements Command {
    private void add( RegWork ax, RegWork dx ) {
        ax.setReg( (short)(ax.getReg() + dx.getReg()) );
    }

    // Campos do registrador SR afetados pela soma: CF, PF, ZF, SF, OF
    // CF: carry ou borrow
    // PF: flag de paridade
    // ZF: flag de zero
    // SF: flag de sinal
    // OF: flag de overflow
    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        RegWork dx = (RegWork) args.get("dx");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() + dx.getReg();
        if (result > 32_767) {
            sr.setSf(true);
            sr.setOf(true);
        } else if (true) {

        }


        this.add( ax, dx );
    }
}
