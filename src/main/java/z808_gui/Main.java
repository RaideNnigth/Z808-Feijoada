package z808_gui;

import virtual_machine.VirtualMachine;

public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();
        MainWindow z808UI = new MainWindow(vm);
    }
}
