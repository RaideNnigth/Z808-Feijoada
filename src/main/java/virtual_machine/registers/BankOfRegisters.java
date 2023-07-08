package virtual_machine.registers;

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

    private final HashMap<Registers, RegWork> workRegisters = new HashMap<>();

    public BankOfRegisters() {
        workRegisters.put(Registers.AX, ax);
        workRegisters.put(Registers.DX, dx);
        workRegisters.put(Registers.IP, ip);
        workRegisters.put(Registers.SP, sp);
        workRegisters.put(Registers.SI, si);
        workRegisters.put(Registers.CS, cs);
        workRegisters.put(Registers.DS, ds);
        workRegisters.put(Registers.SS, ss);
    }

    public void incrementIp() {
        ip.setValue((short) (ip.getValue() + 1));
    }

    public HashMap<Registers, RegWork> getWorkRegisters() {
        return workRegisters;
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
}
