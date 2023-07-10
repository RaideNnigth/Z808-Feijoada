package virtual_machine.registers;

import virtual_machine.Observable;
import virtual_machine.Observer;

import java.util.ArrayList;

public class RegFlags implements StatusRegister, Observable {
    Boolean of;
    Boolean sf;
    Boolean zf;
    Boolean ifFlag;
    Boolean pf;
    Boolean cf;
    private ArrayList<Observer> observers;

    public RegFlags() {
        this.of = false;
        this.sf = false;
        this.zf = false;
        this.ifFlag = false;
        this.pf = false;
        this.cf = false;
        observers = new ArrayList<>();
    }

    /**
     * @return the value of overflow flag
     */
    public Boolean getOf() {
        return of;
    }

    /**
     * @return the value of signal flag
     */
    public Boolean getSf() {
        return sf;
    }

    /**
     * @return the value of zero flag
     */
    public Boolean getZf() {
        return zf;
    }

    /**
     * @return the value of interruption flag
     */
    public Boolean getIfFlag() {
        return ifFlag;
    }

    /**
     * @return the value of parity bit flag
     */
    public Boolean getPf() {
        return pf;
    }

    /**
     * @return the value of carry flag
     */
    public Boolean getCf() {
        return cf;
    }

    /**
     * Used to set the overflow flag
     * @param of
     */
    public void setOf(Boolean of) {
        this.of = of;
        notifyObservers();
    }

    /**
     * Used to set the signal flag
     * @param sf
     */
    public void setSf(Boolean sf) {
        this.sf = sf;
        notifyObservers();
    }

    /**
     * Used to set the zero flag
     * @param zf
     */
    public void setZf(Boolean zf) {
        this.zf = zf;
        notifyObservers();
    }

    /**
     * Used to set the interruption flag
     * @param ifFlag
     */
    public void setIfFlag(Boolean ifFlag) {
        this.ifFlag = ifFlag;
        notifyObservers();
    }

    /**
     * Used to set the parity bit flag
     * @param pf
     */
    public void setPf(Boolean pf) {
        this.pf = pf;
        notifyObservers();
    }

    /**
     * Used to set the carry flag
     * @param cf
     */
    public void setCf(Boolean cf) {
        this.cf = cf;
        notifyObservers();
    }

    // Overhead
    public void reset() {
        setOf(false);
        setSf(false);
        setZf(false);
        setIfFlag(false);
        setPf(false);
        setCf(false);
    }

    public void register(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
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
        return null;
    }
}
