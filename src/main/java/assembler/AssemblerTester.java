package assembler;

import assembler.codeprocessors.OperationProcessor;

import java.util.Arrays;

public class AssemblerTester {
    public static void main(String[] args) {
        String teste = "; pinto";

        String[] arrayTeste = teste.split(";");

        System.out.println(Arrays.toString(arrayTeste));

    }
}
