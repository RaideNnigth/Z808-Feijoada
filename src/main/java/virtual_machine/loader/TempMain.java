package virtual_machine.loader;

import java.io.IOException;

public class TempMain {
    public static void main(String[] args) {
        Loader loader = null;
        try {
            loader = new Loader("C:\\Users\\Henrique\\Documents\\GitHub\\Z808-Feijoada\\src\\programs\\p1.bin");
        } catch (IOException e) {
            System.out.println(e);
        }

        loader.loadToMemory();
    }
}
