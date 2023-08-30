package virtual_machine.commands.operations.move;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class MovAxCte implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        BankOfRegisters br = (BankOfRegisters) args.get(OpParameters.REGISTERS);
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        short cte = mc.getWordBE(ip.getValue());
        br.incrementIp();

        ax.setValue(cte);
    }
}
