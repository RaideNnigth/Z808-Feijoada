package virtual_machine;

import java.io.IOException;

public class VMTestsMain {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        try {
            vm.loadProgram(args[0]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        vm.executeProgram();
    }
}
