package virtual_machine.os_interruptions.operations;

import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.BankOfRegisters;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;
import java.util.Scanner;

public class DirectReadChar implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        RegWork ax = (RegWork) ((BankOfRegisters) args.get(OpParameters.REGISTERS)).getAx();

        // Read character from stdin
        Scanner sc = new Scanner(System.in);
        char c = sc.next().charAt(0);

        ax.setLow((byte) c);
    }
}
