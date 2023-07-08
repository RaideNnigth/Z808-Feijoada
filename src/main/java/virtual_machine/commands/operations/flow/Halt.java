package virtual_machine.commands.operations.flow;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.Interpreter;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class Halt implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ip = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getIp();
        RegWork ss = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getSs();
        ip.setValue(ss.getValue());
    }
}
