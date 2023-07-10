package virtual_machine;

public class RegistersObserver implements Observer {
    private String regName;
    private Observable observable;

    public RegistersObserver(String regName, Observable observable) {
        this.regName = regName;
        this.observable = observable;
    }

    public void update() {
        short regValue = (short) observable.getUpdate(this);
        System.out.println(regName + ": " + regValue);
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }
}
