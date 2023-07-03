package virtual_machine.commands.operations.logical;

import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.OperationsUtils;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class OrAxCte implements Command {
    public void doOperation(HashMap<String, Object> args) {
        RegWork ax = (RegWork) args.get("ax");
        byte cte = (byte) args.get("cte");
        RegFlags sr = (RegFlags) args.get("sr");

        short result = 0;
        boolean carryFlag = false;
        byte bitCarry = 0;
        byte bitResult;
        byte bitOp1;
        byte bitOp2;
        for (int i = 0; i < 16; i++) {
            bitOp1 = (byte) (((ax.getReg() << (15 - i)) >>> (15 - i)));
            bitOp2 = (byte) ((cte << (15 - i)) >>> (15 - i));
            bitResult = (byte) (bitOp1 | bitOp2 | bitCarry);
            bitCarry = (byte) (bitOp1 & bitOp2 & bitCarry);
            if (bitCarry == 1)
                carryFlag = true;
            result = (short) (bitResult | (bitResult << (15 - i)));
        }
        sr.setCf(carryFlag);
        sr.setPf(OperationsUtils.parityBit(result));
        sr.setZf(OperationsUtils.isZero(result));
        sr.setSf(OperationsUtils.hasSignal(result));
        sr.setOf(bitCarry == 1);

        ax.setReg(result);
    }
}
