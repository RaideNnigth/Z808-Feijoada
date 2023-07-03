package virtual_machine.interpreter;

import virtual_machine.commands.CommandExecutor;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;

import java.util.HashMap;

public class Interpreter {

    // Registers
    private static final RegWork ax = new RegWork();
    private static final RegWork dx = new RegWork();
    private static final RegWork sp = new RegWork();
    private static final RegWork si = new RegWork();
    private static final RegWork ip = new RegWork();
    private static final RegFlags sr = new RegFlags();

    private static final MemoryController memoryController = new MemoryController();
    private static final CommandExecutor commandExecutor = new CommandExecutor();
    private static final HashMap<OpParameters, Object> operationParameters = new HashMap<>();

    public Interpreter() {
        operationParameters.put(OpParameters.AX, ax); // Accumulator
        operationParameters.put(OpParameters.DX, dx); // Data rex
        operationParameters.put(OpParameters.SP, sp); // Stack Pointer
        operationParameters.put(OpParameters.SI, si); // Source Index
        operationParameters.put(OpParameters.IP, ip); // Instruction Pointer
        operationParameters.put(OpParameters.SR_FLAGS, sr); // Flags Register
        operationParameters.put(OpParameters.MEM_CONTROLLER, memoryController); // Memory (duh)
    }

    public void startExecution() {
        while (ip.getReg() < MemoryController.standardDataSegment) {
            short instruction = Interpreter.memoryController.getInstruction(ip.getReg());

            // Execute instruction
            commandExecutor.doOperation(instruction, operationParameters);

            // Update IP
            Interpreter.ip.setReg((short) (ip.getReg() + 1));
        }
    }
}
