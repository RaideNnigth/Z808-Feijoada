package virtual_machine;

import virtual_machine.commands.CommandExecutor;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    //region Registers
    protected static final RegWork ax = new RegWork();
    protected static final RegWork dx = new RegWork();
    protected static final RegWork sp = new RegWork();
    protected static final RegWork si = new RegWork();
    protected static final RegWork ip = new RegWork();
    protected static final RegFlags sr = new RegFlags();
    //endregion

    //region Memory
    protected static final MemoryController memoryController = new MemoryController();
    //endregion

    //region Invoker
    protected static final CommandExecutor commandExecutor = new CommandExecutor();
    //endregion

    protected static final Map<String, Object> operators = new HashMap<>();
    protected static void main( String[] args ) {

    }

    protected void startExecution() {
        while ( Interpreter.ip.getReg() < MemoryController.standardDataSegment ) {

            short currentInstructionPointer = Interpreter.ip.getReg();
        }
    }
}
