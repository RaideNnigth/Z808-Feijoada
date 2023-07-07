package virtual_machine.interpreter;

import virtual_machine.commands.CommandExecutor;
import virtual_machine.memory.Memory;
import virtual_machine.os_interruptions.OSInterruptionHandler;
import virtual_machine.memory.MemoryController;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;
import virtual_machine.utils.BinaryUtils;

import java.util.HashMap;

public class Interpreter {

    // Registers
    private final RegWork ax = new RegWork(); // General register
    private final RegWork dx = new RegWork(); // General register
    private final RegWork sp = new RegWork(); // Stack pointer
    private final RegWork si = new RegWork(); // Used by some instruction in indirect addressable mode
    private final RegWork ip = new RegWork(); // Instruction pointer
    private final RegFlags sr = new RegFlags(); // Status register

    private final RegWork cs = new RegWork(); // Code segment register
    private final RegWork ds = new RegWork(); // Data segment register
    private final RegWork ss = new RegWork(); // Stack segment register

    private final MemoryController memoryController = new MemoryController();
    private final CommandExecutor commandExecutor = new CommandExecutor();
    private final HashMap<Short, Object> interruptionVector = new HashMap<>();
    private final HashMap<OpParameters, Object> operationParameters = new HashMap<>();

    public Interpreter() {
        cs.setReg((short) 0);
        ds.setReg((short) (30000 + 1));
        ss.setReg((short) (Memory.MEM_SIZE - 1));

        initializeInterruptionVector();
        initializeOperationParameters();
    }

    public Interpreter(int codeSegLength) {
        cs.setReg((short) 0);
        ds.setReg((short) (codeSegLength + 1));
        ss.setReg((short) (Memory.MEM_SIZE - 1));

        initializeInterruptionVector();
        initializeOperationParameters();
    }

    private void initializeInterruptionVector() {
        interruptionVector.put((short) 0x0021, new OSInterruptionHandler()); // OS Interruption (duh)
    }

    private void initializeOperationParameters() {
        operationParameters.put(OpParameters.AX, ax); // Accumulator
        operationParameters.put(OpParameters.DX, dx); // Data rex
        operationParameters.put(OpParameters.SP, sp); // Stack Pointer
        operationParameters.put(OpParameters.SI, si); // Source Index
        operationParameters.put(OpParameters.IP, ip); // Instruction Pointer
        operationParameters.put(OpParameters.SR_FLAGS, sr); // Flags Register
        operationParameters.put(OpParameters.CS, cs);
        operationParameters.put(OpParameters.DS, ds);
        operationParameters.put(OpParameters.SS, ss);
        operationParameters.put(OpParameters.MEM_CONTROLLER, memoryController); // Memory (duh)
        operationParameters.put(OpParameters.INT_VECTOR, interruptionVector); // Interruption vector (duh)
    }

    public void executeProgram() {
        while (ip.getReg() < ss.getReg()) {
            executeNextInstruction();
        }
    }

    public void executeNextInstruction() {
        // Get the instruction that is ponteinted by IP
        short instruction = memoryController.getWordBE(ip.getReg());
        // Execute the instruction
        commandExecutor.doOperation(instruction, operationParameters);
        // Update the register IP
        incrementIP();
    }

    public void incrementIP() {
        ip.setReg((short) (ip.getReg() + 1));
    }

    public short getCs() {
        return cs.getReg();
    }

    public short getDs() {
        return ds.getReg();
    }

    public short getSs() {
        return ss.getReg();
    }

    public MemoryController getMemoryController() {
        return memoryController;
    }
}
