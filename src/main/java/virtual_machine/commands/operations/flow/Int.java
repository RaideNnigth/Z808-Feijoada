package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.interruptions.InterruptionHandler;
import virtual_machine.os_interruptions.OSInterruptionHandler;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class Int implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) args.get(OpParameters.AX);
        RegWork ip = (RegWork) args.get(OpParameters.IP);
        MemoryController mc = (MemoryController) args.get(OpParameters.MEM_CONTROLLER);
        HashMap<Short, InterruptionHandler> intVector = (HashMap<Short, InterruptionHandler>) args.get(OpParameters.INT_VECTOR);

        byte intService = ax.getRegHigh();
        //System.out.println(intService);

        // We must get from the memory the 16 bit constant
        ip.setReg((short) (ip.getReg() + 1)); // Increment IP
        short intCode = mc.getWord(ip.getReg()); // Get interruption code

        intVector.get(intCode).doOperation(intService, args);
    }
}
