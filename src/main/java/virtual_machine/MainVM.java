package virtual_machine;

import virtual_machine.interpreter.Interpreter;
import virtual_machine.loader.Loader;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;
import virtual_machine.registers.Registers;

import java.io.IOException;
import java.util.HashMap;

public class MainVM {
    private static final Loader vmLoader = new Loader();
    private static final Interpreter vmInterpreter = new Interpreter();
    private static final HashMap<Registers, RegWork> workRegistersHashMap = new HashMap<>();

    public static void runEntireProgram(String programPath) {
        try {
            vmLoader.setProgramToLoad(programPath);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        // Loads program to main memory
        vmLoader.loadToMemory();
        // Executes
        vmInterpreter.startExecution();
    }

    public static HashMap<Registers, RegWork> getWorkRegisters() {
        workRegistersHashMap.put(Registers.AX, Interpreter.ax);
        workRegistersHashMap.put(Registers.DX, Interpreter.dx);
        workRegistersHashMap.put(Registers.IP, Interpreter.ip);
        workRegistersHashMap.put(Registers.SP, Interpreter.sp);
        workRegistersHashMap.put(Registers.SI, Interpreter.si);

        return workRegistersHashMap;
    }

    public static RegFlags getFlagsRegister() {
        return Interpreter.sr;
    }
}
