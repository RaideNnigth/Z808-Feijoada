package virtual_machine.commands.operations.arithmetical;

import virtual_machine.commands.operations.Command;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import javax.lang.model.type.ArrayType;
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
    //  0001
    // +0001
    // =0010

    @Override
    public void doOperation( HashMap<String, Object> args ) {
        RegWork ax = (RegWork) args.get("ax");
        RegWork dx = (RegWork) args.get("dx");
        RegFlags sr = (RegFlags) args.get("sr");

        int result = ax.getReg() + dx.getReg();
        sr.setOf(ArithmeticUtils.hasOverflow(result));
        sr.setCf(ArithmeticUtils.hasCarry(ax.getReg(), dx.getReg()));
        sr.setPf(ArithmeticUtils.parityBit(result));
        sr.setZf(ArithmeticUtils.isZero(result));
        sr.setSf(ArithmeticUtils.hasSignal(result));

        ax.setReg((short) result);
    }
}
