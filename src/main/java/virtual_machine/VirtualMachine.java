package virtual_machine;

import virtual_machine.interpreter.Interpreter;
import virtual_machine.loader.Loader;
import virtual_machine.registers.RegFlags;
import virtual_machine.registers.RegWork;
import virtual_machine.registers.Registers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class VirtualMachine {
    private final Loader vmLoader = new Loader();
    private final Interpreter vmInterpreter = new Interpreter();
    private final HashMap<Registers, RegWork> workRegistersHashMap = new HashMap<>();
    private RegistersObserver ipObserver;

    public VirtualMachine() {
        ipObserver = new RegistersObserver("IP", vmInterpreter.getRegisters().getIp());
        vmInterpreter.getRegisters().getIp().register(ipObserver);
        ipObserver.setObservable(vmInterpreter.getRegisters().getIp());
        vmInterpreter.getRegisters().getIp().notifyObservers();
    }

    public void loadProgram(String path) throws IOException {
        vmLoader.setProgramToLoad(path);
        // Loads program to main memory
        vmLoader.loadToMemory(vmInterpreter.getMemoryController(), vmInterpreter.getRegisters().getCs().getValue(), vmInterpreter.getRegisters().getDs().getValue());
    }

    public void executeProgram() {
        vmInterpreter.executeProgram();
        vmInterpreter.getRegisters().getIp().notifyObservers();
    }

    public void executeNextInstruction() {
        vmInterpreter.executeNextInstruction();
    }

    public HashMap<Registers, RegWork> getWorkRegisters() {
        return workRegistersHashMap;
    }

    public RegFlags getFlagsRegister() {
        return vmInterpreter.getRegisters().getSr();
    }

    public Loader getVmLoader() {
        return vmLoader;
    }

    public Interpreter getVmInterpreter() {
        return vmInterpreter;
    }
}
