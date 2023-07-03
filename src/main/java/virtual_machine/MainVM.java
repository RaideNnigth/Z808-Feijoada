package virtual_machine;

import virtual_machine.loader.Loader;

import java.io.IOException;

public class MainVM {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("You must give the path of a valid .bin file!");
            System.exit(-1);
        }

        // Creates Loader
        Loader vmLoader = null;
        try {
            vmLoader = new Loader(args[1]);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
        // Creates Interpreter
        Interpreter vmInterpreter = new Interpreter();

        // Loads program to main memory
        vmLoader.loadToMemory();
        vmInterpreter.startExecution();
    }
}
