package virtual_machine.registers;

import virtual_machine.memory.Memory;
import virtual_machine.memory.MemoryController;

import java.util.HashMap;

public class BankOfRegisters {
    private final RegWork ax = new RegWork(); // General register
    private final RegWork dx = new RegWork(); // General register
    private final RegWork sp = new RegWork(); // Stack pointer
    private final RegWork si = new RegWork(); // Used by some instruction in indirect addressable mode
    private final RegWork ip = new RegWork(); // Instruction pointer
    private final RegFlags sr = new RegFlags(); // Status register

    private final RegWork cs = new RegWork(); // Code segment register
    private final RegWork ds = new RegWork(); // Data segment register
    private final RegWork ss = new RegWork(); // Stack segment register

    public BankOfRegisters() {
        resetAllRegisters();
    }

    public void incrementIp() {
        ip.setValue((short) (ip.getValue() + 1));
    }

    public RegWork getAx() {
        return ax;
    }

    public RegWork getDx() {
        return dx;
    }

    public RegWork getSp() {
        return sp;
    }

    public RegWork getSi() {
        return si;
    }

    public RegWork getIp() {
        return ip;
    }

    public RegFlags getSr() {
        return sr;
    }

    public RegWork getCs() {
        return cs;
    }

    public RegWork getDs() {
        return ds;
    }

    public RegWork getSs() {
        return ss;
    }

    public HashMap<Registers, Short> getWorkRegValues() {
        HashMap<Registers, Short> regValues = new HashMap<>();

        regValues.put(Registers.AX, ax.getValue());
        regValues.put(Registers.DX, dx.getValue());
        regValues.put(Registers.IP, ip.getValue());
        regValues.put(Registers.SP, sp.getValue());
        regValues.put(Registers.SI, si.getValue());
        regValues.put(Registers.CS, cs.getValue());
        regValues.put(Registers.DS, ds.getValue());
        regValues.put(Registers.SS, ss.getValue());

        return regValues;
    }

    public void resetAllRegisters() {
        ax.reset();
        dx.reset();
        sp.reset();
        si.reset();
        ip.reset();
        sr.reset();

        cs.setValue(MemoryController.CODE_SEGMENT_DEFAULT_START);
        ds.setValue(MemoryController.DATA_SEGMENT_DEFAULT_START);
        ss.setValue(MemoryController.STACK_DEFAULT_START);
    }


}
