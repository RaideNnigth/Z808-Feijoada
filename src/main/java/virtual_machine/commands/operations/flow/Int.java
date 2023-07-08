package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.interruptions.InterruptionHandler;
import virtual_machine.os_interruptions.OSInterruptionHandler;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class Int implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);
        HashMap<Short, InterruptionHandler> intVector = (HashMap<Short, InterruptionHandler>) args.get(OpParameters.INT_VECTOR);

        byte intService = ax.getHigh();
        //System.out.println(intService);

        // We must get from the memory the 16 bit constant
        ip.setValue((short) (ip.getValue() + 1)); // Increment IP

        short intCode = mc.getWordBE(ip.getValue()); // Get interruption code

        intVector.get(intCode).doOperation(intService, args);
    }
}
