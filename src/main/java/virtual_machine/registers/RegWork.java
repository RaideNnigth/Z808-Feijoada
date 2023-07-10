package virtual_machine.registers;

import virtual_machine.Observable;
import virtual_machine.Observer;
import virtual_machine.utils.BinaryUtils;

import java.util.ArrayList;

public class RegWork implements Observable {
    private byte regHigh;
    private byte regLow;
    private ArrayList<Observer> observers;

    public RegWork() {
        observers = new ArrayList<>();
    }

    public byte getHigh() {
        return regHigh;
    }

    public byte getLow() {
        return regLow;
    }

    /**
     * Retorna o valor de 16-bits armazenado no registrador. Esse
     * valor pode ser usado para operações.
     *
     * @return valor armazenado no registrador
     */
    public short getValue() {
        return BinaryUtils.concatBytes(regHigh, regLow);
    }

    /**
     * Retorna o valor de 16-bits armazenado no registrador no
     * formato Little Endian.
     * <p>
     * Esse valor NÃO pode ser usado para operações! Somente
     * para armazenar na memória.
     * <p>
     * Para operações, use getValue().
     *
     * @return valor armazenado no registrador em Little Endian
     */
    public short getValueLE() {
        return BinaryUtils.concatBytes(regLow, regHigh);
    }

    public void setHigh(int regHigh) {
        this.regHigh = (byte) regHigh;
        this.notifyObservers();
    }

    public void setLow(int regLow) {
        this.regLow = (byte) regLow;
        this.notifyObservers();
    }

    public void setValue(short reg) {
        this.regHigh = BinaryUtils.getHighByte(reg);
        this.regLow = BinaryUtils.getLowByte(reg);
        this.notifyObservers();
    }

    public void reset() {
        setValue((short) 0);
    }

    public void register(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
        System.out.println("Register");
    }

    public void unregister(Observer observer) {
        if (observers.contains(observer))
            observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }

    public Object getUpdate(Observer observer) {
        return getValue();
    }
}
