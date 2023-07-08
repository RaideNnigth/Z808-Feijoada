package virtual_machine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        try {
            vm.loadProgram(args[1]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        vm.executeProgram();
    }
}
