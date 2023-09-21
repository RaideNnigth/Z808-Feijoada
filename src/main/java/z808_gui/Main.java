package z808_gui;

import virtual_machine.VirtualMachine;
import z808_gui.utils.UIUtils;

public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();
        UIUtils.mainWindowInstance = new MainWindow(vm);
    }
}
