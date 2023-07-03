package virtual_machine;

import virtual_machine.commands.CommandExecutor;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;
import virtual_machine.utils.BinaryUtils;

import java.util.HashMap;

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

    //region Interpreter
    protected static final HashMap<String, Object> operators = new HashMap<>();
    //endregion


    protected Interpreter() {
        operators.put( "ax", ax );
        operators.put( "dx", dx );
        operators.put( "sp", sp );
        operators.put( "si", si );
        operators.put( "ip", ip );
        operators.put( "sr", sr );
        operators.put( "memoryController", memoryController );
        operators.put( "identifier", null );
    }

    protected void startExecution() {
        while ( Interpreter.ip.getReg() < MemoryController.standardDataSegment ) {
            short currentInstructionPointer = Interpreter.ip.getReg();
            short instruction = Interpreter.memoryController.getInstruction( currentInstructionPointer );

            //decode instruction
            byte opCode = BinaryUtils.getHighByte( instruction );
            byte identifier = BinaryUtils.getLowByte( instruction );
            operators.put( "identifier", identifier );

            //execution instruction
            commandExecutor.doOperation( opCode, operators );

            if ( Interpreter.ip.getReg() == currentInstructionPointer ) {
                Interpreter.ip.setReg( (short)( Interpreter.ip.getReg() + 1 ) );
            }
        }
    }
}
