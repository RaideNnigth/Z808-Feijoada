package virtual_machine.commands.operations.move;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MovAxDir implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        ip.setValue((short) (ip.getValue() + 1)); // Increment IP

        short operandAddr = mc.getWordBE(ip.getValue()); // Get operand addr in dataMem
        short cte = mc.getWordBE(operandAddr);

        ax.setValue(cte);
    }
}
