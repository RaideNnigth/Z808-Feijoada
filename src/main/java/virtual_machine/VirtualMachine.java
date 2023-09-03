package virtual_machine;

import virtual_machine.interpreter.Interpreter;
import virtual_machine.loader.Loader;
import virtual_machine.observerpattern.RegObsListener;
import virtual_machine.registers.Registers;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class VirtualMachine {
    private final Loader vmLoader = new Loader();
    private final Interpreter vmInterpreter = new Interpreter();
    private static LinkedList<RegObsListener> subscribers = new LinkedList<>();

    public void loadProgram(String path) throws IOException {
        vmInterpreter.getRegisters().resetAllRegisters();
        vmLoader.setProgramToLoad(path);

        // Loads program to main memory
        vmLoader.loadToMemory(vmInterpreter.getMemoryController(), vmInterpreter.getRegisters().getCs().getValue(), vmInterpreter.getRegisters().getDs().getValue(), vmInterpreter.getRegisters().getSs().getValue());
    }

    public void executeProgram() {
        vmInterpreter.executeProgram();
        notifySubscribers();
    }

    public void exportMemoryData(String filepath) {
        vmInterpreter.getMemoryController().exportMemoryData(filepath);
    }

    public void subscribe(RegObsListener rl) {
        subscribers.add(rl);
    }

    public void notifySubscribers() {
        HashMap<Registers, Short> workRegValues = vmInterpreter.getRegisters().getWorkRegValues();
        String regFlagValue = vmInterpreter.getRegisters().getSr().toString();

        for(RegObsListener rl : subscribers) {
            rl.updatedRegs(workRegValues, regFlagValue);
        }
    }
}
