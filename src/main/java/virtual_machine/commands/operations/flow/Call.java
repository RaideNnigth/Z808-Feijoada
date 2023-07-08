package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class Call implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        RegWork sp = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSp(); // Get stack pointer register
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        // We must get from the memory the 16 bit constant
        ip.setValue((short) (ip.getValue() + 1)); // Increment IP

        sp.setValue((short) (sp.getValue() + 1)); // Increment SP
        short jmpAddr = mc.getWordBE(ip.getValue()); // Get operand addr in dataMem

        mc.writeWord((short) (ip.getValue() + 1), sp.getValue()); // Push to stack the next instruction address

        ip.setValue(jmpAddr);
    }
}
