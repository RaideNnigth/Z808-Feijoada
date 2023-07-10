package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

import java.awt.image.BandCombineOp;
import java.util.HashMap;

// nome de escolha do miguel
public class Jmp implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        BankOfRegisters br = (BankOfRegisters) args.get(OpParameters.REGISTERS);
        RegWork ip = (RegWork) br.getIp();
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);

        short jmpDesl = (short) (mc.getWordBE(ip.getValue())); // Get operand addr in dataMem
        //br.incrementIp(); // Increment IP

        ip.setValue((short) (ip.getValue() + jmpDesl));
    }
}
